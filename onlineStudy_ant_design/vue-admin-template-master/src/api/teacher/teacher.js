import request from '@/utils/request'

export default {
  // 讲师分页查询
  getTeacherListPage(pageNum, pageSize, teacherQuery) {
    return request({
      url: `/edu/teacher/queryList/${pageNum}/${pageSize}`,
      method: 'post',
      // data 将条件对象转换为json字符串, 后台使用 @RequestBody 来接收
      data: teacherQuery
    })
  }
}
