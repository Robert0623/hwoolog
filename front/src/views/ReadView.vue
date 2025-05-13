<script setup lang="ts">
import axios from 'axios'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import Comments from '@/components/Comments.vue'

const router = useRouter()

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
})

const post = ref({
  id: 0,
  title: '',
  content: '',
})

const moveToEdit = () => {
  router.push({ name: 'edit' })
}

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data
  })
})
</script>

<template>
  <el-row>
    <el-col>
      <div class="title">제목입니다.</div>
    </el-col>
  </el-row>

  <el-row>
    <el-col :span="10" :offset="7">
      <div class="title">
        <div class="regDate">Posted on 2025-05-13</div>
      </div>
    </el-col>
  </el-row>

  <el-row>
    <el-col>
      <div class="content">내용입니다.</div>

      <div class="footer">
        <div class="edit">수정</div>
        <div class="delete">삭제</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="comments"> </el-row>
  <el-col>
    <Comments />
  </el-col>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.8rem;
  font-weight: 400;
  text-align: center;
}

.regDate {
  margin-top: 0.5rem;
  font-size: 0.78rem;
  font-weight: 300;
}

.content {
  margin-top: 1.88rem;
  font-weight: 300;

  word-break: break-all;
  white-space: break-spaces;
  line-height: 1.4;
  min-height: 5rem;
}

hr {
  border-color: #f9f9f9;
  margin: 1.2rem 0;
}

.footer {
  margin-top: 1rem;
  display: flex;
  font-size: 0.78rem;
  justify-content: flex-end;
  gap: 0.8rem;

  .delete {
    color: red;
  }
}

.comments {
  margin-top: 4.8rem;
}
</style>
