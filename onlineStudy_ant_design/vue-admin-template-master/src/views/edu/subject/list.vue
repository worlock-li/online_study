<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree
      ref="tree2"
      :data="data2"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subject from '../../../api/subject/subject'

export default {

  data() {
    return {
      filterText: '',
      data2: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },

  created() {
    this.getAllSubject()
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.title.indexOf(value) !== -1
    },

    getAllSubject() {
      subject.getAllSubject().then(response => {
        console.log(response.data.data)
        this.data2 = response.data.data
      })
    }
  }
}
</script>
