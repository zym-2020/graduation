<template>
  <div class="script-setting">
    <div class="search">
      <el-input v-model="input" />
      <el-button @click="searchClick">检索</el-button>
    </div>
    <el-skeleton :rows="5" animated v-if="skeletonFlag" />
    <div class="content" v-else>
      <div v-if="scriptList.length !== 0">
        <div class="script" v-for="(item, index) in scriptList" :key="index">
          <div class="name" @click="clickHandle(item.id)">
            <span v-html="replaceHandle(item.name)"></span>
          </div>
          <div class="detail" v-if="currentScriptId === item.id">
            <div class="script-des">
              <span v-html="replaceHandle(item.description)" />
            </div>
            <param-setting :scriptId="item.id" />
          </div>
        </div>
      </div>
      <el-empty description="暂无数据" v-else />
    </div>
    <div class="page">
      <el-pagination
        small
        background
        layout="total, prev, pager, next"
        :total="total"
        :pager-count="5"
        :hide-on-single-page="true"
        @current-change="pageChange"
        v-model:current-page="currentPage"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from "vue";
import ParamSetting from "./ParamSetting.vue";
import { pageQuery } from "@/api/request";
import { ScriptPojo } from "@/type";
export default defineComponent({
  components: { ParamSetting },
  setup() {
    let keyword = "";
    const input = ref("");
    const currentScriptId = ref("");
    const skeletonFlag = ref(true);

    const scriptList = ref<ScriptPojo[]>([]);
    const total = ref(0);
    const currentPage = ref(1);

    const clickHandle = (id: string) => {
      if (currentScriptId.value === id) currentScriptId.value = "";
      else currentScriptId.value = id;
    };

    const queryScriptList = async (page: number) => {
      skeletonFlag.value = true;
      const res = await pageQuery({ size: 5, page: page, keyword: keyword });
      if (res) {
        scriptList.value = res.data.list;
        total.value = res.data.total;
      }
      skeletonFlag.value = false;
    };

    const pageChange = async (val: number) => {
      await queryScriptList(val - 1);
      input.value = keyword;
    };

    const searchClick = async () => {
      keyword = input.value;
      await queryScriptList(0);
      currentPage.value = 1;
      currentScriptId.value = ""
    };

    const replaceHandle = (currentStr: string) => {
      const res = new RegExp("(" + keyword + ")", "g");
      currentStr = currentStr.replace(
        res,
        "<span style='color:red;'>" + keyword + "</span>"
      );
      return currentStr;
    };

    onMounted(async () => {
      await queryScriptList(0);
    });

    return {
      input,
      currentScriptId,
      skeletonFlag,
      scriptList,
      total,
      currentPage,
      clickHandle,
      pageChange,
      searchClick,
      replaceHandle,
    };
  },
});
</script>

<style lang="scss" scoped>
@-webkit-keyframes scale-up-ver-top {
  0% {
    -webkit-transform: scaleY(0.4);
    transform: scaleY(0.4);
    -webkit-transform-origin: 100% 0%;
    transform-origin: 100% 0%;
  }
  100% {
    -webkit-transform: scaleY(1);
    transform: scaleY(1);
    -webkit-transform-origin: 100% 0%;
    transform-origin: 100% 0%;
  }
}
@keyframes scale-up-ver-top {
  0% {
    -webkit-transform: scaleY(0.4);
    transform: scaleY(0.4);
    -webkit-transform-origin: 100% 0%;
    transform-origin: 100% 0%;
  }
  100% {
    -webkit-transform: scaleY(1);
    transform: scaleY(1);
    -webkit-transform-origin: 100% 0%;
    transform-origin: 100% 0%;
  }
}
.script-setting {
  .search {
    text-align: center;
    .el-input {
      width: calc(100% - 150px);
      margin-right: 10px;
    }
    .el-button {
      right: 0px;
      box-sizing: border-box;
      border: solid 1px #081c42;
      color: #081c42;
      &:hover {
        background: #f5f6f8;
      }
    }
  }
  .content {
    margin-top: 20px;
    .script {
      border-radius: 4px;
      box-sizing: border-box;
      border: solid 1px #bccbd7;
      margin-top: 10px;
      overflow: hidden;
      &:hover {
        .name {
          color: #4264fb;
        }
      }
      .name {
        height: 30px;
        line-height: 30px;
        padding: 0 10px;
        background: #f6f8f8;
        cursor: pointer;
        color: #6e718f;
      }
      .detail {
        -webkit-animation: scale-up-ver-top 0.4s
          cubic-bezier(0.39, 0.575, 0.565, 1) both;
        animation: scale-up-ver-top 0.4s cubic-bezier(0.39, 0.575, 0.565, 1)
          both;
        padding: 10px;
        .script-des {
          font-size: 16px;
          color: black;
          margin-bottom: 20px;
        }
      }
    }
  }
  .page {
    margin-top: 10px;
    display: flex;
    justify-content: center;
  }
}
</style>