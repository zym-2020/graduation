<template>
  <div class="map-chart">
    <div class="title">
      <el-icon><MapLocation /></el-icon>设备分布
    </div>
    <div class="container" ref="container"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, PropType, watch } from "vue";
import mapBoxGl from "mapbox-gl";
import "mapbox-gl/dist/mapbox-gl.css";
import { DevicePojo } from "@/type";
export default defineComponent({
  props: {
    deviceList: {
      type: Object as PropType<DevicePojo[]>,
    },
  },
  setup(porps) {
    const container = ref<HTMLElement>();
    let map: mapBoxGl.Map;

    const initMap = () => {
      map = new mapBoxGl.Map({
        container: container.value!,
        style: {
          version: 8,
          sources: {
            tdtVec: {
              type: "raster",
              tiles: [
                "http://t0.tianditu.com/vec_w/wmts?tk=35a94ab5985969d0b93229c30db6abd6&SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=w&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&FORMAT=tiles",
              ],
              tileSize: 256,
            },
            txt: {
              type: "raster",
              tiles: [
                "http://t0.tianditu.com/cva_w/wmts?tk=35a94ab5985969d0b93229c30db6abd6&SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=w&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&FORMAT=tiles",
              ],
              tileSize: 256,
            },
          },
          layers: [
            {
              id: "tdtVec",
              type: "raster",
              source: "tdtVec",
            },
            {
              id: "txt",
              type: "raster",
              source: "txt",
            },
          ],
        },
        center: [121.18, 31.723],
        zoom: 8,
      });
    };

    const watchUtil = (newVal: DevicePojo[], oldVal: DevicePojo[]) => {
      const tempNew = [...newVal];
      const tempOld = [...oldVal];
      tempNew.forEach((item, index) => {
        for (let i = 0; i < tempOld.length; i++) {
          if (item.id === tempOld[i].id) {
            tempOld.splice(i, 1);
            tempNew.splice(index, 1);
            break;
          }
        }
      });
      tempNew.forEach((item) => {
        if (!map.getLayer(item.id)) {
          map.addSource(item.id, {
            type: "geojson",
            data: {
              properties: {},
              type: "Feature",
              geometry: {
                type: "Point",
                coordinates: [item.longitude, item.latitude],
              },
            },
          });
          map.addLayer({
            id: item.id,
            source: item.id,
            type: "circle",
          });
        }
      });
      tempOld.forEach((item) => {
        if (map.getLayer(item.id)) {
          map.removeLayer(item.id);
          map.removeSource(item.id);
        }
      });
      let lon = 0,
        lat = 0;
      newVal.forEach((item) => {
        lon += item.longitude;
        lat += item.latitude;
      });
      lon = lon / newVal.length;
      lat = lat / newVal.length;
      map.setCenter([lon, lat]);
    };

    watch(
      () => porps.deviceList,
      (newVal, oldVal) => {
        watchUtil(newVal!, oldVal!);
      }
    );

    onMounted(() => {
      console.log(1);
      initMap();
    });

    return { container };
  },
});
</script>

<style lang="scss" scoped>
.map-chart {
  background: white;
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  .title {
    box-sizing: border-box;
    border-bottom: solid 1px #f2f2f2;
    line-height: 60px;
    padding-left: 20px;
    .el-icon {
      position: relative;
      top: 3px;
      margin-right: 5px;
    }
  }
  .container {
    height: calc(100% - 60px);
  }
}
</style>