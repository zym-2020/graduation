<template>
  <div class="icon" @dblclick="dblClickHandle">
    <svg>
      <use xlink:href="#icon-wenjianjia"></use>
    </svg>
    <div class="bottom">
      <div class="text">{{ text }}</div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent } from "vue";
export default defineComponent({
  props: {
    text: {
      type: String,
    },
  },
  setup(props) {
    const text = computed(() => {
      return props.text;
    });

    return { text };
  },
});
</script>

<style lang="scss" scoped>
.icon {
  width: 150px;
  height: 200px;
  cursor: pointer;
  &:hover {
    background: rgba($color: #409eff, $alpha: 0.2);
  }
  svg {
    width: 110px;
    height: 110px;
    margin: 20px;
  }
  .bottom {
    width: 130px;
    margin: 0 10px;
    height: 50px;
    display: flex;
    .text {
      width: 100%;
      align-self: center;
      text-align: center;
      font-size: 16px;
    }
  }
}
</style>