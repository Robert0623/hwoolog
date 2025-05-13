<script setup lang="ts"></script>

<template>
  <div class="title">
    <!--    <router-link />-->
  </div>

  <div class="regDate">2025-05-13</div>

  <div class="content">
    내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용내용
  </div>

  <div class="readMore">
    <!--    <router-link />-->
  </div>
</template>

<style scoped lang="scss">
.title {
  a {
    color: inherit;
    text-decoration: none;
    font-size: 1.4rem;
    font-weight: 400;

    &:hover {
      text-decoration: underline;
    }
  }
}

.regDate {
  margin-top: 0.5rem;
  font-size: 0.78rem;
  font-weight: 300;
}

.content {
  margin-top: 0.88rem;
  font-weight: 300;
}

.readMore {
  margin-top: 0.3rem;

  a {
    font-size: 0.88rem;
    color: #3dbdcd;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
