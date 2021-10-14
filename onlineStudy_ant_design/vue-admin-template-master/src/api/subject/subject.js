import request from '@/utils/request'

export default {
  // 讲师分页查询
  getAllSubject() {
    return request({
      url: '/eduService/subject/getAllSubject',
      method: 'get'
    })
  }
}
