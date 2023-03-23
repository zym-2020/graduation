<template>
  <div class="layout">
    <left-menu-bar />
    <div class="main-body">
      <head-menu-bar />
      <main-app />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import LeftMenuBar from "./components/LeftMenuBar.vue";
import HeadMenuBar from "./components/HeadMenuBar.vue";
import MainApp from "./components/MainApp.vue";
export default defineComponent({
  components: {
    LeftMenuBar,
    HeadMenuBar,
    MainApp,
  },
  setup() {},
});
</script>

<style lang="scss" scoped>
.layout {
  display: flex;
  height: 100%;
  .main-body {
    width: calc(100% - 300px);
    height: 100%;
  }
}
</style>