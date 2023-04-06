import { RouteLocationNormalized } from "vue-router";
import { getDeviceInfo, getDeviceData } from "@/api/request";

export const toIdPages = async (to: RouteLocationNormalized) => {
  if (to.params.id) {
    const res = await getDeviceInfo(to.params.id as string);
    const data = await getDeviceData(to.params.id as string, { path: "/" });
    if (res && data) {
      to.params.device = res.data.device;
      to.params.status = res.data.status;
      to.params.dataList = data.data
      return 1;
    } else {
      return -1;
    }
  }
};
