<template>
  <div class="device-data">
    <el-empty description="暂无数据" v-if="dataList.length === 0" />
    <div></div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted, PropType } from "vue";
export default defineComponent({
  props: {
    dataList: {
      type: Object as PropType<string[]>,
    },
  },
  setup(props) {
    const dataList = computed(() => {
      return props.dataList;
    });
    onMounted(() => {
      console.log(props.dataList);
    });

    return {
      dataList,
    };
  },
});
</script>

<style lang="scss" scoped>
.device-data {
  background: white;
  min-height: calc(100vh - 40px - 70px - 40px);
  padding: 20px;
}
</style>