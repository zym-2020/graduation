
<template>
  <div class="avatar-upload">
    <el-upload
      action="#"
      :auto-upload="false"
      :show-file-list="false"
      :accept="accept.toString()"
      :on-change="changeHandle"
      list-type="picture"
      ref="upload"
    >
      <div class="avatar" v-if="imageUrl">
        <img :src="imageUrl" />
        <div class="el-upload-list__item-actions">
          <span>
            <el-icon><RefreshLeft /></el-icon>
          </span>
          <span>
            <el-icon @click.stop="deleteClick"><Delete /></el-icon>
          </span>
        </div>
      </div>
      <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>
  </div>
</template> 

<script lang="ts">
/**
 * 用户头像上传组件
 * props：
 * @h：组件高度
 * @w：组件宽度
 * @fontSize：组件中icon大小
 * @imageUrl：初始头像地址
 *
 * emits：
 * @returnPictureFile：更新头像的File or undefined
 */
import { computed, defineComponent, ref } from "vue";
import { UploadFile } from "element-plus";
import { uploadPicture } from "@/api/request";
export default defineComponent({
  props: {
    h: {
      type: String,
    },
    w: {
      type: String,
    },
    fontSize: {
      type: String,
    },
    imageUrl: {
      type: String,
    },
  },
  emits: ["returnPictureFile"],
  setup(props, context) {
    const imageUrl = ref(props.imageUrl === undefined ? "" : props.imageUrl);
    const width = computed(() => {
      if (props.w === undefined) return "100px";
      return props.w;
    });
    const height = computed(() => {
      if (props.h === undefined) return "100px";
      return props.h;
    });
    const fontSize = computed(() => {
      if (props.fontSize === undefined) return "28px";
      return props.fontSize;
    });

    const deleteClick = () => {
      imageUrl.value = "";
      context.emit("returnPictureFile", undefined);
    };

    const changeHandle = async (uploadFile: UploadFile) => {
      if (uploadFile.status === "ready" && uploadFile.raw) {
        imageUrl.value = URL.createObjectURL(uploadFile.raw);
        const formData = new FormData();
        formData.append("file", uploadFile.raw);
        const res = await uploadPicture(formData);
        if (res) {
          context.emit("returnPictureFile", res.data);
        }
      }
    };
    const accept = [
      ".xbm",
      ".tif",
      ".pjp",
      ".svgz",
      ".jpg",
      ".jpeg",
      ".ico",
      ".tiff",
      ".gif",
      ".svg",
      ".jfif",
      ".webp",
      ".png",
      ".bmp",
      ".pjpeg",
      ".avif",
    ];

    return {
      imageUrl,
      width,
      height,
      fontSize,
      changeHandle,
      deleteClick,
      accept,
    };
  },
});
</script>

<style lang="scss" scoped>
.avatar-upload {
  /deep/ .el-upload {
    border: 2px dashed #8c939d;
    border-radius: 6px;
    cursor: pointer;
    overflow: hidden;
    height: v-bind(height);
    width: v-bind(width);
    .avatar {
      width: 100%;
      height: 100%;
      display: block;
      position: relative;
      img {
        height: 100%;
        width: 100%;
      }
      .el-upload-list__item-actions {
        height: 100%;
        width: 100%;
        position: absolute;
        top: 0;
        display: flex;
        align-items: center;
        justify-content: space-around;
        visibility: hidden;
        background: rgba(0, 0, 0, 0.3);
        .el-icon {
          font-size: v-bind(fontSize);
          color: white;
          cursor: pointer;
        }
      }
      &:hover {
        .el-upload-list__item-actions {
          cursor: default;
          visibility: visible;
          transition: 1s;
        }
      }
    }
    &:hover {
      border-color: #357cc5;
      .avatar-uploader-icon {
        color: #357cc5;
      }
    }

    .avatar-uploader-icon {
      font-size: v-bind(fontSize);
      color: #8c939d;
      text-align: center;
    }
  }
}
</style>