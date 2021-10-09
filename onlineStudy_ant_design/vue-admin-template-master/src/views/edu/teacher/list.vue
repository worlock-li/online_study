<template>
  <div style="padding: 10px 0 0 30px;">
    <el-form :inline="true" :model="teacherQuery" class="demo-form-inline" style="width: 100%; padding: 10px 10px">
      <el-form-item label="讲师姓名">
        <el-input v-model="teacherQuery.name" placeholder="讲师姓名"/>
      </el-form-item>
      <el-form-item label="头衔">
        <el-select v-model="teacherQuery.level" placeholder="头衔">
          <el-option label="高级讲师" value="1"/>
          <el-option label="首席讲师" value="2"/>
        </el-select>
      </el-form-item>

      <el-form-item label="开始时间">
        <el-col :span="11">
          <el-date-picker
            v-model="teacherQuery.begin"
            type="datetime"
            value-format="yyyy-MM-dd:mm:ss"
            default-time="00:00:00"
            placeholder="选择日期"
            style="width: 100%;"/>
        </el-col>
        <el-col :span="2" class="line" style="padding-left: 15px">-</el-col>
        <el-col :span="11">
          <el-date-picker
            v-model="teacherQuery.end"
            type="datetime"
            value-format="yyyy-MM-dd:mm:ss"
            default-time="00:00:00"
            placeholder="结束时间"
            style="width: 100%;"/>
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="getList()">查询</el-button>
        <el-button @click="resetForm()">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="list"
      style="width: 100%; padding: 10px 10px">

      <el-table-column
        label="讲师姓名"
        width="200"
        prop="name"/>

      <el-table-column
        label="讲师简介"
        width="180"
        prop="intro"/>

      <el-table-column
        label="头衔"
        width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.level == 1 ?'首席讲师':'高级讲师' }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="头像"
        width="200"
        prop="avatar"/>

      <el-table-column
        label="创建时间"
        width="200"
        prop="createTime"/>

      <el-table-column
        label="更新时间"
        width="200"
        prop="updateTime"/>

      <el-table-column label="操作">
        <template slot-scope="scope">

          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button size="mini" type="primary" icon="el-icon-edit">编辑</el-button>
          </router-link>

          <el-button
            size="mini"
            type="danger"
            @click="removeTeacher(scope.row.id)">删除</el-button>
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
      @current-change="getList"/>

  </div>

</template>

<script>
import teacher from '../../../api/teacher/teacher'

export default {
  data() {
    return {
      pageNum: 1, // 当前页
      pageSize: 3, // 每页记录数
      total: 0,
      teacherQuery: {},
      list: null
    }
  },

  created() {
    this.getList()
  },

  methods: {
    // 获取数据列表
    getList(page = 1) {
      // 获取当前页码、赋值，默认第一页
      this.pageNum = page
      teacher.getTeacherListPage(this.pageNum, this.pageSize, this.teacherQuery)
        .then(response => { // 请求成功
          this.list = response.data.page.records
          this.total = response.data.page.total
        })
        .catch(error => { // 请求失败
          console.log(error)
        })
    },

    //
    // // 修改
    // teacherEdit(id) {
    //   this.$router.push('/teacher/save', id)
    // },

    // 删除
    removeTeacher(id) {
      this.$confirm('此操作将该条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 点击确定
        teacher.removeTeacher(id).then(response => {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.getList()
        }).cache(error => {
          console.log(error)
        })
      }).catch(() => { // 点击取消
      })
    },

    // 清空
    resetForm() {
      this.teacherQuery = {}
      this.getList()
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
