<template>
  <div class="main-app">
    <router-view />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  setup() {},
});
</script>


<style lang="scss" scoped>
.main-app {
  background: #eff1f5;
  height: calc(100% - 70px);
}
</style>
