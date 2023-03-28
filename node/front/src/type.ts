export interface ResponseType {
  code: number;
  msg: string;
  data: any;
}

export interface DeviceAttribute {
  name: string;
  model: string;
  description: string;
  longitude: number | "";
  latitude: number | "";
  watercourse: string;
  department: string;
  picture: string;
  productParameters: {
    parameters: { name: string; value: string }[];
  };
}

export interface DeviceAttributeInstance {
  checkForm: () => Promise<{
    attribute: DeviceAttribute;
    flag: boolean;
  }>;
}

export interface MapChartInstance {
  focusLocation: (param: [number, number]) => {};
}

export interface DeviceConfig {
  id: string;
  deviceConfigAttribute: DeviceAttribute;
  push?: {
    protocol: string;
    port: number;
  };
  typing?: {
    type: string;
    keys: string[];
  };
}

export interface DevicePojo {
  id: string;
  name: string | null;
  picture: string | null;
  longitude: number;
  latitude: number;
  description: string | null;
  port: number | null;
  state: number;
  lastUpdate: string | null;
}
