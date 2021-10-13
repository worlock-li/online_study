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

      <el-form-item label="讲师头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="String(teacher.avatar)"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imageCropperShow=true">更换头像</el-button>

        <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调
          <input type="file" name="file"/>
        -->
        <image-cropper
          v-show="imageCropperShow"
          :width="300"
          :height="300"
          :key="imageCropperKey"
          :url="'/eduOss/oss/upload'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" style="width: 100px" type="primary" @click="saveOrUpdate('teacher')">保存</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import teacherApi from '../../../api/teacher/teacher'
import PanThumb from '../../../components/PanThumb'
import ImageCropper from '../../../components/ImageCropper'

export default {
  components: { PanThumb, ImageCropper },
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
      },
      imageCropperShow: false,
      imageCropperKey: 0,
      BASE_API: process.env.BASE_API // 获取config中的base_api

    }
  },
  watch: { // 监听
    $route(to, from) { // 路有变化，方法就会执行
      this.init()
    }
  },

  created() {
    this.init()
  },

  methods: {
    close() { // 关闭上传头像框
      this.imageCropperShow = false
      // 上传组件初始化
      this.imageCropperKey += 1
    },
    cropSuccess(data) { // 上传成功
      this.teacher.avatar = data.url
      this.imageCropperShow = false
      this.imageCropperKey += 1
    },

    init() {
      if (this.$route.params.id) {
        this.getById(this.$route.params.id)
      } else {
        this.teacher = {}
      }
    },

    getById(id) {
      teacherApi.getById(id).then(response => {
        this.teacher = response.data.teacher
      })
    },

    saveOrUpdate(teacher) {
      this.$refs[teacher].validate((valid) => {
        if (valid) {
          if (this.teacher.id) { // 包含id，修改
            teacherApi.update(this.teacher).then(response => {
              // 提示信息
              this.$message.success(response.message)
              // 回到列表页面, 路由跳转
              this.$router.push('/teacher/table')
            })
          } else { // 不包含id, 保存
            teacherApi.save(this.teacher).then(response => {
              // 提示信息
              this.$message.success(response.message)
              // 回到列表页面, 路由跳转
              this.$router.push('/teacher/table')
            })
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
