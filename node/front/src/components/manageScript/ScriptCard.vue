<template>
  <div class="script-card">
    <div class="add" v-if="cardType === 'add'">
      <el-upload
        :action="action"
        :multiple="false"
        accept=".zip"
        :show-file-list="false"
        :on-success="successHandle"
      >
        <div class="text">
          <el-icon><Plus /></el-icon>
          新增脚本
        </div>
      </el-upload>
    </div>

    <div class="script" v-if="cardType === 'script'">
      <div class="top">
        <div class="img">
          <img src="/script-color.png" />
        </div>
        <div class="content">
          <div class="title">
            <span v-html="replaceHandle(scriptPojo.name)" />
          </div>
          <div class="description">
            <span v-html="replaceHandle(scriptPojo.description)" />
          </div>
        </div>
      </div>
      <div class="bottom">
        <div class="left" @click="detailClick">查看详情</div>
        <div class="divider"></div>
        <div class="right">操作</div>
      </div>
    </div>
  </div>
</template>


<script lang="ts">
import { computed, defineComponent, PropType, ref } from "vue";
import { ScriptPojo } from "@/type";
import { prefix } from "@/prefix";
import ScriptInfo from "./ScriptInfo.vue";
import { notice } from "@/utils/common";
export default defineComponent({
  props: {
    keyword: {
      type: String,
    },
    cardType: {
      type: String,
    },
    scriptPojo: {
      type: Object as PropType<ScriptPojo>,
    },
  },
  emits: ["scriptCardCall", "refresh"],
  components: { ScriptInfo },
  setup(props, context) {
    const scriptInfoDialog = ref(false);
    const action = `${prefix}script/addScript`;
    const cardType = computed(() => {
      return props.cardType;
    });

    const scriptPojo = computed(() => {
      return props.scriptPojo;
    });

    const detailClick = () => {
      context.emit("scriptCardCall", scriptPojo.value!.id);
    };

    const successHandle = (response: any) => {
      if (response.code === 0) {
        context.emit("refresh");
      } else {
        notice("error", "错误", "请检查脚本压缩包");
      }
    };

    const replaceHandle = (currentStr: string) => {
      const res = new RegExp("(" + props.keyword + ")", "g");
      currentStr = currentStr.replace(
        res,
        "<span style='color:red;'>" + props.keyword + "</span>"
      );
      return currentStr;
    };

    return {
      action,
      cardType,
      scriptPojo,
      scriptInfoDialog,
      detailClick,
      successHandle,
      replaceHandle,
    };
  },
});
</script>

<style lang="scss" scoped>
.script-card {
  height: 100%;
  width: 100%;
  .add,
  .script {
    height: 100%;
    width: 100%;
    background: #ffffff;
  }

  .add {
    box-sizing: border-box;
    border: dashed 1px #8c8c8c;
    cursor: pointer;
    justify-content: center;
    display: flex;
    align-items: center;

    /deep/ .el-upload {
      height: 100%;
      width: 100%;
      .text {
        display: flex;
        color: #8c8c8c;
        font-size: 14px;
        .el-icon {
          margin-right: 5px;
          margin-top: 3px;
        }
      }
    }

    &:hover {
      border-color: #5fa9ff;
      .text {
        color: #5fa9ff;
      }
    }
  }
  .add > div {
    width: 100%;
    height: 100%;
  }

  .script {
    cursor: pointer;
    &:hover {
      box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2),
        0 6px 20px 0 rgba(0, 0, 0, 0.19);
    }
    transition: all 700ms;
    .top {
      height: calc(100% - 40px);
      box-sizing: border-box;
      border-bottom: solid 1px #f0f0f0;
      padding: 20px;
      display: flex;
      .img {
        width: 60px;
        img {
          width: 40px;
        }
      }
      .content {
        width: calc(100% - 60px);
        .title {
          font-weight: 500;
          height: 30px;
        }
        .description {
          font-size: 14px;
          color: #9b8c9b;
          line-height: 22px;
          display: -webkit-box;
          -webkit-line-clamp: 3;
          -webkit-box-orient: vertical;
          overflow: hidden;
        }
        &:hover {
          .title {
            color: #1890ff;
          }
        }
      }
    }
    .bottom {
      height: 40px;
      display: flex;
      .divider {
        height: 20px;
        border: solid 1px #f0f0f0;
        margin-top: 10px;
      }
      .left,
      .right {
        width: calc((100% - 2px) / 2);
        text-align: center;
        line-height: 40px;
        color: #9b8c9b;
        &:hover {
          color: #189eff;
        }
      }
    }
  }

  .my-header {
    font-size: 20px;
  }
}
</style>