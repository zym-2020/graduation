import { get, post, del, patch } from "./axios";
import { DeviceConfig } from "@/type";

//device相关接口
export const uploadPicture = async (formData: FormData) => {
  return await post(`/device/uploadPicture`, false, formData);
};

export const initDevice = async (jsonData: Omit<DeviceConfig, "id">) => {
  return await post(`/device/initDevice`, true, jsonData);
};

export const getAllDevice = async () => {
  return await get(`/device/getAllDevice`, true);
};

export const getDeviceInfo = async (deviceId: string) => {
  return await get(`/device/getDeviceInfo/${deviceId}`, true);
};

export const getDeviceData = async (
  deviceId: string,
  jsonDtaa: { path: string }
) => {
  return await post(`/device/getDeviceData/${deviceId}`, true, jsonDtaa);
};

export const getDeviceFolder = async (jsonData: {
  deviceId: string;
  path: string;
}) => {
  return await post(`/device/getDeviceFolder`, true, jsonData);
};

export const createFolder = async (
  deviceId: string,
  jsonData: { path: string; folder: string }
) => {
  return await post(`/device/createFolder/${deviceId}`, true, jsonData);
};

export const updateActionParameter = async (jsonData: {
  deviceId: string;
  actionId: string;
  stepId: string;
  parameters: string[];
}) => {
  return await post(`/device/updateActionParameter`, true, jsonData);
};

export const addStep = async (jsonData: {
  deviceId: string;
  actionId: string;
  scriptId: string;
  parameters: string[];
}) => {
  return await post(`/device/addStep`, true, jsonData);
};

export const deleteAction = async (jsonData: {
  deviceId: string;
  id: string;
  type: string;
}) => {
  return await post(`/device/deleteAction`, true, jsonData);
};

export const addAction = async (jsonData: {
  deviceId: string;
  actionName: string;
}) => {
  return await post(`/device/addAction`, true, jsonData);
};

//receiveRealTimeData相关接口
export const checkPort = async (port: number) => {
  return await get(`/receiveRealTimeData/checkPort/${port}`, true);
};

export const startTCPServer = async (port: number, deviceId: string) => {
  return await post(
    `/receiveRealTimeData/startTCPServer/${port}/${deviceId}`,
    true
  );
};

export const stopTCPServer = async (port: number, deviceId: string) => {
  return await post(
    `/receiveRealTimeData/stopTCPServer/${port}/${deviceId}`,
    true
  );
};

//SSE接口
export const overSSE = async (type: string, id: string) => {
  return await get(`/SSE/over/${type}/${id}`, true);
};

//script相关接口
export const pageQuery = async (jsonData: {
  size: number;
  page: number;
  keyword: string;
}) => {
  return await post(`/script/pageQuery`, true, jsonData);
};

export const getScriptConfig = async (scriptId: string) => {
  return await get(`/script/getScriptConfig/${scriptId}`, true);
};

export const addScript = async (formData: FormData) => {
  return await post(`/script/addScript`, true, formData);
};
