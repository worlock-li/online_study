import request from '@/utils/request'

export default {
  // 课程分页查询
  getAllChapterVideo(courseId) {
    return request({
      url: '/eduService/chapter/getChapterList/' + courseId,
      method: 'get'
    })
  },

  // 添加章节
  addChapter(chapter) {
    return request({
      url: '/eduService/chapter/add',
      method: 'post',
      data: chapter
    })
  },

  // 修改章节
  updateChapter(chapter) {
    return request({
      url: '/eduService/chapter/update',
      method: 'post',
      data: chapter
    })
  },

  // 根据id查询
  getChapterById(id) {
    return request({
      url: '/eduService/chapter/getChapterById/' + id,
      method: 'get',
    })
  },

  // 根据id删除
  deleteById(id) {
    return request({
      url: '/eduService/chapter/delete/' + id,
      method: 'delete',
    })
  },
}
