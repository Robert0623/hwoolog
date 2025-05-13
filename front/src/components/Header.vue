<template>
  <div class="header">
    <!--    <img src="/images/logo.png" alt="logo" class="logo" />-->
    <div class="title">hwoolog</div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="css">
.header {
  height: 60px;
  margin: 1rem 2rem;
  display: flex;
  justify-content: center;
  align-content: center;
}

.logo {
  width: 52px;
  object-fit: cover;
}

.title {
  font-size: 2rem;
  font-weight: 300;
  margin-left: 5px;
}
</style>
