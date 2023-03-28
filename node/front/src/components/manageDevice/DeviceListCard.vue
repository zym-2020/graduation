<template>
  <div class="device-list">
    <div class="title">
      <el-icon><List /></el-icon>设备列表
    </div>
    <div class="content">
      <el-scrollbar>
        <div v-for="(item, index) in deviceList" :key="index" class="card">
          <div>
            <img :src="imageUrl(item.picture, item.name)" />
          </div>
          <div class="text">
            <span @click="textClick([item.longitude, item.latitude])">{{
              item.name
            }}</span>
            <div class="last-update">
              最近更新：{{
                item.lastUpdate ? format(item.lastUpdate) : "暂无更新"
              }}
            </div>
          </div>
          <div :class="item.state === 1 ? 'state-dot' : 'state-dot stop'" />
          <div class="state">{{ item.state === 1 ? "运行中" : "关闭" }}</div>
          <div class="detail">
            <el-button type="primary" link @click="detailClick(item.id)"
              >查看详情</el-button
            >
          </div>
        </div>
      </el-scrollbar>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, PropType } from "vue";
import { DevicePojo } from "@/type";
import { imgBase64, dateFormat } from "@/utils/common";
import { prefix } from "@/prefix";
import router from "@/router";
export default defineComponent({
  props: {
    deviceList: {
      type: Object as PropType<DevicePojo[]>,
    },
  },
  emits: ["focusDeviceLocation"],
  setup(props, context) {
    const deviceList = computed(() => {
      return props.deviceList;
    });

    const imageUrl = (picture: string | null, name: string | null) => {
      if (picture === null || picture === "") {
        if (name === null || name === "") {
          return imgBase64("device");
        } else {
          return imgBase64(name);
        }
      } else {
        return prefix + `device/getPicture/${picture}`;
      }
    };

    const textClick = (param: [number, number]) => {
      context.emit("focusDeviceLocation", param);
    };

    const format = (time: string) => {
      return dateFormat(time, "yyyy-MM-dd hh:mm");
    };

    const detailClick = (id: string) => {
      router.push({
        name: "DeviceDetail",
        params: {
          id: id,
        },
      });
    };

    return {
      deviceList,
      imageUrl,
      textClick,
      format,
      detailClick,
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

.device-list {
  background: white;
  .title {
    box-sizing: border-box;
    border-bottom: solid 1px #f2f2f2;
    line-height: 60px;
    padding-left: 20px;
    .el-icon {
      position: relative;
      top: 3px;
      margin-right: 5px;
    }
  }
  .content {
    padding: 15px 0px;
    height: calc(100% - 90px);
    .el-scrollbar {
      height: 100%;
      padding: 0px 15px;
      .card {
        height: 80px;
        display: flex;
        margin-bottom: 15px;
        position: relative;
        cursor: pointer;
        img {
          height: 100%;
          width: 80px;
        }
        .text {
          width: calc(100% - 100px);
          margin-left: 20px;
          color: #7b809a;
          word-wrap: break-word;
          word-break: normal;
          .last-update {
            margin-top: 5px;
            font-size: 12px;
            color: #000000;
          }
          span:hover {
            color: #409eff;
          }
        }
        .state-dot {
          position: absolute;
          bottom: 3px;
          left: 100px;
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
        .state {
          position: absolute;
          bottom: 0px;
          left: 115px;
          font-size: 10px;
          color: #dadbe2;
        }
        .detail {
          position: absolute;
          bottom: 0px;
          right: 10px;
        }
      }
    }
  }
}
</style>