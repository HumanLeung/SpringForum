export default {
  getTitle: (state) => {
    return state.tagName
  },
  getStatus: (state) => {
    return state.status
  },
  getUserId: (state) => {
    return state.userId
  },
  getUserPayload: (state) => {
    console.log(state.user)
    return state.user
  },
  getIndex: (state) => {
    console.log(state.index)
    return state.index
  },
  getAllArts: (state) => {
    console.log(state.articles)
    return state.articles
  },
  getTrendIndex: (state) => {
    console.log(state.trendindex)
    return state.trendindex
  },
  getAllArt: (state) => {
    console.log(state.articles)
    return state.articles
  },
  getMarks: (state) => {
    console.log(state.marks)
    return state.marks
  },
  getSearch: (state) => {
    console.log(state.search)
    return state.search
  }
}
