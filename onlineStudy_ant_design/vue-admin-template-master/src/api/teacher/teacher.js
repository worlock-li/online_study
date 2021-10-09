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
  },

  // 删除讲师
  removeTeacher(id) {
    return request({
      url: `/edu/teacher/deleteById/${id}`,
      method: 'delete'
    })
  },

  // 添加讲师
  save(teacher) {
    return request({
      url: '/edu/teacher/save',
      method: 'post',
      data: teacher
    })
  },

  // 根据id获取
  getById(id) {
    return request({
      url: `/edu/teacher/getById/${id}`,
      method: 'get'
    })
  },

  // 根据id获取
  update(teacher) {
    return request({
      url: '/edu/teacher/update',
      method: 'post',
      data: teacher
    })
  }
}
