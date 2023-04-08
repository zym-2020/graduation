<template>
  <div class="add-folder">
    <div class="current">
      <div class="key">当前路径：</div>
      <div>{{ currentPath }}</div>
    </div>
    <div class="folder-name">
      <div class="key"><span>文件夹名：</span></div>
      <el-input v-model="input" placeholder="请输入文件夹名称" />
    </div>
    <div class="btn">
      <el-button color="#081c42" :disabled="disabledFlag" @click="clickHandle"
        >确定</el-button
      >
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref } from "vue";
export default defineComponent({
  props: {
    path: {
      type: Array,
    },
  },
  emits: ["addFolderCall"],
  setup(props, context) {
    const input = ref("");
    const currentPath = computed(() => {
      if (props.path?.length) {
        let p = "";
        props.path.forEach((item) => {
          p += "/" + item;
        });
        return p;
      } else {
        return "/";
      }
    });

    const disabledFlag = computed(() => {
      if (input.value === "") return true;
      else return false;
    });

    const clickHandle = () => {
      let temp =
        currentPath.value === "/" ? "/" : currentPath.value.substring(1);
      context.emit("addFolderCall", { path: temp, folder: input.value });
    };

    return { input, currentPath, disabledFlag, clickHandle };
  },
});
</script>

<style lang="scss" scoped>
.add-folder {
  height: 120px;
  position: relative;
  font-size: 16px;
  color: #929292;
  .current,
  .folder-name {
    display: flex;
    height: 30px;
    line-height: 30px;
    .key {
      width: 80px;
      text-align: right;
    }
    .el-input {
      width: calc(100% - 80px);
    }
  }
  .current {
    margin-bottom: 10px;
  }

  .btn {
    position: absolute;
    bottom: 0px;
    right: 0px;
  }
}
</style>