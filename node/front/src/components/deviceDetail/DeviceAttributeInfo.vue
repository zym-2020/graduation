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
        <el-tab-pane label="设备行为" name="action">
          <div class="action">
            <device-action
              :actions="deviceConfig.actions"
              @deviceActionCall="deviceActionCall"
            ></device-action>
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
          <div v-if="type[0] === '主动推送'">
            <div class="protocol">
              <strong>传输协议：</strong>
              {{ protocol }}
            </div>
            <div class="port" v-if="type[1] === ''">
              <strong>监听端口：</strong>
              {{ port }}
            </div>  
          </div>

          <div class="last-update">
            <strong>上次更新数据：</strong>
            {{ textHandle(status.lastUpdate, "yyyy-MM-dd hh:mm") }}
          </div>
          <div class="storage" v-if="type[0] != '主动推送'">
            <strong>数据存储路径：</strong>
            <span @click="storageClick">{{ storage }}</span>
          </div>
          <div class="btn">
            <el-button type="info" plain v-if="addDataInputFlag"
              >添加数据</el-button
            >
            <el-button type="info" plain v-if="addDataFileFlag"
              >添加数据</el-button
            >
            <!-- <el-upload
              :action="''"
              :show-file-list="false"
              :multiple="true"
              v-if="addDataFileFlag"
            >
              <el-button type="info" plain>添加数据</el-button>
            </el-upload> -->

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

    <el-dialog v-model="paramSelectDialog" width="900" title="选择存储路径">
      <param-select
        :paramType="'path'"
        @cancelCall="cancelCall"
        @confirmCall="confirmCall"
        v-if="paramSelectDialog"
      />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, PropType, ref, watch } from "vue";
import { DeviceConfig } from "@/type";
import { imgBase64, notice, dateFormat } from "@/utils/common";
import { prefix } from "@/prefix";
import { startTCPServer, stopTCPServer } from "@/api/request";
import DeviceAction from "./DeviceAction.vue";
import ParamSelect from "./ParamSelect.vue";
import { updateStorage, changeState } from "@/api/request";
import router from "@/router";
export default defineComponent({
  components: { DeviceAction, ParamSelect },
  props: {
    deviceConfig: {
      type: Object as PropType<DeviceConfig>,
    },
    status: {
      type: Object as PropType<{
        state: number;
        lastUpdate: string;
      }>,
    },
  },
  setup(props) {
    const status = ref(props.status);
    const deviceConfig = ref<DeviceConfig>(props.deviceConfig!);
    const paramSelectDialog = ref(false);
    const storage = ref<string>(
      (function () {
        if (props.deviceConfig?.typing) {
          return props.deviceConfig.typing.storage;
        } else {
          return "";
        }
      })()
    );

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

    const addDataInputFlag = computed(() => {
      if (props.deviceConfig?.typing) {
        if (
          status.value?.state &&
          status.value?.state === 1 &&
          props.deviceConfig.typing.type === "input"
        ) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    });

    const addDataFileFlag = computed(() => {
      if (props.deviceConfig?.typing) {
        if (
          status.value?.state &&
          status.value?.state === 1 &&
          props.deviceConfig.typing.type === "file"
        ) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    });

    const attribute = computed(() => {
      return props.deviceConfig?.deviceConfigAttribute;
    });

    const type = computed(() => {
      if (props.deviceConfig?.push) {
        return ["主动推送", ""];
      } else if (props.deviceConfig?.typing) {
        if (props.deviceConfig.typing.type === "input") {
          return ["手动录入（文本录入）", "success"];
        } else {
          return ["手动录入（文件录入）", "success"];
        }
      }
    });

    const protocol = computed(() => {
      return props.deviceConfig?.push?.protocol;
    });

    const port = computed(() => {
      return props.deviceConfig?.push?.port;
    });

    const activeName = ref("description");

    const textHandle = (
      text: string | null | undefined,
      dateFormatStr?: string
    ) => {
      if (text) {
        if (text != "") {
          if (dateFormatStr) {
            return dateFormat(text, dateFormatStr);
          } else {
            return text;
          }
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
      if (deviceConfig.value.push) {
        if (deviceConfig.value.push.protocol === "TCP") {
          const res = await startTCPServer(
            deviceConfig.value.push.port,
            deviceConfig.value.id
          );
          if (res) {
            notice("success", "成功", "启动成功");
            status.value!.state = 1;
          }
        }
      } else if (deviceConfig.value.typing) {
        const res = await changeState(deviceConfig.value.id, 1);
        if (res) {
          notice("success", "成功", "启动成功");
          status.value!.state = 1;
        }
      }
    };

    const closeClick = async () => {
      if (deviceConfig.value.push) {
        if (deviceConfig.value.push.protocol === "TCP") {
          const res = await stopTCPServer(
            deviceConfig.value.push.port,
            deviceConfig.value.id
          );
          if (res) {
            notice("success", "成功", "关闭监听");
            status.value!.state = -1;
          }
        }
      } else if (deviceConfig.value.typing) {
        const res = await changeState(deviceConfig.value.id, -1);
        if (res) {
          notice("success", "成功", "关闭监听");
          status.value!.state = -1;
        }
      }
    };

    const deviceActionCall = (val: DeviceConfig) => {
      deviceConfig.value = val;
    };

    const storageClick = () => {
      paramSelectDialog.value = true;
    };

    const cancelCall = () => {
      paramSelectDialog.value = false;
    };

    const confirmCall = async (val: { path: string[]; result: string }) => {
      console.log(val);
      const id = router.currentRoute.value.params.id as string;
      let p = "";
      val.path.forEach((item) => {
        p += item + "/";
      });
      p += val.result;
      const res = await updateStorage({ deviceId: id, storage: p });
      if (res) {
        storage.value = p;
        notice("success", "成功", "修改存储路径");
      }
      paramSelectDialog.value = false;
    };

    return {
      imageUrl,
      attribute,
      activeName,
      status,
      type,
      port,
      protocol,
      deviceConfig,
      storage,
      paramSelectDialog,
      addDataInputFlag,
      addDataFileFlag,
      textHandle,
      parameters,
      openClick,
      closeClick,
      deviceActionCall,
      cancelCall,
      confirmCall,
      storageClick,
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
      .protocol,
      .last-update,
      .storage {
        margin-top: 10px;
      }
      .storage {
        span {
          cursor: pointer;
          &:hover {
            color: #409eff;
            text-decoration: underline;
          }
        }
      }
      .btn {
        margin-top: 10px;
        float: right;
        display: flex;
        /deep/ .el-upload {
          margin-right: 10px;
        }
      }
    }
  }
}
</style>