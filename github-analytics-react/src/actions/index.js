import {gitHubApi} from '../api/api'

export const signIn = (username, password) => {
    return new Promise((resolve, reject) => {
        gitHubApi.post('login', {username, password}).then(response => {
            localStorage.setItem('jwtToken', response.data.jwt_token);
            localStorage.setItem('userId', response.data._id);
            gitHubApi.defaults.headers = {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('jwtToken')
            }
            resolve(response)
        }).catch(err => {
            reject(new Error('Invalid username or password'))
        })
    })

}

// export const signOut = () => async () => {
//     localStorage.removeItem("jwtToken");
//     localStorage.removeItem("userId");
//     await gitHubApi.post("users/signout").then(() => {
//         window.location.reload()
//     });
// };

export const searchRepositories = (queryString) => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.get('api/search/repository', {params: {queryString}}).then(response => {
            resolve(response.data.items)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const queryRepository = (id) => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.get(`api/repositories/${id}`).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const getCollaborators = (repositoryId) => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.get(`api/repositories/${repositoryId}/collaborators`).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const getBookmarks = () => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.get(`githubrepos`).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const deleteBookmark = (repositoryId) => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.delete(`githubrepos/${repositoryId}`).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const addBookmark = (repository) => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.post(`githubrepos`, repository).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const queryAnalytics = (repositoryId) => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.get(`api/analytics/repositories/${repositoryId}`,{
            params: {
                count: 100
            }
        }).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const queryLastCommits = (repositoryId) => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.get(`api/repositories/${repositoryId}/commits`,{
            params: {
                count: 100
            }
        }).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}

export const queryBookmarkIds = () => {
    return new Promise(async (resolve, reject) => {
        gitHubApi.get(`githubrepos/bookmark/ids`).then(response => {
            resolve(response.data)
        }).catch(err => {
            reject(new Error(err.message))
        })

    })
}


