<template>
  <div class="device-detail">
    <el-scrollbar>
      <div class="content">
        <device-attribute-info />
        <device-data />
      </div>
    </el-scrollbar>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import DeviceAttributeInfo from "@/components/deviceDetail/DeviceAttributeInfo.vue";
import DeviceData from "@/components/deviceDetail/DeviceData.vue";

export default defineComponent({
  components: { DeviceAttributeInfo, DeviceData },
  setup() {},
});
</script>

<style lang="scss" scoped>
.device-detail {
  height: 100%;
  .el-scrollbar {
    padding: 20px;
    height: calc(100% - 40px);
    .content {
      display: flex;
      position: relative;
      align-items: flex-start;
      .device-attribute-info {
        width: calc(40% - 10px);
      }
      .device-data {
        margin-left: 20px;
        width: calc(60% - 10px);
      }
    }
  }
}
</style>