import request from '@/utils/request'

export default {
  // 课程分页查询
  getAllChapterVideo(courseId) {
    return request({
      url: '/eduService/chapter/getChapterList/' + courseId,
      method: 'get'
    })
  }
}
