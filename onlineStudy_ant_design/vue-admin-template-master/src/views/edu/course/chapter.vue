<template>
  <div class="app-container">
    <h2 style="text-align: center">发布课程信息</h2>

    <el-steps :active="2" style="margin-bottom:12px">
      <el-step title="（一）填写课程基本信息" icon="el-icon-edit"/>
      <el-step title="（二）创建课程大纲" icon="el-icon-edit"/>
      <el-step title="（三）确认发布" icon="el-icon-edit"/>
    </el-steps>

    <!--添加章节-->
    <el-button type="button" @click="openChapterForm">添加章节</el-button>
    <el-dialog title="添加编辑章节" :visible.sync="dialogFormVisible">
      <el-form :model="chapter" :rules="rules" ref="chapter">
        <el-form-item label="章节名称" prop="title" :label-width="formLabelWidth">
          <el-input v-model="chapter.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="章节排序" prop="sort" :label-width="formLabelWidth">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>

        <el-form-item>
          <div style="margin-left: 80%;">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addOrEditChapter('chapter')">确 定</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" :rules="rules" ref="video" label-width="120px">
        <el-form-item label="课时标题" prop="title">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序" prop="sort">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <el-upload
            :action="BASE_API+'/eduvod/video/uploadAlyiVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="addOrEditVideo('video')">确 定</el-button>
      </div>
    </el-dialog>

    <!--列表展示章节小节-->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{chapter.title}}
          <span class="acts">
            <el-button class="my-button-big" @click="openVideoAddForm(chapter.id)">添加小节</el-button>
            <el-button class="my-button" @click="openEditForm(chapter.id)">修改</el-button>
            <el-button class="my-button" @click="open(chapter.id)">删除</el-button>
          </span>
        </p>
        <!--小节-->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{video.title}}
              <span class="acts">
              <el-button class="my-button" @click="openVideoEditForm(video.id)">修改</el-button>
              <el-button class="my-button" @click="openVideoDeleteTips(video.id)">删除</el-button>
          </span>
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
  import videoApi from "../../../api/course/video";
export default {
  data() {
    return {
      saveBtnDisabled: false, // 是否禁用
      saveVideoBtnDisabled: false, // 是否禁用
      active: 0,
      chapterVideoList: [],
      courseId: '',
      dialogFormVisible: false, // 添加章节弹框， 默认为false
      BASE_API: process.env.BASE_API, // 接口基本地址

      formLabelWidth: '120px',
      chapter: { // 章节数据
      },
      video: { // 小节数据
          free: true
      },
      dialogVideoFormVisible: false, // 添加小节弹框， 默认为false
      rules: {
          title: [
              {required: true, message: '请输入讲师名称', trigger: 'blur'},
              {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}
          ],
          sort: [
              {required: true, message: '请输入讲师排序', trigger: 'blur'}
          ]
      }
    }

  },

  created() {
      if (this.$route.params && this.$route.params.id) {
          // 获取路径上的courseId
          this.courseId = this.$route.params.id
      }
      this.getChapterVideo()
  },

  methods: {

      /*----------------------------------章节--------------------------------------*/

    // 获取所有的章节以及小节数据
    getChapterVideo() {
        chapterApi.getAllChapterVideo(this.courseId).then(response => {
            this.chapterVideoList = response.data.list
        })
    },

    // 点击按钮弹出框-添加
    openChapterForm() {
        this.dialogFormVisible = true
        // 清空对象
        this.chapter = {id: '', title: '', sort: 0}
    },

    // 编辑按钮点击
    openEditForm(id) {
        chapterApi.getChapterById(id).then(response => {
            // 根据id获取显示
            this.chapter = response.data.chapter
            this.dialogFormVisible = true
        })
    },

    // 添加章节/修改
    addOrEditChapter(chapter) {
        this.$refs[chapter].validate((valid) => {
            if (valid) {
                if (this.chapter.id !== '') { // 编辑
                    chapterApi.updateChapter(this.chapter).then(response => {
                        // 关闭弹框
                        this.dialogFormVisible = false
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: '修改章节成功'
                        })
                        // 添加完成后刷新页面
                        this.getChapterVideo()
                    })
                } else {
                    this.chapter.courseId = this.courseId
                    chapterApi.addChapter(this.chapter).then(response => {
                        // 关闭弹框
                        this.dialogFormVisible = false
                        // 提示信息
                        this.$message({
                            type: 'success',
                            message: '添加章节成功'
                        })
                        // 添加完成后刷新页面
                        this.getChapterVideo()
                    })
                }
            } else {
                console.log('error submit!!')
                return false
            }
        })

    },

    // 点击删除按钮
    open(id) {
        this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            // 后端删除
            chapterApi.deleteById(id).then(response => {
                this.$message({
                    type: 'success',
                    message: '删除成功!'
                });
                // 刷新页面
                this.getChapterVideo()
            })
        }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消删除'
            });
        });
    },

      /*----------------------------------小节--------------------------------------*/

      // 点击添加小节按钮
      openVideoAddForm(chapterId) {
          // 清空对象
          this.video = {sort: 0}
          // 弹框
          this.dialogVideoFormVisible = true
          // 设置章节id、课程id
          this.video.chapterId = chapterId
          this.video.courseId = this.courseId
      },

      // 点击修改小节按钮
      openVideoEditForm(id) {
          videoApi.getVideoById(id).then(response => {
              // 根据id获取显示
              this.video = response.data.video
              this.dialogVideoFormVisible = true
          })
      },

      // 添加
      addVideo() {
        videoApi.addVideo(this.video).then(response => {
            // 关闭弹框
            this.dialogVideoFormVisible = false
            // 提示信息
            this.$message({
                type: 'success',
                message: '添加小节成功'
            })
            // 添加完成后刷新页面
            this.getChapterVideo()
        })
      },
      // 修改
      updateVideo() {
          videoApi.updateVideo(this.video).then(response => {
              // 关闭弹框
              this.dialogVideoFormVisible = false
              // 提示信息
              this.$message({
                  type: 'success',
                  message: '修改小节成功'
              })
              // 修改完成后刷新页面
              this.getChapterVideo()
          })
      },

      // 添加或者修改小节
      addOrEditVideo(video) {
          this.$refs[video].validate((valid) => {
              if (valid) {
                  if (this.video.id) { // id不为空，修改
                      this.updateVideo()
                  } else { // 添加
                      this.addVideo()
                  }
              }
          })
      },

      // 点击删除按钮
      openVideoDeleteTips(id) {
          this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
          }).then(() => {
              // 后端删除
              videoApi.deleteById(id).then(response => {
                  this.$message({
                      type: 'success',
                      message: '删除成功!'
                  });
                  // 刷新页面
                  this.getChapterVideo()
              })
          }).catch(() => {
              this.$message({
                  type: 'info',
                  message: '已取消删除'
              });
          });
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
    font-size: 20px;
    margin: 10px 0;
    height: 54px;
    line-height: 30px;
    width: 100%;
    border: 1px solid #DDD;
    border-radius: 20px;
    padding: 10px 10px 10px 20px;
    background-color: rgb(171 243 249);
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    font-size: 14px;
    margin: 10px 0;
    padding: 10px 10px 10px 20px;
    height: 59px;
    line-height: 42px;
    width: 100%;
    border: 1px dotted #DDD;
    background-color: #e9f9e2;
  }
  .my-button {
    border-radius: 33px;
    width: 70px;
    height: 38px;
  }
  .my-button-big {
    border-radius: 33px;
    width: 100px;
    height: 38px;
  }

</style>
