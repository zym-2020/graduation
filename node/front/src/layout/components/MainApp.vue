<template>
  <div class="main-app">
    <router-view v-slot="{ Component }">
      <keep-alive>
        <component
          :is="Component"
          v-if="route.meta.keepAlive"
          :key="route.name"
        />
      </keep-alive>
      <component
        :is="Component"
        :key="route.name"
        v-if="!route.meta.keepAlive"
      />
    </router-view>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, onMounted } from "vue";
import router from "@/router";
export default defineComponent({
  setup() {
    const route = computed(() => {
      return router.currentRoute.value;
    });

    return {
      route,
    };
  },
});
</script>


<style lang="scss" scoped>
.main-app {
  background: #eff1f5;
  height: calc(100% - 70px);
}
</style>
