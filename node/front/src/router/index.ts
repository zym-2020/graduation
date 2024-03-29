import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import Home from "../views/Home.vue";
import Layout from "@/layout/Layout.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    component: Layout,
    children: [
      {
        path: "",
        name: "Home",
        component: () => import("@/views/Home.vue"),
        meta: {
          keepAlive: true,
        },
      },
    ],
  },
  {
    path: "/addDevice",
    component: Layout,
    children: [
      {
        path: "",
        name: "AddDevice",
        component: () => import("@/views/AddDevice.vue"),
        meta: {
          keepAlive: true,
        },
      },
    ],
  },
  {
    path: "/registerDevice",
    component: Layout,
    children: [
      {
        path: "",
        name: "RegisterDevice",
        component: () => import("@/views/RegisterDevice.vue"),
        meta: {
          keepAlive: false,
        },
      },
    ],
  },
  {
    path: "/subscribeDevice",
    component: Layout,
    children: [
      {
        path: "",
        name: "SubscribeDevice",
        component: () => import("@/views/SubscribeDevice.vue"),
        meta: {
          keepAlive: false,
        },
      },
    ],
  },
  {
    path: "/manageDevice",
    component: Layout,
    children: [
      {
        path: "",
        name: "ManageDevice",
        component: () => import("@/views/ManageDevice.vue"),
        meta: {
          keepAlive: true,
        },
      },
      {
        path: ":id",
        name: "DeviceDetail",
        component: () => import("@/views/DeviceDetail.vue"),
        meta: {
          keepAlive: false,
        },
      },
    ],
  },
  {
    path: "/manageScript",
    component: Layout,
    children: [
      {
        path: "",
        name: "ManageScript",
        component: () => import("@/views/ManageScript.vue"),
        meta: {
          keepAlive: true,
        },
      },
    ],
  },
  {
    path: "/404",
    name: "404",
    component: () => import("@/views/404.vue"),
    meta: {
      title: "404",
    },
  },
  {
    path: "/:catchAll(.*)",
    name: "Redirect404",
    redirect: "/404",
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
