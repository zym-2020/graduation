<template>
  <div class="add-device">
    <device-attribute-componet @typeChange="typeChange" ref="deviceAttribute" />
    <div class="right">
      <push-device :typeValue="typeValue" @checkAttribute="checkAttribute" />
      <typing-device :typeValue="typeValue" @checkAttribute="checkAttribute" />
      <fetch-device />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import DeviceAttributeComponet from "@/components/addDevice/DeviceAttribute.vue";
import PushDevice from "@/components/addDevice/PushDevice.vue";
import TypingDevice from "@/components/addDevice/TypingDevice.vue";
import FetchDevice from "@/components/addDevice/FetchDevice.vue";
import { DeviceAttributeInstance, DeviceAttribute } from "@/type";
export default defineComponent({
  components: {
    DeviceAttributeComponet,
    PushDevice,
    TypingDevice,
    FetchDevice,
  },
  setup() {
    const typeValue = ref("");
    const deviceAttribute = ref<DeviceAttributeInstance>();

    const typeChange = (val: string) => {
      typeValue.value = val;
    };

    const checkAttribute = async (
      callback: (param: { attribute: DeviceAttribute; flag: boolean }) => {}
    ) => {
      if (deviceAttribute.value) {
        let res = await deviceAttribute.value.checkForm();
        callback(res);
      }
    };

    return {
      typeValue,
      typeChange,
      deviceAttribute,
      checkAttribute,
    };
  },
});
</script>

<style lang="scss" scoped>
.add-device {
  height: calc(100% - 40px);
  width: calc(100% - 40px);
  display: flex;
  padding: 20px;
  .device-attribute {
    width: calc(50% - 10px);
  }
  .right {
    width: calc(50% - 10px);
    margin-left: 20px;
    .push-device,
    .typing-device,
    .fetch-device {
      height: calc((100% - 40px) / 3);
    }
    .typing-device,
    .fetch-device {
      margin-top: 20px;
    }
  }
}
</style>