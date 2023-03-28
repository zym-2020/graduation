<template>
  <div class="push-device" v-loading="loading">
    <div class="title">
      <el-icon><Sort /></el-icon>
      主动推送
    </div>
    <div class="content">
      <el-form
        :inline="true"
        :model="form"
        :disabled="disabledFlag"
        ref="formInstance"
        :rules="rules"
      >
        <el-form-item label="协议" prop="protocol">
          <el-select v-model="form.protocol">
            <el-option
              v-for="item in options"
              :key="item"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="监听端口" prop="port">
          <el-input v-model="form.port" @change="changeHandle"
            ><template #append>
              <el-button @click="checkClick">检验端口</el-button>
            </template></el-input
          >
        </el-form-item>
      </el-form>
      <el-button
        type="primary"
        class="commit"
        :disabled="disabledFlag"
        @click="addClick(formInstance)"
        >挂载新设备</el-button
      >
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, reactive } from "vue";
import { checkPort, initDevice } from "@/api/request";
import { notice } from "@/utils/common";
import type { FormInstance, FormRules } from "element-plus";
import { DeviceAttribute, DeviceConfig } from "@/type";
export default defineComponent({
  props: {
    typeValue: {
      type: String,
    },
  },
  emits: ["checkAttribute"],
  setup(props, context) {
    const options = ["TCP协议", "UDP协议"];
    const form = ref({
      protocol: "",
      port: "",
    });

    const disabledFlag = computed(() => {
      if (props.typeValue === "push") {
        return false;
      } else {
        return true;
      }
    });
    let availablePort = false;
    const loading = ref(false);

    const formInstance = ref<FormInstance>();
    const rules = reactive<FormRules>({
      protocol: [{ required: true, trigger: "blur", message: "协议不得为空" }],
      port: [{ required: true, trigger: "blur", message: "端口不得为空" }],
    });

    const changeHandle = () => {
      availablePort = false;
    };

    const checkClick = async () => {
      if (!form.value.port) {
        notice("warning", "警告", "端口不得为空");
      }
      loading.value = true;
      const res = await checkPort(Number.parseInt(form.value.port));
      if (res) {
        if (res.data === 0) {
          availablePort = true;
          notice("success", "成功", "有效端口");
        } else {
          availablePort = false;
          notice("warning", "警告", "该端口被占用，请重新更换端口");
        }
      } else {
        availablePort = false;
      }
      loading.value = false;
    };

    const addClick = async (formEl: FormInstance | undefined) => {
      if (availablePort) {
        await formEl!.validate((valid) => {
          if (valid) {
            context.emit(
              "checkAttribute",
              async (val: { attribute: DeviceAttribute; flag: boolean }) => {
                if (val.flag) {
                  const jsonData: Omit<DeviceConfig, "id"> = {
                    deviceConfigAttribute: val.attribute,
                    push: {
                      protocol: form.value.protocol,
                      port: Number.parseInt(form.value.port),
                    },
                  };
                  const res = await initDevice(jsonData);
                  if (res) {
                    notice("success", "成功", "添加新设备");
                    availablePort = false
                  }
                }
              }
            );
          }
        });
      } else {
        notice("warning", "警告", "请先检验端口是否有效");
      }
    };

    return {
      form,
      options,
      disabledFlag,
      loading,
      checkClick,
      changeHandle,
      addClick,
      rules,
      formInstance,
    };
  },
});
</script>

<style lang="scss" scoped>
.push-device {
  background: white;
  .title {
    height: 60px;
    box-sizing: border-box;
    border-bottom: solid 1px #f2f2f2;
    line-height: 60px;
    padding-left: 20px;
  }
  .content {
    height: calc(100% - 100px);
    padding: 20px;
    position: relative;
    .commit {
      position: absolute;
      bottom: 20px;
    }
  }
}
</style>