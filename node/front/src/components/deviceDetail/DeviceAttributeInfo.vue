<template>
  <div class="device-attribute-info">
    <div class="top">
      <div>
        <img :src="imageUrl" />
      </div>
      <div class="text">
        <div class="name">
          {{ textHandle(attribute.name) }}
        </div>
        <div class="divider"></div>
        <div class="model">
          <strong>型号：{{ textHandle(attribute.model) }}</strong>
        </div>
        <div class="watercourse-department">
          <div>河道：{{ textHandle(attribute.watercourse) }}</div>
          <div>部门：{{ textHandle(attribute.department) }}</div>
        </div>
      </div>
    </div>
    <div class="bottom">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="设备描述" name="description">
          <div class="description">
            {{ textHandle(attribute.description) }}
          </div>
        </el-tab-pane>
        <el-tab-pane label="设备参数" name="parameter">
          <div
            v-for="(item, index) in parameters(attribute.productParameters)"
            :key="index"
            class="parameter"
          >
            <div class="key">{{ item.name }}:</div>
            <div class="value">{{ item.value }}</div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="设备状态" name="state">
          <div class="state">
            <div :class="status.state === 1 ? 'state-dot' : 'state-dot stop'" />
            <div
              class="text"
              :style="status.state === 1 ? 'color: #5bff4a' : 'color: #ff0033'"
            >
              {{ status.state === 1 ? "设备运行中" : "设备关闭" }}
            </div>
          </div>
          <div class="type">
            <strong>设备类型：</strong>
            <el-tag :type="type[1]">{{ type[0] }}</el-tag>
          </div>
          <div class="port" v-if="type[1] === ''">
            <strong>监听端口：</strong>
            {{ port }}
          </div>
          <div class="last-update">
            <strong>上次更新数据：</strong>
            {{ textHandle(status.lastUpdate) }}
          </div>
          <div class="btn">
            <el-button
              type="success"
              v-if="status.state === -1"
              @click="openClick"
              >开启设备</el-button
            >
            <el-button type="danger" v-else @click="closeClick"
              >关闭设备</el-button
            >
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, PropType, ref } from "vue";
import { DeviceConfig } from "@/type";
import { imgBase64, notice } from "@/utils/common";
import { prefix } from "@/prefix";
import { startTCPServer, stopTCPServer } from "@/api/request";
export default defineComponent({
  props: {
    deviceConfig: {
      type: Object as PropType<DeviceConfig>,
    },
    status: {
      type: Object as PropType<{ state: number; lastUpdate: string }>,
    },
  },
  setup(props) {
    const imageUrl = computed(() => {
      if (
        props.deviceConfig?.deviceConfigAttribute.picture === null ||
        props.deviceConfig?.deviceConfigAttribute.picture === "" ||
        props.deviceConfig?.deviceConfigAttribute.picture === undefined
      ) {
        if (
          props.deviceConfig?.deviceConfigAttribute.name === null ||
          props.deviceConfig?.deviceConfigAttribute.name === "" ||
          props.deviceConfig?.deviceConfigAttribute.name === undefined
        ) {
          return imgBase64("device");
        } else {
          return imgBase64(props.deviceConfig?.deviceConfigAttribute.name);
        }
      } else {
        return (
          prefix +
          `device/getPicture/${props.deviceConfig?.deviceConfigAttribute.picture}`
        );
      }
    });

    const attribute = computed(() => {
      return props.deviceConfig?.deviceConfigAttribute;
    });

    const status = computed(() => {
      return props.status;
    });

    const type = computed(() => {
      if (props.deviceConfig?.push) {
        return ["主动推送", ""];
      } else if (props.deviceConfig?.typing) {
        return ["手动录入", "success"];
      }
    });

    const port = computed(() => {
      return props.deviceConfig?.push?.port;
    });

    const activeName = ref("description");

    const textHandle = (text: string | null | undefined) => {
      if (text) {
        if (text != "") {
          return text;
        } else {
          return "暂无信息";
        }
      } else {
        return "暂无信息";
      }
    };

    const parameters = (productParameters: {
      parameters: { name: string; value: string }[] | undefined | null;
    }) => {
      if (productParameters) {
        return productParameters.parameters;
      } else {
        return [];
      }
    };

    const openClick = async () => {
      if (props.deviceConfig?.push) {
        if (props.deviceConfig.push.protocol === "TCP") {
          const res = await startTCPServer(
            props.deviceConfig.push.port,
            props.deviceConfig.id
          );
          if (res) {
            notice("success", "成功", "启动成功");
          }
        }
      }
    };

    const closeClick = async () => {
      if (props.deviceConfig?.push) {
        if (props.deviceConfig.push.protocol === "TCP") {
          const res = await stopTCPServer(
            props.deviceConfig.push.port,
            props.deviceConfig.id
          );
          if (res) {
            notice("success", "成功", "关闭监听");
          }
        }
      }
    };

    return {
      imageUrl,
      attribute,
      activeName,
      status,
      type,
      port,
      textHandle,
      parameters,
      openClick,
      closeClick,
    };
  },
});
</script>

<style lang="scss" scoped>
@keyframes scale {
  0% {
    transform: scale(1);
  }
  50%,
  75% {
    transform: scale(2);
  }
  78%,
  100% {
    opacity: 0;
  }
}
.device-attribute-info {
  background: white;
  padding: 20px;
  min-height: 100px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  .top {
    display: flex;
    width: 100%;
    img {
      height: 9vw;
      width: 9vw;
    }
    .text {
      width: calc(100% - 9vw - 20px);
      margin-left: 20px;
      position: relative;
      .name {
        font-size: 24px;
        color: #344767;
      }
      .divider {
        width: 40px;
        border: solid 2px #40b572;
        margin-top: 5px;
      }
      .model {
        color: #40b572;
        margin-top: 10px;
        font-size: 18px;
      }
      .watercourse-department {
        position: absolute;
        top: calc(9vw - 60px);
        line-height: 30px;
        color: #929292;
      }
    }
  }
  .bottom {
    margin-top: 70px;
    color: #929292;
    .el-tabs {
      /deep/ .is-active {
        font-weight: 600;
      }
      .description {
        line-height: 25px;
      }
      .parameter {
        display: flex;
        margin-bottom: 5px;
        .key {
          width: 30%;
          color: #000000;
        }
        .value {
          width: 70%;
        }
        .key,
        .value {
          word-wrap: break-word;
          word-break: break-all;
        }
      }

      .state {
        display: flex;
        .text {
          margin-left: 30px;
          line-height: 30px;
          font-size: 14px;
        }
        .state-dot {
          position: absolute;
          top: 10px;
          left: 10px;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          background-color: #5bff4a;
          &::before {
            content: "";
            display: block;
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background-color: #5bff4a;
            opacity: 0.7;
            animation: scale 2s infinite cubic-bezier(0, 0, 0.49, 1.02);
            animation-delay: 100ms;
          }
        }
        .stop {
          background-color: #ff0033;
          &::before {
            background-color: #ff0033;
          }
        }
      }

      .type,
      .port,
      .last-update {
        margin-top: 10px;
      }
      .btn {
        margin-top: 10px;
        float: right;
      }
    }
  }
}
</style>