import axios, {
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  AxiosRequestHeaders,
  InternalAxiosRequestConfig,
} from "axios";

import { ResponseType } from "@/type";

import { notice } from "@/utils/common";

const requestList = new Set();

const axiosInstance: AxiosInstance = axios.create({
  baseURL: "/nodeManage/",
  timeout: 200000,
});

axiosInstance.interceptors.response.use(
  (response: AxiosResponse) => {
    setTimeout(() => {
      requestList.delete(response.config.url + response.config.data);
    }, 600); //请求间隔600ms
    if (response.data.code != 0) {
      notice("error", "错误", response.data.msg);
      return null;
    }
    return response.data;
  },
  (err: AxiosResponse) => {
    if (axios.isCancel(err)) {
      notice("warning", "警告", "操作过于频繁");
      return null;
    } else {
      notice("error", "错误", "请求错误");
      requestList.delete(err.config.url + err.config.data);
      return null;
    }
  }
);
axiosInstance.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  const flag = config.headers["debounce"];
  config.cancelToken = new axios.CancelToken((e) => {
    const cancelRequest = () => {
      let url: string = (config.baseURL as string) + config.url;
      e(url);
    };

    if (flag === "true") {
      requestList.has(config.url + JSON.stringify(config.data))
        ? cancelRequest()
        : requestList.add(config.url + JSON.stringify(config.data));
    }
  });
  return config;
});

export const get = (
  url: string,
  debounce: boolean,
  params?: any
): Promise<ResponseType | null> => {
  return axiosInstance.get(url, {
    headers: {
      debounce: debounce ? "true" : "false",
    },
    params: params,
  });
};

export const post = (
  url: string,
  debounce: boolean,
  data?: any
): Promise<ResponseType | null> => {
  return axiosInstance.post(url, data, {
    headers: {
      debounce: debounce ? "true" : "false",
    },
  });
};

export const del = (
  url: string,
  debounce: boolean
): Promise<ResponseType | null> => {
  return axiosInstance.delete(url, {
    headers: {
      debounce: debounce ? "true" : "false",
    },
  });
};

export const patch = (
  url: string,
  debounce: boolean,
  data?: any
): Promise<ResponseType | null> => {
  return axiosInstance.patch(url, data, {
    headers: {
      debounce: debounce ? "true" : "false",
    },
  });
};
