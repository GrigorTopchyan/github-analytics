import axios from 'axios';
import history from '../history';

export const gitHubApi = axios.create({
    proxy: true,
    baseURL: process.env.REACT_APP_GITHUB_APP_URI,
    timeout: 6000000,
    headers: {
        'Authorization': localStorage.getItem('jwtToken')
    },
})

gitHubApi.interceptors.response.use(
    function(response) {
        return response;
    },
    function(error) {
        if (error.response.status === 401) {
            localStorage.removeItem('jwtToken');
            history.push('/');
        }
        return Promise.reject(error);
    }
);

