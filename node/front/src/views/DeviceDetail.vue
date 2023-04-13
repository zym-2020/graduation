<template>
  <div class="device-detail">
    <el-skeleton :rows="5" animated v-if="skeletonFlag" />
    <el-scrollbar v-else>
      <div class="content">
        <device-attribute-info :deviceConfig="deviceConfig" :status="status" />
        <device-data />
      </div>
    </el-scrollbar>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import DeviceAttributeInfo from "@/components/deviceDetail/DeviceAttributeInfo.vue";
import DeviceData from "@/components/deviceDetail/DeviceData.vue";
import { DeviceConfig } from "@/type";
import router from "@/router";
export default defineComponent({
  components: { DeviceAttributeInfo, DeviceData },
  setup() {
    const skeletonFlag = ref(true);
    const deviceConfig = ref<DeviceConfig>();
    const status = ref<{
      state: number;
      lastUpdate: string;
    }>();

    onMounted(() => {
      console.log("123");
      skeletonFlag.value = true;
      deviceConfig.value = router.currentRoute.value.params
        .device as unknown as DeviceConfig;
      status.value = router.currentRoute.value.params.status as unknown as {
        state: number;
        lastUpdate: string;
      };
      skeletonFlag.value = false;
    });

    return { deviceConfig, status, skeletonFlag };
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