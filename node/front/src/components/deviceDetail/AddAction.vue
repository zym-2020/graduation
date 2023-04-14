<template>
  <div class="add-action">
    <el-input v-model="input" placeholder="行为名" />
    <div class="btn">
      <el-button @click="confirmClick">确定</el-button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
export default defineComponent({
  emits: ["addActionCall"],
  setup(_, context) {
    const input = ref("");

    const confirmClick = () => {
      context.emit("addActionCall", input.value);
    };
    return { input, confirmClick };
  },
});
</script>

<style lang="scss" scoped>
.add-action {
  height: 80px;
  position: relative;
  .btn {
    position: absolute;
    right: 0px;
    bottom: 0px;
    .el-button {
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