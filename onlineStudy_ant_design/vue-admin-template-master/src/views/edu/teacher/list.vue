<template>
  <el-table
      :data="list"
      style="width: 100%">

    <el-table-column
        label="讲师姓名"
        width="180"
        prop="name">
    </el-table-column>

    <el-table-column
        label="讲师简介"
        width="180"
        prop="intro">
    </el-table-column>

    <el-table-column
        label="头衔"
        width="180">
      <template slot-scope="scope">
        <span>{{scope.row.level === 1 ?'高级讲师':'首席讲师'}}</span>
      </template>
    </el-table-column>

    <el-table-column
        label="头像"
        width="180"
        prop="avatar">
    </el-table-column>

    <el-table-column
        label="创建时间"
        width="180"
        prop="createTime">
    </el-table-column>

    <el-table-column
        label="创建时间"
        width="180"
        prop="updateTime">
    </el-table-column>

    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>



<script>
import teacher from "../../../api/teacher/teacher";

  export default {
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        teacherQuery: {},
        list: null

      }
    },

    created() {
      this.getList()
    },

    methods: {
      getList() {
        teacher.getTeacherListPage(this.pageNum, this.pageSize, this.teacherQuery)
            .then(response => { // 请求成功
              this.list = response.data.page.records
              this.total = response.data.page.total
              console.log(this.list)
              console.log(this.total)
              console.log(response)
            })
            .catch(error => { // 请求失败
              console.log(error)
            })
      },

      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
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