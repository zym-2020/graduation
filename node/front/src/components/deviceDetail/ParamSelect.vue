<template>
  <div class="param-select">
    <div class="path">
      <div class="back" @click="backClick">
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
    <el-skeleton :rows="5" animated v-if="skeletonFlag" />
    <div class="content">
      <el-table
        :data="dataList"
        max-height="350"
        @row-dblclick="rowDblclickHandle"
        @row-click="rowClickHandle"
        v-loading="loading"
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
    <div class="comfirm">
      <div class="file-folder">
        <div class="title" v-if="paramType === 'path'">文件夹：</div>
        <div class="title" v-else>文件名：</div>
        <div class="text-box">{{ result }}</div>
      </div>
      <div class="btn">
        <el-button
          color="#081c42"
          :disabled="disabledFlag"
          @click="comfirmClick"
          >{{ paramType === "path" ? "选择文件夹" : "选择文件" }}</el-button
        >
        <el-button class="cancel" @click="cancelClick">取消</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, ref } from "vue";
import { TableDataType } from "@/type";
import { dateFormat, formatFileSize } from "@/utils/common";
import { getDeviceData, getDeviceFolder } from "@/api/request";
import router from "@/router";
export default defineComponent({
  props: {
    paramType: {
      type: String,
    },
  },
  emits: ["cancelCall", "confirmCall"],
  setup(props, context) {
    const skeletonFlag = ref(true);
    const loading = ref(false);
    const dataList = ref<TableDataType[]>([]);
    const path = ref<string[]>([]);
    const result = ref("");
    const disabledFlag = computed(() => {
      return result.value === "" ? true : false;
    });
    const paramType = computed(() => {
      return props.paramType;
    });

    const rowClickHandle = (row: TableDataType) => {
      if (paramType.value === "path") {
        result.value = row.name;
      } else {
        if (row.type === "file") {
          result.value = row.name;
        }
      }
    };

    const rowDblclickHandle = async (row: TableDataType) => {
      if (row.type === "folder") {
        const id = router.currentRoute.value.params.id as string;
        loading.value = true;
        let p = "";
        path.value.forEach((item) => {
          p += item + "/";
        });
        p += row.name;
        if (paramType.value === "path") {
          const res = await getDeviceFolder({ deviceId: id, path: p });
          if (res) {
            dataList.value = res.data;
            path.value.push(row.name);
            loading.value = false;
          }
        } else {
          const res = await getDeviceData(id as string, { path: p });
          if (res) {
            dataList.value = res.data;
            path.value.push(row.name);
            loading.value = false;
          }
        }
      }
    };

    const backClick = async () => {
      if (path.value.length > 0) {
        const id = router.currentRoute.value.params.id as string;
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
        if (paramType.value === "path") {
          const res = await getDeviceFolder({ deviceId: id, path: p });
          if (res) {
            dataList.value = res.data;
            path.value.splice(path.value.length - 1, 1);
            loading.value = false;
            result.value =
              path.value.length > 0 ? path.value[path.value.length - 1] : "";
          }
        } else {
          const res = await getDeviceData(id, { path: p });
          if (res) {
            dataList.value = res.data;
            path.value.splice(path.value.length - 1, 1);
            loading.value = false;
            result.value = "";
          }
        }
      }
    };

    const cancelClick = () => {
      context.emit("cancelCall");
    };

    const comfirmClick = () => {
      context.emit("confirmCall", { path: path.value, result: result.value });
    };

    const formatSize = (type: "file" | "folder", size: number) => {
      if (type === "folder") {
        return "-";
      } else {
        return formatFileSize(size);
      }
    };

    const init = async () => {
      skeletonFlag.value = true;
      if (paramType.value === "path") {
        const res = await getDeviceFolder({
          deviceId: router.currentRoute.value.params.id as string,
          path: "/",
        });
        if (res) {
          dataList.value = res.data;
        }
      } else {
        const res = await getDeviceData(
          router.currentRoute.value.params.id as string,
          { path: "/" }
        );
        if (res) {
          dataList.value = res.data;
        }
      }
      skeletonFlag.value = false;
    };

    onMounted(async () => {
      await init();
    });

    return {
      loading,
      dataList,
      skeletonFlag,
      path,
      paramType,
      result,
      disabledFlag,
      formatSize,
      dateFormat,
      rowDblclickHandle,
      rowClickHandle,
      backClick,
      cancelClick,
      comfirmClick,
    };
  },
});
</script>

<style lang="scss" scoped>
.param-select {
  .path {
    height: 40px;
    width: 100%;
    box-sizing: border-box;
    background: #fcfcfd;
    border: solid 1px #e4e7ed;
    border-radius: 4px;
    display: flex;
    .back {
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
    margin: 10px 0px;

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

  .comfirm {
    height: 70px;
    position: relative;
    .file-folder {
      float: right;
      height: 30px;
      width: 70%;
      display: flex;
      .title {
        width: 50px;
        line-height: 30px;
        color: #7a7a7a;
        font-weight: 600;
        font-size: 12px;
      }
      .text-box {
        width: calc(100% - 50px);
        border: solid 1px #e4e7ed;
        box-sizing: border-box;
        border-radius: 4px;
        background: #fcfcfd;
        line-height: 30px;
        padding-left: 10px;
      }
    }
    .btn {
      position: absolute;
      right: 0px;
      bottom: 0px;
      .cancel {
        box-sizing: border-box;
        border: solid 1px #081c42;
        color: #081c42;
        &:hover {
          background: #f5f6f8;
        }
      }
    }
  }
}
</style>