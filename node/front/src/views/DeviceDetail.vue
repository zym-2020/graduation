<template>
  <div class="device-detail">
    <el-scrollbar>
      <div class="content">
        <device-attribute-info :deviceConfig="deviceConfig" :status="status"/>
        <device-data :deviceConfig="deviceConfig" :dataList="dataList" />
      </div>
    </el-scrollbar>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import DeviceAttributeInfo from "@/components/deviceDetail/DeviceAttributeInfo.vue";
import DeviceData from "@/components/deviceDetail/DeviceData.vue";
import router from "@/router";
import { DeviceConfig } from "@/type";
export default defineComponent({
  components: { DeviceAttributeInfo, DeviceData },
  setup() {
    const deviceConfig = ref<DeviceConfig>(
      router.currentRoute.value.params.device as unknown as DeviceConfig
    );
    const dataList = ref<string[]>(
      router.currentRoute.value.params.data as string[]
    );
    const status = ref<{ state: number; lastUpdate: string }>(
      router.currentRoute.value.params.status as unknown as {
        state: number;
        lastUpdate: string;
      }
    );

    return {
      deviceConfig,
      dataList,
      status
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