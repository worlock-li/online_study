<template>
  <div class="app-container">
    <h2 style="text-align: center">发布课程信息</h2>

    <el-steps :active="2">
      <el-step title="（一）填写课程基本信息" icon="el-icon-edit"/>
      <el-step title="（二）创建课程大纲" icon="el-icon-edit"/>
      <el-step title="（三）确认发布" icon="el-icon-edit"/>
    </el-steps>

    <el-form label-width="120px">
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" @click="back">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      saveBtnDisabled: false, // 是否禁用
      active: 0
    }
  },

  created() {
  },

  methods: {
    next() {
      this.$router.push({ path: '/course/publish/1' })
    },
    back() {
      this.$router.push({ path: '/course/info/1' })
    }
  }
}
</script>
