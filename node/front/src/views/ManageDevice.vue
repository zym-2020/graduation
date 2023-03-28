<template>
  <div class="manage-device">
    <div class="top">
      <map-chart :deviceList="deviceList" ref="mapChart" />
      <device-list-card
        :deviceList="deviceList"
        @focusDeviceLocation="focusDeviceLocation"
      />
    </div>
    <div class="bottom">
      <chart-card class="c3" />
      <chart-card class="c4" />
      <chart-card class="c5" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onActivated, onDeactivated, ref } from "vue";
import MapChart from "@/components/manageDevice/MapChart.vue";
import ChartCard from "@/components/manageDevice/ChartCard.vue";
import DeviceListCard from "@/components/manageDevice/DeviceListCard.vue";
import { getAllDevice } from "@/api/request";
import { DevicePojo, MapChartInstance } from "@/type";
export default defineComponent({
  components: { MapChart, ChartCard, DeviceListCard },
  setup() {
    const deviceList = ref<DevicePojo[]>([]);

    const mapChart = ref<MapChartInstance>();

    const getDeviceList = async () => {
      const res = await getAllDevice();
      if (res) {
        deviceList.value = res.data;
      }
    };

    const focusDeviceLocation = (val: [number, number]) => {
      mapChart.value!.focusLocation(val);
    };

    onActivated(() => {
      getDeviceList();
    });
    onDeactivated(() => {
      console.log(2);
    });

    return {
      deviceList,
      mapChart,
      focusDeviceLocation,
    };
  },
});
</script>

<style lang="scss" scoped>
.manage-device {
  height: calc(100% - 40px);
  padding: 20px;
  .top {
    height: calc((100% / 3 * 2) - 10px);
    display: flex;
    width: 100%;
    .map-chart {
      width: calc((100% / 4 * 3) - 10px);
      height: 100%;
    }
    .device-list {
      width: calc((100% / 4) - 10px);
      height: 100%;
      margin-left: 20px;
    }
  }
  .bottom {
    height: calc((100% / 3) - 10px);
    margin-top: 20px;
    display: flex;
    width: 100%;
    .c3,
    .c4,
    .c5 {
      height: 100%;
    }
    .c3 {
      width: calc((100% - 40px) / 2);
    }
    .c4,
    .c5 {
      width: calc((100% - 40px) / 4);
      margin-left: 20px;
    }
  }
}
</style>