<script setup lang="ts">
import axios from "axios";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  postId: {
    type: [Number, String],
    required: true,
  },
});

const post = ref({
  id: 0,
  title: '',
  content: '',
});

const moveToEdit = () => {
  router.push({name: 'edit'});
};

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then((response) => {
    post.value = response.data;
  });
})

</script>

<template>
  <h2>{{post.title}}</h2>
  <div>{{post.content}}</div>
<!--  <router-link :to="{name: 'edit', params: {postId: post.id}}">-->
<!--    <el-button type="warning">수정</el-button>-->
<!--  </router-link>-->
  <el-button type="warning" @click="moveToEdit()">수정</el-button>

</template>
