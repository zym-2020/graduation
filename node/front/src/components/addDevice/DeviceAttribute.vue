<template>
  <div class="device-attribute">
    <div class="title">
      <el-icon size="20"><InfoFilled /></el-icon>设备属性
    </div>
    <div class="content">
      <el-scrollbar>
        <el-form
          label-width="80px"
          :model="form"
          ref="formInstance"
          :rules="rules"
        >
          <el-form-item label="设备名">
            <el-input v-model="form.name" />
          </el-form-item>
          <el-form-item label="设备型号">
            <el-input v-model="form.model" />
          </el-form-item>
          <el-form-item label="设备经度" prop="longitude">
            <el-input v-model="form.longitude" />
          </el-form-item>
          <el-form-item label="设备纬度" prop="latitude">
            <el-input v-model="form.latitude" />
          </el-form-item>
          <el-form-item label="所在水道">
            <el-input v-model="form.watercourse" />
          </el-form-item>
          <el-form-item label="所属部门">
            <el-input v-model="form.department" />
          </el-form-item>
          <el-form-item label="设备描述">
            <el-input
              v-model="form.description"
              type="textarea"
              :rows="3"
              resize="none"
            />
          </el-form-item>
          <el-form-item label="设备图片">
            <avatar-upload
              :h="'100px'"
              :w="'100px'"
              :fontSize="'28px'"
              :imageUrl="''"
              @returnPictureFile="returnPictureFile"
            />
          </el-form-item>
          <el-form-item label="传输类型" prop="typeValue">
            <el-select v-model="form.typeValue" @change="changeHandle">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="设备参数">
            <div
              v-for="(item, index) in form.productParameters.parameters"
              :key="index"
              class="productParameters"
            >
              <el-input v-model="item.name" class="name" placeholder="key" />
              <el-input
                v-model="item.value"
                class="value"
                placeholder="value"
              />
              <el-icon @click="closeClick(index)"><CircleClose /></el-icon>
            </div>
            <el-button @click="btnClick"
              ><el-icon><Plus /></el-icon>添加参数</el-button
            >
          </el-form-item>
        </el-form>
      </el-scrollbar>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive, ref } from "vue";
import { DeviceAttribute } from "@/type";
import AvatarUpload from "@/components/upload/AvatarUpload.vue";
import type { FormInstance, FormRules } from "element-plus";
export default defineComponent({
  components: { AvatarUpload },
  emits: ["typeChange"],
  setup(_, context) {
    const form = ref<DeviceAttribute & { typeValue: string }>({
      name: "",
      model: "",
      description: "",
      watercourse: "",
      department: "",
      longitude: "",
      latitude: "",
      picture: "",
      productParameters: {
        parameters: [
          { name: "", value: "" },
          { name: "", value: "" },
          { name: "", value: "" },
        ],
      },
      typeValue: "",
    });

    const options = [
      { label: "主动推送", value: "push" },
      { label: "手动录入", value: "typing" },
      { label: "定时捞取", value: "fetch" },
    ];
    const formInstance = ref<FormInstance>();

    const rules = reactive<FormRules>({
      longitude: [{ required: true, trigger: "blur", message: "经度不得为空" }],
      latitude: [{ required: true, trigger: "blur", message: "纬度不得为空" }],
      typeValue: [
        { required: true, trigger: "blur", message: "传输类型不得为空" },
      ],
    });

    const changeHandle = (val: string) => {
      context.emit("typeChange", val);
    };

    const closeClick = (index: number) => {
      form.value.productParameters.parameters.splice(index, 1);
    };

    const btnClick = () => {
      form.value.productParameters.parameters.push({
        name: "",
        value: "",
      });
    };

    const returnPictureFile = (val: string | undefined) => {
      if (val) {
        form.value.picture = val;
      } else {
        form.value.picture = "";
      }
    };

    const checkForm = async (): Promise<{
      attribute: DeviceAttribute;
      flag: boolean;
    }> => {
      const {
        name,
        model,
        description,
        watercourse,
        department,
        longitude,
        latitude,
        picture,
        productParameters,
      } = form.value;
      const res = {
        attribute: {
          name,
          model,
          description,
          watercourse,
          department,
          longitude,
          latitude,
          picture,
          productParameters,
        },
        flag: false,
      };
      if (!formInstance.value) {
        return res;
      }
      res.flag = await formInstance.value.validate();
      return res;
    };

    return {
      form,
      closeClick,
      btnClick,
      options,
      returnPictureFile,
      changeHandle,
      rules,
      checkForm,
      formInstance,
    };
  },
});
</script>

<style lang="scss" scoped>
.device-attribute {
  background: white;
  height: 100%;
  .title {
    height: 60px;
    box-sizing: border-box;
    border-bottom: solid 1px #f2f2f2;
    line-height: 60px;
    padding-left: 20px;
    .el-icon {
      position: relative;
      top: 6px;
      margin-right: 5px;
    }
  }
  .content {
    height: calc(100% - 80px);

    .el-scrollbar {
      padding: 0 25px;
      height: 100%;
      .el-form {
        margin-top: 20px;
        .productParameters {
          width: 100%;
          margin-bottom: 10px;
          .name {
            width: calc(40% - 5px);
          }
          .value {
            width: calc(60% - 25px);
            margin-left: 10px;
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
          .el-icon {
            position: relative;
            margin-right: 3px;
            top: -1px;
          }
        }
      }
    }
  }
}
</style>