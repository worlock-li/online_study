<template>
  <div class="app-container">
    <h2 style="text-align: center">发布课程信息</h2>

    <el-steps :active="3">
      <el-step title="（一）填写课程基本信息" icon="el-icon-edit"/>
      <el-step title="（二）创建课程大纲" icon="el-icon-edit"/>
      <el-step title="（三）确认发布" icon="el-icon-edit"/>
    </el-steps>

    <div class="ccInfo">
      <img :src="coursePublishVo.cover">
      <div class="main">
        <h2>{{ coursePublishVo.title }}</h2>
        <p class="gray"><span>共{{ coursePublishVo.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ coursePublishVo.oneSubject }} — {{ coursePublishVo.twoSubject }}</span></p>
        <p>课程讲师：{{ coursePublishVo.teacherName }}</p>
        <h3 class="red">￥{{ coursePublishVo.price }}</h3>
      </div>
    </div>

    <el-form label-width="120px">
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" @click="back">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="publishCourse">最终确认上传</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import courseApi from "../../../api/course/course";

export default {
  data() {
    return {
      saveBtnDisabled: false, // 是否禁用
      active: 0,
      courseId: '',
      coursePublishVo: {}
    }
  },

  created() {
      // 1. 获取路径当中的id
      if (this.$route.params && this.$route.params.id) {
          this.courseId = this.$route.params.id
      }

      // 2. 根据id获取课程信息
      this.getPublishCourseVo()
  },

  methods: {
      // 课程最终发布
      publishCourse() {
          courseApi.coursePublish(this.courseId).then(response => {
              console.log(response)
              this.$message({
                  type: 'success',
                  message: response.message
              })
              // 跳转课程列表页面
              this.$router.push({ path: '/course/table' })
          })
      },

      // 根据id获取课程信息
      getPublishCourseVo() {
          courseApi.getPublishCourse(this.courseId).then(response => {
              this.coursePublishVo = response.data.coursePublishVo
          })
      },

    submit() {
      console.log('上传成功')
    },

    back() {
      this.$router.push({ path: '/course/chapter/' + this.courseId })
    }
  }
}
</script>

<style scoped>
  .ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
    margin-top: 60px;
  }
  .ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
  }
  .ccInfo .main {
    margin-left: 520px;
  }

  .ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
  }
  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }

  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }
  .ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
  }
</style>
