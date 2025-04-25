<script setup lang="ts">
import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const posts = ref([]);

axios.get('/api/posts?page=1&size=5')
.then(response => {
  response.data.forEach((r: any) => {
    posts.value.push(r);
  });
})

</script>

<template>
<!--  <div v-for="(post, index) in posts" :key="index"></div>-->
  <ul>
    <li v-for="post in posts" :key="post.id">
      <div>
        <router-link :to="{name: 'read', params: {postId: post.id}}">{{ post.title }}</router-link>
      </div>

      <div>
        {{ post.content }}
      </div>
    </li>
  </ul>
</template>

<style>
  li {
    margin-bottom: 1rem;
  }

  li:last-child {
    margin-bottom: 0;
  }
</style>
