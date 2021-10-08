<template>
  <div class="app-container">
    <el-form ref="teacher" :model="teacher" :rules="rules" label-width="120px">
      <el-form-item label="讲师姓名 : " prop="name">
        <el-input v-model="teacher.name" placeholder="讲师姓名"/>
      </el-form-item>

      <el-form-item label="讲师排序 : " prop="sort">
        <el-input-number v-model="teacher.sort" :min="0" controls-position="right"/>
      </el-form-item>

      <el-form-item label="头衔 : " prop="level">
        <el-select v-model="teacher.level" placeholder="请选择头衔">
          <el-option label="高级讲师" value="1"/>
          <el-option label="首席讲师" value="2"/>
        </el-select>
      </el-form-item>

      <el-form-item label="讲师资历 : " prop="career">
        <el-input v-model="teacher.career" placeholder="讲师简介"/>
      </el-form-item>

      <el-form-item label="讲师简介 : " prop="intro">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" placeholder="讲师简介"/>
      </el-form-item>

      <el-form-item label="头像 : ">
        <el-button v-model="teacher.avatar" type="message">上传<i class="el-icon-upload el-icon--right"/></el-button>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" style="width: 100px" type="primary" @click="save('teacher')">保存</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import teacherApi from '../../../api/teacher/teacher'

export default {
  data() {
    return {
      teacher: {},
      saveBtnDisabled: false, // 连续点击按钮禁用
      // 表单验证规则
      rules: {
        name: [
          { required: true, message: '请输入讲师名称', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入讲师排序', trigger: 'blur' }
        ],
        level: [
          { required: true, message: '请选择头衔', trigger: 'blur' }
        ],
        career: [
          { required: true, message: '请输入讲师资历', trigger: 'blur' },
          { max: 20, message: '长度20个字符以内', trigger: 'change' }
        ],
        intro: [
          { required: true, message: '请输入讲师简介', trigger: 'blur' },
          { max: 50, message: '长度50个字符以内', trigger: 'change' }
        ]
      }

    }
  },
  created() {
  },
  methods: {
    save(teacher) {
      this.$refs[teacher].validate((valid) => {
        if (valid) {
          teacherApi.save(this.teacher).then(response => {
            // 提示信息
            console.log(response.message)
            // 回到列表页面, 路由跳转
            this.$router.push('/teacher/table')
          }).cache(error => {
            console.log(error)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
