
export default {
  changeTitle: (state, title) => {
    state.tagName = title
  },
  show: (state, status) => {
    state.status = status
  },
  userId: (state, userId) => {
    state.userId = userId
  },
  loadUser: (state, user) => {
    state.user = user
  },
  clear: (state) => {
    state.user = null
  },
  syncIndex: (state, index) => {
    state.index = index
  },
  loadArticles: (state, articles) => {
    state.articles = articles
  },
  updateArticle: (state, payload) => {
    payload.filter(data => {
      state.articles.push(data)
    })
  },
  updateTrendIndex: (state, payload) => {
    state.trendindex = payload
  },
  loadMarks: (state, payload) => {
    state.marks = payload
  },
  populate: (state, payload) => {
    state.search = payload
  },
  loadOauth: (state, payload) => {
    state.user = payload
  }
}
