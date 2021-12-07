<template>
  <div style="padding: 10px 0 0 30px;">
    <el-form :inline="true" :model="course" class="demo-form-inline" style="width: 100%; padding: 10px 10px">
      <el-form-item label="课程标题">
        <el-input v-model="course.title" placeholder="课程标题"/>
      </el-form-item>

      <el-form-item label="课程状态">
        <el-select v-model="course.status" placeholder="课程状态">
          <el-option label="未发布" value="Draft"/>
          <el-option label="已发布" value="Normal"/>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getCourseList()">查询</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="list"
      style="width: 100%; padding: 10px 10px">

      <el-table-column
        label="课程标题"
        width="400"
        prop="title"/>

      <el-table-column
        label="课程销售价格"
        width="360"
        prop="price"/>

      <el-table-column
        label="总课时"
        width="360"
        prop="lessonNum"/>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <router-link :to="'/course/edit/'+scope.row.id">
            <el-button size="mini" type="primary" icon="el-icon-edit">编辑</el-button>
          </router-link>
          <el-button
            size="mini"
            type="danger"
            @click="remove(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  分页  -->
    <el-pagination
      :current-page="pageNum"
      :page-size="pageSize"
      :total="total"
      style="padding: 30px 0; text-align: center"
      background
      layout="total, prev, pager, next, jumper"
      @current-change="getCourseList"/>

  </div>

</template>

<script>
    import courseApi from "../../../api/course/course";

    export default {
        data() {
            return {
                pageNum: 1, // 当前页
                pageSize: 3, // 每页记录数
                total: 0,
                course: {},
                list: null
            }
        },

        created() {
            this.getCourseList()
        },

        methods: {
            // 获取数据列表
            getCourseList(page = 1) {
                // 获取当前页码、赋值，默认第一页
                this.pageNum = page
                courseApi.coursePage(this.course, this.pageNum, this.pageSize)
                    .then(response => { // 请求成功
                        this.list = response.data.page.records
                        this.total = response.data.page.total
                    })
                    .catch(error => { // 请求失败
                        console.log(error)
                    })
            },

            // 删除
            remove(id) {
                this.$confirm('此操作将该条数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    // 点击确定
                    courseApi.delete(id).then(response => {
                        this.$message({
                            type: 'success',
                            message: response.message
                        })
                        this.getCourseList()
                    }).cache(error => {
                        console.log(error)
                    })
                }).catch(() => { // 点击取消
                })
            },

            // 清空
            resetForm() {
                this.course = {}
                this.getCourseList()
            }
        }
    }
</script>

<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
