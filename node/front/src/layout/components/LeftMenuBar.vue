<template>
  <div class="left-menu-bar">
    <div class="logo">
      <div class="image">
        <img src="/favicon copy.ico" />
      </div>
      <div class="text">
        <strong>实时水情数据<br />管理系统</strong>
      </div>
    </div>
    <el-menu
      active-text-color="#ffd04b"
      background-color="#1e1e1e"
      :default-active="active"
      text-color="#fff"
      @select="selectHandle"
    >
      <el-menu-item index="1">
        <el-icon><HomeFilled /></el-icon>
        <span>首页</span>
      </el-menu-item>
      <!-- <el-sub-menu index="2">
        <template #title>
          <el-icon><Setting /></el-icon>
          <span>挂载设备</span>
        </template>
        <el-menu-item index="2-1">实时推送</el-menu-item>
        <el-menu-item index="2-2">手动输入</el-menu-item>
        <el-menu-item index="2-3">定时捞取</el-menu-item>
      </el-sub-menu> -->
      <el-menu-item index="2">
        <el-icon><Setting /></el-icon>
        <span>挂载设备</span>
      </el-menu-item>
      <el-menu-item index="3">
        <el-icon><Tickets /></el-icon>
        <span>注册设备</span>
      </el-menu-item>
      <el-menu-item index="4">
        <el-icon><Checked /></el-icon>
        <span>订阅设备</span>
      </el-menu-item>
      <el-menu-item index="5">
        <el-icon><Menu /></el-icon>
        <span>管理设备</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import router from "@/router";
export default defineComponent({
  setup() {
    const active = ref(
      (function () {
        switch (router.currentRoute.value.name) {
          case "Home":
            return "1";
          case "AddDevice":
            return "2";
          case "RegisterDevice":
            return "3";
          case "SubscribeDevice":
            return "4";
          case "ManageDevice":
            return "5";
          case "DeviceDetail":
            return "5";
          default:
            return "1";
        }
      })()
    );

    const selectHandle = (key: string) => {
      switch (key) {
        case "1":
          router.push({ path: "/" });
          break;
        case "2":
          router.push({ path: "/addDevice" });
          break;
        case "3":
          router.push({ path: "/registerDevice" });
          break;
        case "4":
          router.push({ path: "/subscribeDevice" });
          break;
        case "5":
          router.push({ path: "/manageDevice" });
          break;
      }
    };

    return {
      active,
      selectHandle,
    };
  },
});
</script>

<style lang="scss" scoped>
.left-menu-bar {
  width: 300px;
  background: #1e1e1e;
  height: 100%;
  .logo {
    height: 70px;
    display: flex;
    .image {
      margin-left: 50px;
      img {
        margin-top: 10px;
        height: 50px;
        width: 50px;
      }
    }
    .text {
      width: 150px;
      color: white;
      font-size: 20px;
      text-align: center;
      margin-top: 10px;
    }
  }
  .el-menu {
    font-weight: 600;
    border: none;
  }
}
</style>