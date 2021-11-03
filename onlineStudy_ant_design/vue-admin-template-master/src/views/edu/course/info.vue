<template>
  <div class="app-container">
    <h2 style="text-align: center">发布课程信息</h2>

    <el-steps :active="1" style="margin-top: 10px; margin-bottom: 30px">
      <el-step title="（一）填写课程基本信息" icon="el-icon-edit"/>
      <el-step title="（二）创建课程大纲" icon="el-icon-edit"/>
      <el-step title="（三）确认发布" icon="el-icon-edit"/>
    </el-steps>

    <el-form ref="courseInfo" :model="courseInfo" :rules="rules" label-width="100px">
      <el-form-item label="课程标题" prop="title">
        <el-input v-model="courseInfo.title" placeholder="实例：java从入门到精通"/>
      </el-form-item>

      <!--  所属分类   -->
      <el-form-item label="所属分类" prop="subjectId">
        <el-select v-model="courseInfo.subjectParentId" @change="changeSelect">
          <el-option
            v-for="one in subjectOneList"
            :key="one.id"
            :label="one.title"
            :value="one.id"/>
        </el-select>

        <el-select v-model="courseInfo.subjectId" >
          <el-option
            v-for="two in subjectTwoList"
            :key="two.id"
            :label="two.title"
            :value="two.id"/>
        </el-select>
      </el-form-item>

      <!--  课程讲师   -->
      <el-form-item label="课程讲师" prop="teacherId">
        <el-select v-model="courseInfo.teacherId" >
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <!--  课程简介   -->
      <el-form-item label="课程简介" prop="description">
        <tinymce :height="300" v-model="courseInfo.description"></tinymce>
      </el-form-item>

      <!--  课程封面   -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'eduOss/oss'"
          class="avatar-uploader">
          <img :src="courseInfo.cover" style="width: 400px">
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格" prop="price">
        <el-input-number v-model="courseInfo.price" :min="0" controls-position="right"/>
      </el-form-item>

      <el-form-item label="总课时" prop="lessonNum">
        <el-input-number v-model="courseInfo.lessonNum" controls-position="right"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="addCourseInfo('courseInfo')">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import courseApi from '../../../api/course/course'
import teacherApi from '../../../api/teacher/teacher'
import subjectApi from '../../../api/subject/subject'
import tinymce from '../../../components/Tinymce'

export default {
  components: {
      tinymce
  },
  data() {
    return {
      saveBtnDisabled: false, // 是否禁用
      active: 0,
      teacherList: [], // 所有讲师
      subjectOneList: [], // 所有一级分类
      subjectTwoList: [], // 所有二级分类
      BASE_API: process.env.BASE_API, // 接口基本地址
      courseInfo: {
        teacherId: '', // 讲师id
        subjectParentId: '', // 一级分类id
        subjectId: '', // 二级分类id
        title: '', // 标题
        price: '', // 价格
        lessonNum: '', // 总课时
        cover: '/static/sucai.jpg', // 课程封面图片路径
        description: '' // 课程简介
      },
      rules: {
        title: [
          { required: true, message: '请输入讲师名称', trigger: 'blur' },
          { max: 10, message: '长度最大为10个字符', trigger: 'blur' }
        ],
        description: [
          { required: true, message: '请输入课程简介', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入课程价格', trigger: 'blur' }
        ],
        lessonNum: [
          { required: true, message: '请输入课时数', trigger: 'blur' }
        ],
        teacherId: [
          { required: true, message: '请选择讲师', trigger: 'blur' }
        ],
        subjectId: [
          { required: true, message: '请选择分类', trigger: 'blur' }
        ]
      }
    }
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
        this.getInfoById()
    } else {
        this.findAllTeacher()
        this.getAllSubject()
    }
  },

  methods: {
    // 进入页面根据id获取
    getInfoById() {
        courseApi.getInfoById(this.$route.params.id).then(response => {

            this.courseInfo = response.data.courseInfoVo
            // 查询所有的分类
            subjectApi.getAllSubject().then(res => {
                this.subjectOneList = res.data.data
                for (let i=0; i<this.subjectOneList.length; i++) {
                    // 遍历所有的一级分类
                    if (this.courseInfo.subjectParentId === this.subjectOneList[i].id) {
                        this.subjectTwoList = this.subjectOneList[i].children
                    }
                }

            })
            // 查询所有讲师
            this.findAllTeacher()

        })
    },

    // 添加课程基本信息
    addCourseInfo(courseInfo) {
      this.$refs[courseInfo].validate((valid) => {
        if (valid) {
          courseApi.addCourseInfo(this.courseInfo).then(response => {
            this.$message({
              type: 'success',
              message: '添加课程信息成功'
            })
            // 跳转到第二步
            this.$router.push({ path: `/course/chapter/${response.data.id}` })
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 改变一级分类
    changeSelect(value) {
      // 框架自动封装， value=id
      // 遍历所有的一级分类， 判断当前选择的是那个一级分类，拿到选中的一级分类下的二级分类
      for (let i = 0; i < this.subjectOneList.length; i++) {

        if (this.subjectOneList[i].id === value) {
          // 拿到二级分类进行赋值
          this.subjectTwoList = this.subjectOneList[i].children
          // 清空当前的二级分类
          this.courseInfo.subjectId = ''
        }
      }
    },
    // 获取所有讲师
    findAllTeacher() {
      teacherApi.findAll().then(response => {
        this.teacherList = response.data.items
      })
    },
    // 获取所有分类
    getAllSubject() {
      subjectApi.getAllSubject().then(response => {
        this.subjectOneList = response.data.data
      })
    },
    // 上传成功执行
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url
    },
    // 上传之后执行
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' // 是否是jpg格式的图片
      const isLt2M = file.size / 1024 / 1024 < 10 // 图片大小是否小于10M
      if (!isJPG) {
        this.$message.error('只能上传jpg格式的文件')
      }
      if (!isLt2M) {
        this.$message.error('图片大小不能超过10M')
      }
      return isLt2M && isJPG
    }

  }

}
</script>
<style>
  .tinymce-container {
    line-height: 29px;
  }
</style>
