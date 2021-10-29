import request from '@/utils/request'

export default {
  // 课程分页查询
  getAllSubject() {
    return request({
      url: '/eduService/subject/getAllSubject',
      method: 'get'
    })
  },

  // 添加课程
  addCourseInfo(course) {
    return request({
      url: '/eduService/course/addCourseInfo',
      method: 'post',
      data: course
    })
  }
}