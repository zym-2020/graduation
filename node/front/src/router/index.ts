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
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
