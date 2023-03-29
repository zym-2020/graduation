import { RouteLocationNormalized } from "vue-router";
import { getDeviceData } from "@/api/request";

export const toIdPages = async (to: RouteLocationNormalized) => {
  if (to.params.id) {
    const res = await getDeviceData(to.params.id as string);
    if (res) {
      to.params.device = res.data.device;
      to.params.data = res.data.data;
      to.params.status = res.data.status;
      return 1;
    } else {
      return -1;
    }
  }
};
