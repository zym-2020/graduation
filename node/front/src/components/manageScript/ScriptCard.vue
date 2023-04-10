<template>
  <div class="script-card">
    <div class="add" v-if="cardType === 'add'">
      <div class="text">
        <el-icon><Plus /></el-icon>
        新增脚本
      </div>
    </div>
    <div class="script" v-if="cardType === 'script'">
      <div class="top">
        <div class="img">
          <img src="/script-color.png" />
        </div>
        <div class="content">
          <div class="title">{{ scriptConfig.name }}</div>
          <div class="description">
            {{ scriptConfig.description }}
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
import { computed, defineComponent, PropType } from "vue";
import { ScriptConfig } from "@/type";
import { getScriptConfig } from "@/api/request";
export default defineComponent({
  props: {
    cardType: {
      type: String,
    },
    scriptConfig: {
      type: Object as PropType<ScriptConfig>,
    },
  },
  setup(props) {
    const cardType = computed(() => {
      return props.cardType;
    });

    const scriptConfig = computed(() => {
      return props.scriptConfig;
    });

    const detailClick = async () => {
      const res = await getScriptConfig(scriptConfig.value!.id);
      if (res) {
        console.log(res);
      }
    };

    return { cardType, scriptConfig, detailClick };
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

    .text {
      display: flex;
      color: #8c8c8c;
      font-size: 14px;
      .el-icon {
        margin-right: 5px;
        margin-top: 3px;
      }
    }
    &:hover {
      border-color: #5fa9ff;
      .text {
        color: #5fa9ff;
      }
    }
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
}
</style>