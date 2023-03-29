import NProgress from "nprogress";
import "nprogress/nprogress.css";
import router from "@/router/index";
import { RouteLocationNormalized } from "vue-router";
import { toIdPages } from "@/utils/routerUtil";

NProgress.configure({ showSpinner: false });
router.beforeEach(
  async (
    to: RouteLocationNormalized,
    from: RouteLocationNormalized,
    next: any
  ) => {
    NProgress.start();
    if (to.name === "DeviceDetail") {
      const code = await toIdPages(to);
      if (code === 1) {
        next();
      } else {
        next("/404");
      }
    } else {
      next();
    }
    NProgress.done();
  }
);
