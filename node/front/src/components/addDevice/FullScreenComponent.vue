<template>
  <div class="full-screen">
    <el-form label-width="70px" :model="form">
      <el-form-item label="数据列名">
        <el-scrollbar max-height="300px">
          <div class="column" v-for="(item, index) in form.keys" :key="index">
            <el-input v-model="form.keys[index]" />
            <el-icon @click="closeClick(index)"><CircleClose /></el-icon>
          </div>
          <el-button @click="addColumnClick"
            ><el-icon><Plus /></el-icon>添加列</el-button
          >
        </el-scrollbar>
      </el-form-item>
    </el-form>
    <div class="btn">
      <el-button color="#081c42" @click="confirmClick">确定</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { notice } from "@/utils/common";
import { defineComponent, PropType, ref } from "vue";

export default defineComponent({
  props: {
    keys: {
      type: Object as PropType<Array<string>>,
    },
  },
  emits: ["fullScreenComponentCall"],
  setup(props, context) {
    const form = ref<{ keys: string[] }>(
      (function () {
        const temp: { keys: string[] } = { keys: [] };
        if (props.keys) {
          props.keys.forEach((item) => {
            temp.keys.push(item);
          });
        }
        return temp;
      })()
    );

    const closeClick = (index: number) => {
      form.value.keys.splice(index, 1);
    };
    const addColumnClick = () => {
      form.value.keys.push("");
    };

    const confirmClick = () => {
      if (form.value.keys.length > 0) {
        for (let i = 0; i < form.value.keys.length; i++) {
          if (form.value.keys[i] === "") {
            notice("warning", "警告", "列名不得为空");
            return;
          }
        }
        context.emit("fullScreenComponentCall", form.value.keys);
      } else {
        notice("warning", "警告", "数据列必须>=1");
      }
    };

    return { form, closeClick, addColumnClick, confirmClick };
  },
});
</script>

<style lang="scss" scoped>
.full-screen {
  max-height: 340px;

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
  }
  .btn {
    position: relative;
    height: 40px;
    .el-button {
      position: absolute;
      bottom: 0px;
      right: 0px;
    }
  }
}
</style>