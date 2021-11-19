import request from '@/utils/request'

export default {

  // 添加小节
  addVideo(video) {
    return request({
      url: '/eduService/video/add',
      method: 'post',
      data: video
    })
  },

  // 修改小节
  updateVideo(video) {
    return request({
      url: '/eduService/video/update',
      method: 'post',
      data: video
    })
  },

  // 根据id查询
  getVideoById(id) {
    return request({
      url: '/eduService/video/getById/' + id,
      method: 'get',
    })
  },

  // 根据id删除
  deleteById(id) {
    return request({
      url: '/eduService/video/delete/' + id,
      method: 'delete',
    })
  },
}
