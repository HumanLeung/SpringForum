import axios from 'axios'

export default {
  changeTag: ({ commit }, payload) => {
    console.log(payload)
    commit('changeTitle', payload)
  },
  syncIndex: ({ commit }, payload) => {
    console.log(payload)
    commit('syncIndex', payload)
  },
  changeBG: ({ commit }, payload) => {
    console.log(payload)
    commit('show', payload)
  },
  sendUserId: ({ commit }, payload) => {
    console.log(payload)
    commit('userId', payload)
  },
  loadUser: ({ commit }, payload) => {
    console.log(payload)
    commit('loadUser', payload)
  },
  getUsers: ({ commit }) => {
    axios.get('http://localhost:8080/api/entry/freelogin', {
      headers: {
        Authorization: localStorage.token
      }
    }).then(res => {
      axios.get('http://localhost:8080/api/user/load/' + res.headers.check, {
        headers: {
          Authorization: localStorage.token
        }
      }).then(res => {
        console.log(res.data.user)
        localStorage.userNum = res.data.user.userNum
        localStorage.uid = res.data.user.id
        commit('loadUser', res.data.user)
      })
    }).catch(err => {
      console.log(err)
    })
  },
  phoneUser: ({ commit }) => {
    axios.get('http://localhost:8080/api/entry/freelogin', {
      headers: {
        Authorization: localStorage.phoneJwt
      }
    }).then(res => {
      axios.get('http://localhost:8080/api/user/load/' + res.headers.check, {
        headers: {
          Authorization: localStorage.phoneJwt
        }
      }).then(res => {
        console.log(res.data.user)
        localStorage.userNum = res.data.user.userNum
        localStorage.uid = res.data.user.id
        commit('loadUser', res.data.user)
      })
    }).catch(err => {
      console.log(err)
    })
  },
  clear: ({ commit }) => {
    commit('clear')
  },
  getArticles: ({ commit }, payload) => {
    var headerInfo
    if (localStorage.userType === '2') {
      headerInfo = {
        Oauth2: 'oauth2 ' + localStorage.userNum,
        Authorization: 'null'
      }
    } else {
      headerInfo = {
        Authorization: localStorage.token
      }
    }
    axios.get('http://localhost:8080/api/article/getArticles/' + payload, {
      headers: headerInfo
    })
      .then(res => {
        console.log(res.data)
        commit('loadArticles', res.data)
      })
  },
  updateArticle: ({ commit }, payload) => {
    console.log(payload)
    commit('updateArticle', payload)
  },
  updateIndex: ({ commit }, payload) => {
    console.log(payload)
    commit('updateTrendIndex', payload)
  },
  loadMarks: ({ commit }, payload) => {
    console.log(payload)
    commit('loadMarks', payload)
  },
  search: ({ commit }, payload) => {
    var headerInfo
    if (localStorage.userType === '2') {
      headerInfo = {
        Oauth2: 'oauth2 ' + localStorage.userNum,
        Authorization: 'null'
      }
    } else {
      headerInfo = {
        Authorization: localStorage.token
      }
    }
    axios.get(`http://localhost:8080/api/article/search/${payload}`, {
      headers: headerInfo
    }).then(res => {
      console.log(res.data)
      commit('populate', res.data)
    })
  },
  loadOauth: ({ commit }) => {
    var key = localStorage.oauthKey
    axios.get(`http://localhost:8080/api/oauth2/getInfo/${key}`)
      .then(res => {
        localStorage.oauthID = res.data.userNum
        localStorage.userNum = res.data.userNum
        localStorage.userType = res.data.userType
        localStorage.uid = res.data.id
        console.log(res.data)
        commit('loadOauth', res.data)
      })
  },
  getUserByNum: ({ commit }) => {
    var userNum = localStorage.oauthID
    var oauthToken = 'oauth2 ' + userNum
    console.log(oauthToken)
    axios.get(`http://localhost:8080/api/oauth2/getUser/${userNum}`, {
      headers: {
        Oauth2: oauthToken,
        Authorization: 'null'
      }
    })
      .then(res => {
        console.log(res)
        commit('loadUser', res.data)
      })
  }
}
