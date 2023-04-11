<template>
  <div class="device-detail">
    <el-scrollbar>
      <div class="content">
        <device-attribute-info />
        <device-data :dataList="dataList" :deviceConfig="deviceConfig" />
      </div>
    </el-scrollbar>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import DeviceAttributeInfo from "@/components/deviceDetail/DeviceAttributeInfo.vue";
import DeviceData from "@/components/deviceDetail/DeviceData.vue";
import router from "@/router";
import { DeviceConfig, TableDataType } from "@/type";
export default defineComponent({
  components: { DeviceAttributeInfo, DeviceData },
  setup() {
    const deviceConfig = ref<DeviceConfig>(
      router.currentRoute.value.params.device as unknown as DeviceConfig
    );

    const dataList = ref<TableDataType[]>(
      router.currentRoute.value.params.dataList as unknown as TableDataType[]
    );

    const status = ref<{ state: number; lastUpdate: string }>(
      router.currentRoute.value.params.status as unknown as {
        state: number;
        lastUpdate: string;
      }
    );

    return {
      deviceConfig,
      status,
      dataList,
    };
  },
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