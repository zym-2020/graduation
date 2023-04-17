<template>
  <div class="typing-device">
    <div class="title">
      <svg class="icon-svg">
        <use xlink:href="#icon-23input"></use>
      </svg>
      手动录入
    </div>
    <div class="content" ref="content">
      <el-form
        label-width="80px"
        :model="form"
        ref="formInstance"
        :rules="rules"
        :disabled="disabledFlag"
      >
        <el-form-item label="录入类型" prop="keys">
          <el-select v-model="form.type">
            <el-option label="文件录入" value="file" />
            <el-option label="文本录入" value="data" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据列名" v-if="form.type === 'data'">
          <el-scrollbar :max-height="maxHeight">
            <div class="column" v-for="(item, index) in form.keys" :key="index">
              <el-input v-model="form.keys[index]" />
              <el-icon @click="closeClick(index)"><CircleClose /></el-icon>
            </div>
            <el-button @click="addColumnClick"
              ><el-icon><Plus /></el-icon>添加列</el-button
            >
            <el-icon class="full" @click="fullScreenShow = true"
              ><FullScreen
            /></el-icon>
          </el-scrollbar>
        </el-form-item>
      </el-form>
      <el-button
        type="primary"
        class="commit"
        @click="addClick"
        :disabled="disabledFlag"
        >挂载新设备</el-button
      >
    </div>

    <el-dialog v-model="fullScreenShow" :width="600">
      <full-screen-component
        :keys="form.keys"
        @fullScreenComponentCall="fullScreenComponentCall"
        v-if="fullScreenShow"
      />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, reactive } from "vue";
import FullScreenComponent from "./FullScreenComponent.vue";
import type { FormInstance, FormRules } from "element-plus";
import { notice } from "@/utils/common";
import { DeviceAttribute, DeviceConfig } from "@/type";
import { initDevice } from "@/api/request";
export default defineComponent({
  components: { FullScreenComponent },
  props: {
    typeValue: {
      type: String,
    },
  },
  emits: ["checkAttribute"],
  setup(props, context) {
    const content = ref<HTMLElement>();
    const form = ref<{ type: string; keys: string[] }>({
      type: "",
      keys: [],
    });
    const fullScreenShow = ref(false);
    const formInstance = ref<FormInstance>();

    const rules = reactive<FormRules>({
      keys: [{ required: true, trigger: "blur", message: "录入类型不得为空" }],
    });

    const disabledFlag = computed(() => {
      if (props.typeValue === "typing") {
        return false;
      } else {
        return true;
      }
    });

    const maxHeight = computed(() => {
      return content.value!.clientHeight - 40 - 50 - 40;
    });

    const addClick = async () => {
      if (form.value.keys.length > 0) {
        for (let i = 0; i < form.value.keys.length; i++) {
          if (form.value.keys[i] === "") {
            notice("warning", "警告", "列名不得为空");
            return;
          }
        }
        await formInstance.value!.validate((valid) => {
          if (valid) {
            context.emit(
              "checkAttribute",
              async (val: { attribute: DeviceAttribute; flag: boolean }) => {
                if (val.flag) {
                  const jsonData: Omit<DeviceConfig, "id"> = {
                    deviceConfigAttribute: val.attribute,
                    typing: {
                      type: form.value.type,
                      keys: {
                        keyList: form.value.keys,
                      },
                      storage: "/",
                    },
                  };
                  const res = await initDevice(jsonData);
                  if (res) {
                    notice("success", "成功", "添加新设备");
                  }
                }
              }
            );
          }
        });
      } else {
        notice("warning", "警告", "数据列必须>=1");
      }
    };

    const addColumnClick = () => {
      form.value.keys.push("");
    };

    const closeClick = (index: number) => {
      form.value.keys.splice(index, 1);
    };

    const fullScreenComponentCall = (val: string[]) => {
      form.value.keys = val;
      fullScreenShow.value = false;
    };

    return {
      form,
      content,
      maxHeight,
      fullScreenShow,
      formInstance,
      rules,
      disabledFlag,
      closeClick,
      addColumnClick,
      fullScreenComponentCall,
      addClick,
    };
  },
});
</script>

<style lang="scss" scoped>
.typing-device {
  background: white;
  .title {
    height: 60px;
    box-sizing: border-box;
    border-bottom: solid 1px #f2f2f2;
    line-height: 60px;
    padding-left: 20px;
    .icon-svg {
      width: 20px;
      height: 20px;
      position: relative;
      top: 5px;
    }
  }
  .content {
    padding: 20px;
    height: calc(100% - 100px);
    position: relative;
    .el-scrollbar {
      width: 100%;
      .column {
        width: 100%;
        margin-bottom: 5px;
        .el-input {
          width: calc(100% - 30px);
        }
        .el-icon {
          position: relative;
          top: 2px;
          left: 5px;
          color: #bbabb2;
          cursor: pointer;
          &:hover {
            color: #3c92ea;
          }
        }
      }
      .el-button {
        margin-bottom: 2px;
      }
      .full {
        font-size: 25px;
        position: relative;
        top: 8px;
        left: 5px;
        cursor: pointer;
        &:hover {
          color: #3c92ea;
        }
      }
    }
    .commit {
      position: absolute;
      bottom: 20px;
      left: 20px;
    }
  }
}
</style>