<script setup lang="ts"></script>

<template>
  <el-row>
    <el-col :span="10" :offset="7">
      <el-form label-position="top">
        <el-form-item label="아이디">
          <el-input></el-input>
        </el-form-item>

        <el-form-item label="비밀번호">
          <el-input type="password" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" sytle="width: 100%">로그인</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss"></style>
