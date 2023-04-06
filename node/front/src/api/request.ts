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

export const getDeviceInfo = async (deviceId: string) => {
  return get(`/device/getDeviceInfo/${deviceId}`, true);
};

export const getDeviceData = async (
  deviceId: string,
  jsonDtaa: { path: string }
) => {
  return post(`/device/getDeviceData/${deviceId}`, true, jsonDtaa);
};

//receiveRealTimeData相关接口
export const checkPort = async (port: number) => {
  return get(`/receiveRealTimeData/checkPort/${port}`, true);
};

export const startTCPServer = async (port: number, deviceId: string) => {
  return post(`/receiveRealTimeData/startTCPServer/${port}/${deviceId}`, true);
};

export const stopTCPServer = async (port: number, deviceId: string) => {
  return post(`/receiveRealTimeData/stopTCPServer/${port}/${deviceId}`, true);
};

//SSE接口
export const overSSE = async (type: string, id: string) => {
  return get(`/SSE/over/${type}/${id}`, true);
};
