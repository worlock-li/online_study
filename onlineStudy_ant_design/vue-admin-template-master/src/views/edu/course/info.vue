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
      <el-form-item label="所属分类">
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
        <el-input v-model="courseInfo.description" placeholder="好书！！！，学了马上从入门到放弃"/>
      </el-form-item>

      <!--  课程封面   -->

      <el-form-item label="课程价格" prop="price">
        <el-input-number v-model="courseInfo.price" :min="0" controls-position="right"/>
      </el-form-item>

      <el-form-item label="总课时" prop="lessonNum">
        <el-input-number v-model="courseInfo.lessonNum" :min="0" controls-position="right"/>
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

export default {
  data() {
    return {
      saveBtnDisabled: false, // 是否禁用
      active: 0,
      teacherList: [], // 所有讲师
      subjectOneList: [], // 所有一级分类
      subjectTwoList: [], // 所有二级分类
      courseInfo: {
        teacherId: '', // 讲师id
        subjectParentId: '', // 一级分类id
        subjectId: '', // 二级分类id
        title: '', // 标题
        price: '', // 价格
        lessonNum: '', // 总课时
        cover: '', // 课程封面图片路径
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
        ]
      }
    }
  },

  created() {
    this.findAllTeacher()
    this.getAllSubject()
  },

  methods: {
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
      // 框架自动封装， value就是id
      // 遍历所有的一级分类， 判断当前选择的是那个一级分类，拿到选中的一级分类下的二级分类
      for (let i = 0; i <= this.subjectOneList.length; i++) {
        const oneSubject = this.subjectOneList[i]
        if (oneSubject.id === value) {
          // 拿到二级分类进行赋值
          this.subjectTwoList = oneSubject.children
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
    }
  }
}
</script>
