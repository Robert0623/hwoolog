<script setup lang="ts">

import {onMounted, ref} from "vue";

import axios from 'axios';
import {useRouter} from "vue-router";

const router = useRouter()

const post = ref({
  id: 0,
  title: '',
  content: '',
});

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  },
});

const edit = () => {
  axios.patch(`/api/posts/${props.postId}`, post.value).then(() => {
    router.replace({name: 'home'});
  });
};

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });
});
</script>

<template>
  <div>
    <el-input v-model="post.title" type="text" placeholder="제목을 입력해주세요"/>
  </div>

  <div>
    <el-input v-model="post.content" type="textarea" rows="15"/>
  </div>

  <div class="mt-2 d-flex justify-content-end">
    <el-button type="warning" @click="edit()">수정완료</el-button>
  </div>
</template>

<style>

</style>
