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

export interface DeviceActions {
  actionList:
    | {
        id: string;
        name: string;
        steps:
          | {
              id: string;
              script: string;
              scriptName: string;
              parameters: {
                parameterList: string[];
              };
            }[]
          | null;
      }[]
    | null;
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
    storage: string;
  };
  typing?: {
    type: string;
    keys?: {
      keyList: string[];
    };
    storage: string;
  };
  actions?: DeviceActions;
}

export interface ScriptConfig {
  id: string;
  name: string;
  description: string;
  parameters: {
    parameterList: {
      optional: boolean;
      name: string;
      description: string | null;
      example: string | null;
      type: string;
    }[];
  };
  enter: string;
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
  register: boolean;
}

export interface ScriptPojo {
  id: string;
  name: string;
  description: string;
}

export interface Tree {
  id: string;
  label: string;
  type: string;
  children?: Tree[];
  actionId?: string;
}

export interface TableDataType {
  name: string;
  lastUpdate: string;
  type: string;
  size?: number;
}

export interface DeviceDataDom {
  initDataList: () => {};
}
