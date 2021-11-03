<template>
  <div class="app-container">
    <h2 style="text-align: center">发布课程信息</h2>

    <el-steps :active="2">
      <el-step title="（一）填写课程基本信息" icon="el-icon-edit"/>
      <el-step title="（二）创建课程大纲" icon="el-icon-edit"/>
      <el-step title="（三）确认发布" icon="el-icon-edit"/>
    </el-steps>

    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{chapter.title}}
        </p>

        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{video.title}}
            </p>
          </li>
        </ul>

      </li>
    </ul>

    <el-form label-width="120px">
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" @click="back">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import chapterApi from "../../../api/course/chapter";
export default {
  data() {
    return {
      saveBtnDisabled: false, // 是否禁用
      active: 0,
      chapterVideoList: [],
      courseId: ''
    }
  },

  created() {
      if (this.$route.params && this.$route.params.id) {
          this.courseId = this.$route.params.id
      }
      this.getChapterVideo()
  },

  methods: {

    getChapterVideo() {
        chapterApi.getAllChapterVideo(this.courseId).then(response => {
            this.chapterVideoList = response.data.list
        })
    },

    next() {
      this.$router.push({ path: '/course/publish/' + this.courseId })
    },
    back() {
      this.$router.push({ path: '/course/info/' + this.courseId })
    }
  }
}
</script>

<style scoped>
  .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    float: left;
    font-size: 20px;
    margin: 10px 0;
    height: 54px;
    line-height: 30px;
    width: 100%;
    border: 1px solid #DDD;
    border-radius: 20px;
    padding: 10px 10px 10px 20px;
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px 10px 10px 20px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }

</style>
