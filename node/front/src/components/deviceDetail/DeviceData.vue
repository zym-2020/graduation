<template>
  <div class="device-data">
    <div class="head">
      <svg fill="currentcolor" viewBox="0 0 256 256" style="color: #081c42">
        <g>
          <path
            d="M244.1,8.4c-3.9-5.3-10.1-8.5-16.7-8.5H21.6C15,0,8.8,3.1,4.9,8.4C0.8,14-0.9,21,0.3,27.9 c5.1,29.6,15.8,91.9,24.3,141.7v0.1C29,195,32.8,217.1,35,229.9c1.4,10.8,10.4,18.9,21.3,19.3h136.5 c10.9-0.4,19.9-8.5,21.3-19.3l10.3-60.1l0.1-0.4L238.4,88v-0.2l10.3-59.9C249.9,21,248.3,14,244.1,8.4 M206.1,177h-163 l-3.2-18.6h169.3L206.1,177z M220,95.3H28.9l-3.2-18.6h197.4L220,95.3z"
          ></path>
        </g>
      </svg>
      <div class="title">
        <div class="title-one">工作空间</div>
        <div class="title-two">
          <span class="key">设备：</span>
          <span class="value">{{
            deviceConfig.deviceConfigAttribute.name
          }}</span>
          &ensp;&ensp;&ensp;
          <span class="key">类型：</span>
          <span class="value">{{ type }}</span>
        </div>
      </div>
      <div class="btn">
        <el-button class="upload" color="white" @click="uploadClick"
          >上传<el-icon color="#081c42"><Upload /></el-icon
        ></el-button>
        <el-button color="#081c42" @click="refresh"
          >刷新<el-icon color="white"><Refresh /></el-icon
        ></el-button>

        <div class="upload-menu" v-if="uploadFlag"></div>
      </div>
    </div>
    <div class="path">
      <div class="left-icon" @click="backClick">
        <el-icon><ArrowLeftBold /></el-icon>
      </div>
      <div class="path-name">
        <svg fill="currentcolor" viewBox="0 0 256 256">
          <g>
            <path
              d="M23.4,121.5c-11.5,0-21.4,9.8-21.4,21.2c0.2,11.8,9.7,21.2,21.4,21.4 c11.4,0,21.2-9.9,21.2-21.4C44.3,131.1,35,121.7,23.4,121.5"
            ></path>
            <path
              d="M23.4,175.4c-11.5,0-21.4,9.8-21.4,21.2c0.2,11.8,9.7,21.2,21.4,21.4 c11.4,0,21.2-9.9,21.2-21.4C44.3,184.9,35,175.6,23.4,175.4"
            ></path>
            <path
              d="M158.6,40.2h-12.2c-4.3,0-8.3,2.5-10.2,6.4l-76.6,157c-2.7,5.6-0.4,12.4,5.2,15.2 c1.6,0.8,3.3,1.2,5,1.2H82c4.3,0,8.3-2.5,10.2-6.4l76.6-157c2.7-5.6,0.4-12.4-5.2-15.2C162,40.6,160.3,40.2,158.6,40.2"
            ></path>
            <path
              d="M205,121.1c-1.2,0-2.4,0.1-3.6,0.1L233,56.5c2.7-5.6,0.4-12.4-5.2-15.2 c-1.6-0.8-3.3-1.2-5-1.2h-12.2c-4.3,0-8.3,2.5-10.2,6.4l-76.6,157c-2.7,5.6-0.4,12.4,5.2,15.2c1.6,0.8,3.3,1.2,5,1.2h12.2 c4.3,0,8.3-2.5,10.2-6.4L165,196c14.8,22.1,44.7,28.1,66.8,13.3s28.1-44.7,13.3-66.8C236.2,129.1,221.1,121.1,205,121.1 M205.3,207.3c-21,0-38.1-17-38.1-38.1c0-21,17-38.1,38.1-38.1c21,0,38.1,17,38.1,38.1c0,0,0,0,0,0 C243.4,190.3,226.3,207.3,205.3,207.3"
            ></path>
            <path
              d="M211.3,151.3h-11.9v11.9h-11.9v11.9h11.9v11.9h11.9v-11.9h11.9v-11.9h-11.9V151.3z"
            ></path>
          </g>
        </svg>
        <div v-for="(item, index) in path" :key="index">
          <span class="path-text">{{ item }}</span>
          <span v-if="index != path.length - 1">&ensp;/&ensp;</span>
        </div>
      </div>
    </div>

    <div class="content" ref="contentComponent">
      <el-empty description="暂无数据" v-if="dataList.length === 0" />
      <el-table
        :data="dataList"
        :max-height="tableHeight"
        @row-dblclick="rowDblclickHandle"
        v-loading="loading"
        v-else
      >
        <el-table-column label="名称">
          <template #default="scope">
            <svg
              fill="currentcolor"
              viewBox="0 0 256 256"
              v-if="scope.row.type === 'folder'"
            >
              <defs>
                <clipPath id="prefix__a">
                  <path d="M0 0h256v256H0z"></path>
                </clipPath>
              </defs>
              <g data-name="Object Browser" clip-path="url(#prefix__a)">
                <g data-name="Grupo 1541" transform="translate(87.918 103.898)">
                  <circle
                    data-name="Elipse 57"
                    cx="11.515"
                    cy="11.515"
                    r="11.515"
                    transform="rotate(-10.901 280.738 -178.561)"
                  ></circle>
                  <rect
                    data-name="Rect\xE1ngulo 805"
                    width="24.592"
                    height="20.853"
                    rx="1.35"
                    transform="translate(14.546 25.545)"
                  ></rect>
                  <path
                    data-name="Trazado 365"
                    d="M28.151 60.295a2.427 2.427 0 00-4.2 0l-9.1 15.761a2.425 2.425 0 002.1 3.64h18.2a2.43 2.43 0 002.105-3.64z"
                  ></path>
                  <path
                    data-name="Trazado 366"
                    d="M79.273 28.199a151.334 151.334 0 00-.187-17.51c-.395-4.294-2.262-7.942-6.512-9.468a15.5 15.5 0 00-1.836-.529 38.335 38.335 0 00-7.332-.658c-4.289-.125-8.57.136-12.855.116-8.582-.036-17.16.116-25.746.152H6.301a6.308 6.308 0 00-6.3 6.3v80.617a6.307 6.307 0 006.3 6.3h66.684a6.3 6.3 0 006.3-6.3V47.054c-.004-6.273-.168-12.584-.012-18.855zm-7.648 53.334a5.435 5.435 0 01-5.434 5.439h-54.2a5.442 5.442 0 01-5.441-5.439V12.3a5.441 5.441 0 015.441-5.442h36.367v9.3a13.809 13.809 0 0013.789 13.794h9.48zm0-57.6h-9.48a7.781 7.781 0 01-7.773-7.777v-9.3h11.82a5.435 5.435 0 015.434 5.442z"
                  ></path>
                </g>
                <path
                  data-name="Trazado 367"
                  d="M101.585 42.067c6.6 0 13.672 18.858 20.742 18.858h87.934a9.453 9.453 0 019.426 9.429v4.715H40.292V51.496h-.234a9.455 9.455 0 019.426-9.429h52.1m124.219 44.5a9.8 9.8 0 019.773 9.772L225.56 204.095a9.8 9.8 0 01-9.773 9.771H39.615a9.8 9.8 0 01-9.773-9.771L20.065 96.339a9.806 9.806 0 019.777-9.772h195.961M101.584 21.999h-52.1a29.528 29.528 0 00-29.492 29.5 20.028 20.028 0 00.234 3.081v13.513A29.9 29.9 0 00-.001 96.344c0 .605.031 1.208.086 1.814l9.711 107.089a29.874 29.874 0 0029.82 28.691h176.172a29.873 29.873 0 0029.813-28.663l9.961-107.074c.051-.617.082-1.239.082-1.857a29.875 29.875 0 00-15.887-26.376 29.534 29.534 0 00-29.5-29.106H128.87c-.4-.532-.785-1.059-1.121-1.517-5.094-6.906-12.785-17.342-26.168-17.342z"
                ></path>
              </g>
            </svg>
            <svg
              fill="currentcolor"
              viewBox="0 0 256 256"
              v-if="scope.row.type === 'file'"
            >
              <defs>
                <clipPath id="prefix__a">
                  <path d="M0 0h256v256H0z"></path>
                </clipPath>
              </defs>
              <g clip-path="url(#prefix__a)">
                <path fill="none" d="M0 0h256v256H0z"></path>
                <path
                  data-name="Trazado 426"
                  d="M235.995 77.197c.388-15.753.958-32.242-.5-47.941-1.094-11.756-6.193-21.735-17.831-25.914a42.4 42.4 0 0 0-5.017-1.448c-6.549-1.478-13.432-1.6-20.09-1.8-11.726-.343-23.448.366-35.175.314-23.493-.1-46.985.323-70.479.414q-4.228.018-8.455.017H36.241A17.26 17.26 0 0 0 19 18.08v220.679A17.261 17.261 0 0 0 36.241 256h182.542a17.26 17.26 0 0 0 17.24-17.241V128.815c.001-17.183-.45-34.459-.028-51.618Zm-20.922 145.988a14.888 14.888 0 0 1-14.888 14.888H51.816a14.888 14.888 0 0 1-14.892-14.884V33.661a14.888 14.888 0 0 1 14.888-14.888h99.548v25.459a37.8 37.8 0 0 0 37.764 37.757h25.951Zm0-157.656h-25.949a21.321 21.321 0 0 1-21.3-21.3V18.77h32.359a14.888 14.888 0 0 1 14.888 14.888Z"
                ></path>
                <path
                  data-name="Trazado 427"
                  d="M130.985 69.292h-69.1a8.455 8.455 0 0 1-8.455-8.455 8.455 8.455 0 0 1 8.455-8.455h69.1a8.455 8.455 0 0 1 8.455 8.455 8.455 8.455 0 0 1-8.455 8.455Z"
                ></path>
                <path
                  data-name="Trazado 428"
                  d="M130.985 102.607h-69.1a8.455 8.455 0 0 1-8.455-8.455 8.455 8.455 0 0 1 8.455-8.455h69.1a8.455 8.455 0 0 1 8.455 8.455 8.455 8.455 0 0 1-8.455 8.455Z"
                ></path>
                <path
                  data-name="Trazado 429"
                  d="M183.891 136.458H61.876a8.455 8.455 0 0 1-8.455-8.455 8.455 8.455 0 0 1 8.455-8.455h122.011a8.455 8.455 0 0 1 8.455 8.455 8.456 8.456 0 0 1-8.451 8.455Z"
                ></path>
                <path
                  data-name="Trazado 430"
                  d="M183.891 170.309H61.876a8.455 8.455 0 0 1-8.455-8.455 8.455 8.455 0 0 1 8.455-8.455h122.011a8.455 8.455 0 0 1 8.455 8.455 8.456 8.456 0 0 1-8.451 8.455Z"
                ></path>
                <path
                  data-name="Trazado 431"
                  d="M183.891 204.164H61.876a8.455 8.455 0 0 1-8.455-8.455 8.455 8.455 0 0 1 8.455-8.455h122.011a8.455 8.455 0 0 1 8.455 8.455 8.456 8.456 0 0 1-8.451 8.455Z"
                ></path>
                <path
                  data-name="Rect\xE1ngulo 908"
                  fill="none"
                  d="M0 0h256v256H0z"
                ></path>
              </g>
            </svg>
            <span style="margin-left: 10px">{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column label="修改日期" width="200">
          <template #default="scope">
            <span>{{
              dateFormat(scope.row.lastUpdate, "yyyy-MM-dd hh:mm")
            }}</span>
          </template>
        </el-table-column>
        <el-table-column label="大小" width="140">
          <template #default="scope">
            <span>{{ formatSize(scope.row.type, scope.row.size) }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script lang="ts">
import {
  computed,
  defineComponent,
  nextTick,
  onMounted,
  PropType,
  ref,
} from "vue";
import { dateFormat, formatFileSize } from "@/utils/common";
import { TableDataType } from "@/type";
import { getDeviceData } from "@/api/request";
import { DeviceConfig } from "@/type";
import router from "@/router";
export default defineComponent({
  props: {
    dataList: {
      type: Object as PropType<TableDataType[]>,
    },
    deviceConfig: {
      type: Object as PropType<DeviceConfig>,
    },
  },
  setup(props) {
    const tableHeight = ref(0);
    const contentComponent = ref<HTMLElement>();
    const path = ref<string[]>([]);
    const loading = ref(false);
    const dataList = ref(props.dataList);
    const uploadFlag = ref(false);
    const deviceConfig = computed(() => {
      return props.deviceConfig;
    });

    const type = computed(() => {
      if (deviceConfig.value?.push) {
        return "主动推送";
      } else if (deviceConfig.value?.typing) {
        if (deviceConfig.value.typing.type === "input") {
          return "手动录入（输入）";
        } else {
          return "手动录入（文件）";
        }
      }
    });

    const formatSize = (type: "file" | "folder", size: number) => {
      if (type === "folder") {
        return "-";
      } else {
        return formatFileSize(size);
      }
    };

    const rowDblclickHandle = async (row: TableDataType) => {
      if (row.type === "folder") {
        const id = router.currentRoute.value.params.id;
        loading.value = true;
        let p = "";
        path.value.forEach((item) => {
          p += item + "/";
        });
        p += row.name;
        const res = await getDeviceData(id as string, { path: p });
        if (res) {
          dataList.value = res.data;
          path.value.push(row.name);
          loading.value = false;
        }
      }
    };

    const backClick = async () => {
      if (path.value.length > 0) {
        const id = router.currentRoute.value.params.id;
        loading.value = true;
        let p = "";
        if (path.value.length <= 1) {
          p = "/";
        } else {
          for (let i = 0; i < path.value.length - 1; i++) {
            p += path.value[i] + "/";
          }
          p = p.substring(0, -1);
        }
        const res = await getDeviceData(id as string, { path: p });
        if (res) {
          dataList.value = res.data;
          path.value.splice(path.value.length - 1, 1);
          loading.value = false;
        }
      }
    };

    const refresh = async () => {
      let p = "";
      if (path.value.length > 0) {
        p = path.value[0];
        for (let i = 1; i < path.value.length; i++) {
          p += "/" + path.value[i];
        }
      } else {
        p = "/";
      }
      const id: string = router.currentRoute.value.params.id as string;
      loading.value = true;
      const res = await getDeviceData(id, { path: p });
      if (res) {
        dataList.value = res.data;
        loading.value = false;
      }
    };

    const uploadClick = () => {
      uploadFlag.value = true;
      console.log(1)
      function showUpload() {
        console.log(2)
        uploadFlag.value = false;
        window.removeEventListener("click", showUpload);
      }
      window.addEventListener("click", showUpload);
    };

    nextTick(() => {
      tableHeight.value = contentComponent.value!.clientHeight - 40;
    });

    return {
      contentComponent,
      tableHeight,
      path,
      dataList,
      loading,
      deviceConfig,
      type,
      uploadFlag,
      dateFormat,
      formatSize,
      rowDblclickHandle,
      backClick,
      refresh,
      uploadClick
    };
  },
});
</script>

<style lang="scss" scoped>
@-webkit-keyframes scale-up-top {
  0% {
    -webkit-transform: scale(0.5);
    transform: scale(0.5);
    -webkit-transform-origin: 50% 0%;
    transform-origin: 50% 0%;
  }
  100% {
    -webkit-transform: scale(1);
    transform: scale(1);
    -webkit-transform-origin: 50% 0%;
    transform-origin: 50% 0%;
  }
}
@keyframes scale-up-top {
  0% {
    -webkit-transform: scale(0.5);
    transform: scale(0.5);
    -webkit-transform-origin: 50% 0%;
    transform-origin: 50% 0%;
  }
  100% {
    -webkit-transform: scale(1);
    transform: scale(1);
    -webkit-transform-origin: 50% 0%;
    transform-origin: 50% 0%;
  }
}

.device-data {
  background: white;
  height: calc(100vh - 40px - 70px);

  .head {
    height: 80px;
    padding: 20px;
    box-sizing: border-box;
    border-bottom: solid 1px #e4e7ed;
    position: relative;
    svg {
      width: 25px;
    }

    .title {
      position: absolute;
      top: 20px;
      left: 60px;
      font-family: "Helvetica Neue", Helvetica, "PingFang SC",
        "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
      .title-one {
        margin-bottom: 5px;
        color: #081c42;
        font-size: 16px;
        font-weight: 600;
      }
      .title-two {
        color: #969fa8;
        font-size: 12px;
        .value {
          font-weight: 700;
        }
      }
    }

    .btn {
      position: absolute;
      right: 20px;
      top: 20px;
      .upload {
        box-sizing: border-box;
        border: solid 1px #081c42;
        color: #081c42;
        &:hover {
          background: #f5f6f8;
        }
      }
      .el-icon {
        margin-left: 5px;
      }

      .upload-menu {
        background: #ffffff;
        position: absolute;
        box-shadow: rgba(0, 0, 0, 0.2) 0px 5px 5px -3px,
          rgba(0, 0, 0, 0.14) 0px 8px 10px 1px, rgba(0, 0, 0, 0.12) 0px 3px 14px;
        border: solid 1px #e4e7ed;
        width: 150px;
        height: 90px;
        top: 32px;
        z-index: 99;
        right: 55px;
        -webkit-animation: scale-up-top 0.4s cubic-bezier(0.39, 0.575, 0.565, 1)
          both;
        animation: scale-up-top 0.4s cubic-bezier(0.39, 0.575, 0.565, 1) both;
      }
    }
  }

  .path {
    height: 40px;
    box-sizing: border-box;
    border-bottom: solid 1px #e4e7ed;
    display: flex;
    .left-icon {
      width: 40px;
      box-sizing: border-box;
      border-right: solid 1px #e4e7ed;
      cursor: pointer;
      &:hover {
        background: #f2f2f3;
      }
      .el-icon {
        color: #757575;
        width: 40px;
        height: 40px;
      }
    }
    .path-name {
      background: #fcfcfd;
      width: calc(100% - 40px);
      display: flex;
      svg {
        width: 20px;
        color: #7a7a7a;
        margin-left: 10px;
        // margin-top: 10px;
        margin-right: 15px;
        cursor: pointer;
      }
      .path-text {
        cursor: pointer;
        &:hover {
          text-decoration: underline;
        }
      }
      span {
        font-size: 8px;
        line-height: 40px;
        color: #969fa8;
        font-weight: 800;
      }
    }
  }

  .content {
    padding: 20px;
    height: calc(100% - 160px);
    .el-table {
      font-size: 8px;
      cursor: pointer;
      svg {
        width: 15px;
        position: relative;
        top: 3px;
      }

      /deep/ .el-table__row {
        &:hover {
          color: #393939;
          font-weight: 600;
        }
      }
    }
  }
}
</style>