<template>
  <div class="device-action">
    <div class="btn">
      <el-button type="success" plain size="small" @click="addActionClick"
        >添加行为</el-button
      >
    </div>
    <el-scrollbar :max-height="maxHeight">
      <el-tree :data="treeData" :props="defaultProps">
        <template #default="{ node, data }">
          <span class="custom-tree-node">
            <svg class="icon-svg" v-if="data.type === 'action'">
              <use xlink:href="#icon-actionscript"></use>
            </svg>
            <svg class="icon-svg" v-if="data.type === 'script'">
              <use xlink:href="#icon-script"></use>
            </svg>
            <svg class="icon-svg" v-if="data.type === 'param'">
              <use xlink:href="#icon-parameter"></use>
            </svg>
            <span :class="data.type">{{ node.label }}</span>
            <el-icon
              v-if="data.type === 'action'"
              class="plus-icon"
              @click="addScript(data.id)"
              ><CirclePlus
            /></el-icon>
            <el-icon
              v-if="data.type === 'param'"
              @click="paramClick(data.id, data.actionId)"
              ><Switch
            /></el-icon>
            <el-icon v-else @click="closeClick(data.type, data.id)"
              ><CircleClose
            /></el-icon>
          </span>
        </template>
      </el-tree>
    </el-scrollbar>

    <el-dialog v-model="addActionDialog" width="500">
      <template #header>
        <div class="my-header">添加行为</div>
      </template>
      <add-action @addActionCall="addActionCall" />
    </el-dialog>

    <el-dialog v-model="scriptSettingDialog" width="800">
      <template #header>
        <div class="my-header">添加脚本</div>
      </template>
      <script-setting @scriptSettingCall="scriptSettingCall" />
    </el-dialog>

    <el-dialog v-model="paramSettingDialog" width="800">
      <template #header>
        <div class="my-header">
          <span>{{ scriptName }}</span>
          参数配置
        </div>
      </template>
      <param-setting
        :scriptId="scriptId"
        :parameterList="parameterList"
        :actionFlag="true"
        v-if="paramSettingDialog"
        @paramSettingCall="paramSettingCall"
      />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, PropType, ref } from "vue";
import { Tree, DeviceActions } from "@/type";
import router from "@/router";
import ScriptSetting from "./ScriptSetting.vue";
import ParamSetting from "./ParamSetting.vue";
import {
  updateActionParameter,
  addStep,
  deleteAction,
  addAction,
} from "@/api/request";
import { notice } from "@/utils/common";
import { ElMessageBox } from "element-plus";
import AddAction from "./AddAction.vue";
export default defineComponent({
  props: {
    actions: {
      type: Object as PropType<DeviceActions>,
    },
  },
  emits: ["deviceActionCall"],
  components: { ParamSetting, ScriptSetting, AddAction },
  setup(props, context) {
    let actionId = "";
    let stepId = "";
    const defaultProps = {
      children: "children",
      label: "label",
    };
    const scriptSettingDialog = ref(false);
    const paramSettingDialog = ref(false);
    const addActionDialog = ref(false);
    const scriptId = ref("");
    const scriptName = ref("");
    const parameterList = ref<string[]>([]);

    const treeData = computed(() => {
      if (props.actions && props.actions.actionList) {
        const data: Tree[] = [];
        props.actions.actionList.forEach((item) => {
          let stepArr: Tree[] = [];
          if (item.steps) {
            item.steps.forEach((step) => {
              let paramArr: Tree[] = [];
              step.parameters.parameterList.forEach((param) => {
                paramArr.push({
                  label: param,
                  type: "param",
                  id: step.id,
                  actionId: item.id,
                });
              });
              stepArr.push({
                id: step.id,
                label: step.scriptName,
                type: "script",
                children: paramArr,
                actionId: item.id,
              });
            });
          }

          data.push({
            label: item.name,
            type: "action",
            children: stepArr,
            id: item.id,
          });
        });
        return data;
      } else return [];
    });

    const maxHeight = computed(() => {
      const height = document.documentElement.clientHeight;
      return height - (40 + 70 + 40) - (200 + 70 + 40 + 20);
    });

    const addActionClick = () => {
      addActionDialog.value = true;
    };

    const addScript = (actionIdParam: string) => {
      scriptSettingDialog.value = true;
      actionId = actionIdParam;
    };

    const paramClick = (id: string, actionIdParam: string) => {
      if (props.actions && props.actions.actionList) {
        props.actions.actionList.forEach((item) => {
          if (item.steps) {
            item.steps.forEach((step) => {
              if (actionIdParam === item.id && id === step.id) {
                scriptId.value = step.script;
                scriptName.value = step.scriptName;
                parameterList.value = [...step.parameters.parameterList];
              }
            });
          }
        });
      }
      actionId = actionIdParam;
      stepId = id;
      paramSettingDialog.value = true;
    };

    const closeClick = (type: string, id: string) => {
      ElMessageBox.confirm(
        `确定删除该${type === "action" ? "行为" : "脚本"}?`,
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(async () => {
          console.log(type, id);
          const deviceId = router.currentRoute.value.params.id as string;
          const res = await deleteAction({
            deviceId: deviceId,
            id: id,
            type: type,
          });
          if (res) {
            context.emit("deviceActionCall", res.data);
            notice("success", "成功", "修改设备行为");
          }
        })
        .catch(() => {});
    };

    const addActionCall = async (val: string) => {
      const deviceId = router.currentRoute.value.params.id as string;
      const res = await addAction({ deviceId: deviceId, actionName: val });
      if (res) {
        context.emit("deviceActionCall", res.data);
        notice("success", "成功", "添加行为");
      }
      addActionDialog.value = false;
    };

    const paramSettingCall = async (val: string[]) => {
      const deviceId = router.currentRoute.value.params.id as string;
      const res = await updateActionParameter({
        deviceId: deviceId,
        actionId: actionId,
        stepId: stepId,
        parameters: val,
      });
      if (res) {
        context.emit("deviceActionCall", res.data);
        notice("success", "成功", "参数修改成功");
      }
      paramSettingDialog.value = false;
    };

    const scriptSettingCall = async (val: {
      parameters: string[];
      scriptId: string;
    }) => {
      const deviceId = router.currentRoute.value.params.id as string;
      const res = await addStep({
        deviceId: deviceId,
        actionId: actionId,
        scriptId: val.scriptId,
        parameters: val.parameters,
      });
      if (res) {
        context.emit("deviceActionCall", res.data);
        notice("success", "成功", "增加脚本");
      }
      scriptSettingDialog.value = false;
    };

    return {
      defaultProps,
      treeData,
      maxHeight,
      scriptSettingDialog,
      paramSettingDialog,
      scriptId,
      scriptName,
      parameterList,
      addActionDialog,
      addScript,
      paramClick,
      closeClick,
      addActionClick,
      addActionCall,
      paramSettingCall,
      scriptSettingCall,
    };
  },
});
</script>

<style lang="scss" scoped>
.device-action {
  .btn {
    position: relative;
    height: 30px;
    .el-button {
      right: 0px;
      position: absolute;
    }
  }

  /deep/ .el-tree > .el-tree-node {
    min-width: calc(100% - 16px);
    display: inline-block;
    line-height: 40px;
  }

  .el-icon {
    margin-right: 5px;
    position: relative;
    top: 2px;
  }

  /deep/ .el-tree {
    .el-tree-node {
      position: relative;
      padding-left: 16px; // 缩进量
    }
    .el-tree-node__children {
      padding-left: 16px; // 缩进量
      transition: all 0.5s; // 关键动画！！！
    }

    // 竖线
    .el-tree-node::before {
      content: "";
      height: 100%;
      width: 1px;
      position: absolute;
      left: -3px;
      top: -26px;
      border-width: 1px;
      border-left: 1px solid #e9e9e9;
    }
    // 当前层最后一个节点的竖线高度固定
    .el-tree-node:last-child::before {
      height: 48px; // 可以自己调节到合适数值
    }

    // 横线
    .el-tree-node::after {
      content: "";
      width: 18px;
      height: 20px;
      position: absolute;
      left: -3px;
      top: 20px;
      border-width: 1px;
      border-top: 1px solid #e9e9e9;
    }

    // 去掉最顶层的虚线，放最下面样式才不会被上面的覆盖了
    & > .el-tree-node::after {
      border-top: none;
    }
    & > .el-tree-node::before {
      border-left: none;
    }

    // 展开关闭的icon
    .el-tree-node__expand-icon {
      font-size: 16px;
      display: none; // 也可以去掉
      // 叶子节点（无子节点）
      &.is-leaf {
        color: transparent;
        display: none; // 也可以去掉
      }
    }
    .el-tree-node__content {
      height: 40px;
    }
  }

  .custom-tree-node {
    width: 100%;
    display: flex;
    position: relative;
    .icon-svg {
      width: 16px;
      height: 16px;
      margin-top: 12px;
      margin-right: 5px;
    }
    .action {
      color: #f44336;
      //   font-weight: 600;
    }
    .param {
      color: #9292a0;
    }

    .el-icon {
      position: absolute;
      right: 0px;
      top: 13px;
      &:hover {
        color: #409eff;
      }
    }

    .plus-icon {
      right: 20px;
    }
  }

  .my-header {
    font-size: 20px;
    span {
      color: black;
    }
  }
}
</style>