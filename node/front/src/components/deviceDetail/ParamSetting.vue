<template>
  <div class="param-setting">
    <el-skeleton :rows="5" animated v-if="skeletonFlag" />
    <div v-else>
      <el-row>
        <el-col :span="4" class="title">参数名</el-col>
        <el-col :span="4" class="title">示例参数</el-col>
        <el-col :span="6" class="title">参数描述</el-col>
        <el-col :span="8" class="title">实参</el-col>
      </el-row>
      <el-divider />
      <div v-for="(item, index) in params" :key="index">
        <el-row>
          <el-col :span="4">
            <div class="label">
              <svg class="icon-svg" v-if="!item.optional">
                <use xlink:href="#icon-bixuan"></use>
              </svg>
              <span>{{ item.name }}</span>
            </div>
          </el-col>
          <el-col :span="4">
            <div class="example">
              {{ item.example }}
            </div>
          </el-col>
          <el-col :span="6">
            <div class="description">
              {{ item.description }}
            </div>
          </el-col>
          <el-col :span="8">
            <div
              class="file-path"
              v-if="item.type === 'file' || item.type === 'path'"
            >
              <el-input disabled v-model="parameterList[index]" />
              <el-button type="success" circle
                ><el-icon><FolderOpened /></el-icon
              ></el-button>
            </div>
            <div class="input" v-if="item.type === 'input'">
              <el-input v-model="parameterList[index]" />
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="btn">
        <el-button>确定</el-button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, PropType, ref } from "vue";
import { getScriptConfig } from "@/api/request";
import { ScriptConfig } from "@/type";
export default defineComponent({
  props: {
    scriptId: {
      type: String,
    },
    parameterList: {
      type: Object as PropType<Array<string>>,
    },
  },
  setup(props) {
    const skeletonFlag = ref(true);
    const scriptConfig = ref<ScriptConfig>();

    const params = computed(() => {
      return scriptConfig.value?.parameters.parameterList;
    });
    const parameterList = ref<string[] | undefined>(
      props.parameterList ? props.parameterList : []
    );

    const init = async () => {
      const res = await getScriptConfig(props.scriptId!);
      if (res) {
        scriptConfig.value = res.data;
      }
    };

    onMounted(async () => {
      skeletonFlag.value = true;
      await init();
      skeletonFlag.value = false;
      console.log(props.parameterList, parameterList.value);
    });

    return { skeletonFlag, scriptConfig, params, parameterList };
  },
});
</script>

<style lang="scss" scoped>
.param-setting {
  .des {
    font-size: 17px;
    margin-bottom: 20px;
  }
  .title {
    font-weight: 500;
  }
  .el-divider {
    margin-top: 10px;
  }

  .el-row {
    margin-top: 10px;
  }
  .label {
    font-size: 12px;
    width: calc(100% - 15px);
    display: flex;
    color: black;
    word-wrap: break-word;
    word-break: break-all;
    .icon-svg {
      width: 10px;
      height: 10px;
      //   margin-top: 10px;
    }
  }
  .file-path {
    .el-input {
      width: calc(100% - 50px);
      margin-right: 10px;
    }
  }
  .input {
    .el-input {
      width: calc(100% - 50px);
    }
  }
  .description,
  .example {
    color: #9292b5;
    font-size: 12px;
    width: calc(100% - 15px);
    word-wrap: break-word;
    word-break: break-all;
  }

  .btn {
    position: relative;
    height: 30px;
    .el-button {
      position: absolute;
      right: 0px;
      box-sizing: border-box;
      border: solid 1px #081c42;
      color: #081c42;
      &:hover {
        background: #f5f6f8;
      }
    }
  }
}
</style>