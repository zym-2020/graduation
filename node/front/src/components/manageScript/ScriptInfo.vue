<template>
  <div class="script-info">
    <div class="description">{{ scriptConfig.description }}</div>
    <el-divider />
    <div class="parameter">
      <div class="title">脚本参数</div>
      <div class="content">
        <el-row class="t1">
          <el-col :span="4">参数名</el-col>
          <el-col :span="6">示例</el-col>
          <el-col :span="14">描述</el-col>
        </el-row>
        <el-row
          v-for="(item, index) in scriptConfig.parameters.parameterList"
          :key="index"
        >
          <el-col :span="4" class="name">
            <el-badge is-dot class="item"
              ><span>{{ item.name }}</span></el-badge
            >
          </el-col>
          <el-col :span="6" class="example">{{ item.example }}</el-col>
          <el-col :span="14" class="description">{{ item.description }}</el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, PropType } from "vue";
import { ScriptConfig } from "@/type";
export default defineComponent({
  props: {
    scriptConfig: {
      type: Object as PropType<ScriptConfig>,
    },
  },
  setup(props) {
    const scriptConfig = computed(() => {
      return props.scriptConfig;
    });

    return {
      scriptConfig,
    };
  },
});
</script>

<style lang="scss" scoped>
.script-info {
  .parameter {
    .title {
      font-size: 18px;
    }
    .content {
      margin-top: 15px;
      .t1 {
        color: #000000;
        font-size: 14px;
        font-weight: 600;
      }
      .name,
      .example,
      .description {
        margin-top: 10px;
      }
      .name {
        /deep/.el-badge__content.is-dot {
          height: 4px;
          width: 4px;
        }
        span {
          color: #000000;
          background: #f5f5f5;
          padding: 2px 5px;
          border-radius: 4px;
          font-size: 12px;
        }
      }
      .example {
        color: #b2b2cb;
        font-size: 11px;
      }
      .description {
        word-wrap: normal;
      }
    }
  }
}
</style>