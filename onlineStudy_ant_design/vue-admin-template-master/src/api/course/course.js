import request from '@/utils/request'

export default {
  // 课程查询所有
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
  },

  // 修改课程基本信息
  update(course) {
    return request({
      url: '/eduService/course/update',
      method: 'post',
      data: course
    })
  },

  // 根据id获取
  getInfoById(id) {
    return request({
      url: '/eduService/course/getCourseInfoById/' + id,
      method: 'get',
    })
  },

  // 最终发布课程获取页面显示信息
  getPublishCourse(id) {
    return request({
      url: '/eduService/course/getCoursePublish/' + id,
      method: 'get',
    })
  },

  // 最终发布课程
  coursePublish(id) {
    return request({
      url: '/eduService/course/coursePublish/' + id,
      method: 'get',
    })
  },

  // 分页查询
  coursePage(course, pageNum, pageSize) {
    return request({
      url: `/eduService/course/coursePage/${pageNum}/${pageSize}`,
      method: 'post',
      data: course
    })
  },

  // 最终发布课程
  delete(id) {
    return request({
      url: '/eduService/course/delete/' + id,
      method: 'get',
    })
  }
}
