<template>
  <div class="multiple-upload">
    <el-upload action="#" :auto-upload="false" :show-file-list="false">
      <template #trigger>
        <el-button class="select-file">选择文件</el-button>
      </template>

      <el-button color="#081c42" @click="submitUpload">
        上传至服务器
      </el-button>
    </el-upload>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  setup() {},
});
</script>

<style lang="scss" scoped>
.multiple-upload {
  .select-file {
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
</style>