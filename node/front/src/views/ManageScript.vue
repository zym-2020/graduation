<template>
  <div class="manage-script" ref="scriptComponent">
    <div class="search">
      <el-input v-model="input" placeholder="请输入关键字" />
      <el-button @click="searchClick"
        ><el-icon><Search /></el-icon>检索</el-button
      >
    </div>
    <el-skeleton :rows="5" animated v-if="skeletonFlag" />
    <div v-else>
      <el-row :gutter="20">
        <el-col :span="6">
          <script-card :cardType="'add'" @refresh="refresh" />
        </el-col>
        <el-col :span="6" v-for="(item, index) in scriptList" :key="index">
          <script-card
            :keyword="keyword"
            :cardType="'script'"
            :scriptPojo="item"
            @scriptCardCall="scriptCardCall"
          />
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
          v-model:current-page="currentPage"
          @current-change="pageChange"
        />
      </div>
    </div>

    <el-dialog v-model="scriptInfoDialog" width="800">
      <template #header>
        <div class="my-header">
          <svg class="icon-svg">
            <use xlink:href="#icon-code1"></use></svg
          >{{ scriptConfig.name }}
        </div>
      </template>
      <script-info :scriptConfig="scriptConfig" />
    </el-dialog>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, ref, onMounted } from "vue";
import ScriptCard from "@/components/manageScript/ScriptCard.vue";
import { pageQuery } from "@/api/request";
import { ScriptPojo, ScriptConfig } from "@/type";
import ScriptInfo from "@/components/manageScript/ScriptInfo.vue";
import { getScriptConfig } from "@/api/request";
import { notice } from "@/utils/common";
export default defineComponent({
  components: { ScriptCard, ScriptInfo },
  setup() {
    const skeletonFlag = ref(true);
    const input = ref("");
    const scriptComponent = ref<HTMLElement>();
    const scriptList = ref<ScriptPojo[]>([]);
    const keyword = ref("");
    const total = ref(0);
    const currentPage = ref(1);

    const scriptInfoDialog = ref(false);
    const scriptConfig = ref<ScriptConfig>();

    const size = computed(() => {
      if (scriptComponent.value) {
        return (
          Math.floor((scriptComponent.value.clientHeight - 150) / 200) * 4 - 1
        );
      } else {
        return 0;
      }
    });

    const queryScriptList = async (page: number) => {
      skeletonFlag.value = true;
      const res = await pageQuery({
        size: size.value,
        page: page,
        keyword: keyword.value,
      });
      if (res) {
        scriptList.value = res.data.list;
        total.value = res.data.total;
      }
      skeletonFlag.value = false;
    };

    const scriptCardCall = async (val: string) => {
      const res = await getScriptConfig(val);
      if (res) {
        scriptConfig.value = res.data;
        scriptInfoDialog.value = true;
      }
    };

    const refresh = async () => {
      await queryScriptList(0);
      notice("success", "成功", "添加成功");
    };

    const pageChange = async (val: number) => {
      await queryScriptList(val - 1);
      input.value = keyword.value;
    };

    const searchClick = async () => {
      keyword.value = input.value;
      await queryScriptList(0);
      currentPage.value = 1;
    };

    onMounted(async () => {
      await queryScriptList(0);
    });

    return {
      input,
      keyword,
      scriptComponent,
      size,
      scriptList,
      total,
      scriptInfoDialog,
      scriptConfig,
      skeletonFlag,
      currentPage,
      scriptCardCall,
      refresh,
      pageChange,
      searchClick,
    };
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

  .my-header {
    font-size: 20px;
    display: flex;
    .icon-svg {
      width: 25px;
      height: 25px;
      margin-right: 5px;
    }
  }
}
</style>