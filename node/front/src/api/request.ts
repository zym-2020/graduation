import { get, post, del, patch } from "./axios";
import { DeviceConfig } from "@/type";

//device相关接口
export const uploadPicture = async (formData: FormData) => {
  return post(`/device/uploadPicture`, false, formData);
};

export const initDevice = async (jsonData: Omit<DeviceConfig, "id">) => {
  return post(`/device/initDevice`, true, jsonData);
};

export const getAllDevice = async () => {
  return get(`/device/getAllDevice`, true);
};

//receiveRealTimeData相关接口
export const checkPort = async (port: number) => {
  return get(`/receiveRealTimeData/checkPort/${port}`, true);
};
