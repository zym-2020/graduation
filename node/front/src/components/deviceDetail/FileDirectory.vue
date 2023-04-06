<template>
  <div class="file-directory">
    <el-tree :data="data" :props="defaultProps">
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <el-icon v-if="data.children"><FolderOpened /></el-icon>
          <el-icon v-else><Folder /></el-icon>
          <span>{{ node.label }}</span>
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
        label: "Root",
        children: [
          {
            label: "RawData",
          },
          {
            label: "DeriveData",
          },
        ],
      },
    ];

    return { defaultProps, data };
  },
});
</script>

<style lang="scss" scoped>
.file-directory {
  margin-top: 20px;
  /deep/ .el-tree > .el-tree-node {
    min-width: calc(100% - 16px);
    display: inline-block;
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
}
</style>