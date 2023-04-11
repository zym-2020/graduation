<template>
  <div class="device-action">
    <div class="btn">
      <el-button type="success" plain size="small">添加行为</el-button>
    </div>
    <el-tree :data="data" :props="defaultProps">
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
            @click.prevent="addScript"
            ><CirclePlus
          /></el-icon>
          <el-icon v-if="data.type === 'param'"><Switch /></el-icon>
          <el-icon v-else><CircleClose /></el-icon>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { Tree } from "@/type";
export default defineComponent({
  setup() {
    const defaultProps = {
      children: "children",
      label: "label",
    };

    const data: Tree[] = [
      {
        label: "Action1",
        type: "action",
        children: [
          {
            label: "RawData",
            type: "script",
            children: [
              {
                label: "data",
                type: "param",
              },
              {
                label: "path",
                type: "param",
              },
              {
                label: "station",
                type: "param",
              },
            ],
          },
        ],
      },
    ];

    const addScript = () => {};

    return { defaultProps, data, addScript };
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
      //   box-sizing: border-box;
      //   border: solid 1px #081c42;
      //   color: #081c42;
      //   &:hover {
      //     background: #f5f6f8;
      //   }
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
}
</style>