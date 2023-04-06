import { ElNotification } from "element-plus";
import jsSHA from "jssha";
import Identicon from "identicon.js";

export function notice(
  type: "success" | "warning" | "info" | "error",
  title: string,
  msg: string
) {
  ElNotification({
    type: type,
    title: title,
    message: msg,
    offset: 100,
  });
}

export const imgBase64 = (name: string) => {
  let shaObj = new jsSHA("SHA-512", "TEXT");
  shaObj.update(name);
  var hash = shaObj.getHash("HEX");
  let data = new Identicon(hash, 280).toString();
  return "data:image/png;base64," + data;
};

interface O {
  "M+": number;
  "d+": number;
  "h+": number;
  "m+": number;
  "s+": number;
  "q+": number;
  S: number;
}
export const dateFormat = (date: string, format?: string) => {
  let dateObj = new Date(Date.parse(date));
  let fmt = format || "yyyy-MM-dd hh:mm:ss";
  //author: meizz
  var o: O = {
    "M+": dateObj.getMonth() + 1, //月份
    "d+": dateObj.getDate(), //日
    "h+": dateObj.getHours(), //小时
    "m+": dateObj.getMinutes(), //分
    "s+": dateObj.getSeconds(), //秒
    "q+": Math.floor((dateObj.getMonth() + 3) / 3), //季度
    S: dateObj.getMilliseconds(), //毫秒
  };
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(
      RegExp.$1,
      (dateObj.getFullYear() + "").substr(4 - RegExp.$1.length)
    );
  for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt))
      fmt = fmt.replace(
        RegExp.$1,
        RegExp.$1.length == 1
          ? o[k as keyof O].toString()
          : ("00" + o[k as keyof O].toString()).substr(
              ("" + o[k as keyof O].toString()).length
            )
      );
  return fmt;
};

export const formatFileSize = (fileSize: number) => {
  if (fileSize < 1024) {
    return fileSize + " B";
  } else if (fileSize < 1024 * 1024) {
    let temp = fileSize / 1024;
    return temp.toFixed(2) + " KB";
  } else if (fileSize < 1024 * 1024 * 1024) {
    let temp = fileSize / (1024 * 1024);
    return temp.toFixed(2) + " MB";
  } else {
    let temp = fileSize / (1024 * 1024 * 1024);
    return temp.toFixed(2) + " GB";
  }
};
