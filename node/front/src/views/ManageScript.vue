<template>
  <div class="manage-script" ref="scriptComponent">
    <div class="search">
      <el-input v-model="input" placeholder="请输入关键字" />
      <el-button
        ><el-icon><Search /></el-icon>检索</el-button
      >
    </div>
    <el-row :gutter="20">
      <el-col :span="6">
        <script-card :cardType="'add'" />
      </el-col>
      <el-col :span="6" v-for="(item, index) in scriptList" :key="index">
        <script-card :cardType="'script'" :scriptConfig="item" />
      </el-col>
    </el-row>
    <div class="page">
      <el-pagination
        small
        background
        layout="total, prev, pager, next"
        :total="total"
        :pager-count="5"
        :hide-on-single-page="true"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, onMounted } from "vue";
import ScriptCard from "@/components/manageScript/ScriptCard.vue";
import { pageQuery } from "@/api/request";
import { ScriptPojo } from "@/type";
export default defineComponent({
  components: { ScriptCard },
  setup() {
    const input = ref("");
    const scriptComponent = ref<HTMLElement>();
    const scriptList = ref<ScriptPojo[]>([]);
    const keyword = "";
    const total = ref(0);

    const size = computed(() => {
      if (scriptComponent.value) {
        return (
          Math.floor((scriptComponent.value.clientHeight - 150) / 200) * 4 - 1
        );
      } else {
        return 0;
      }
    });

    onMounted(async () => {
      const res = await pageQuery({
        size: size.value,
        page: 0,
        keyword: keyword,
      });
      if (res) {
        scriptList.value = res.data.list;
        total.value = res.data.total;
      }
    });

    return { input, scriptComponent, size, scriptList, total };
  },
});
</script>

<style lang="scss" scoped>
.manage-script {
  padding: 20px;
  height: calc(100% - 40px);
  .search {
    height: 40px;
    background: white;
    margin-bottom: 20px;
    padding: 20px;

    .el-input {
      width: 500px;
    }
    .el-button {
      box-sizing: border-box;
      border: solid 1px #081c42;
      color: #081c42;
      margin-left: 10px;
      &:hover {
        background: #f5f6f8;
      }
    }
    .el-icon {
      margin-right: 5px;
    }
  }
  .script-card {
    height: 180px;
    margin-bottom: 20px;
  }
  .page {
    height: 30px;
    display: flex;
    justify-content: center;
  }
}
</style>