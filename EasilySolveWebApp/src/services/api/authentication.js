import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

const authentication = axios.create({
    baseURL: API_URL,
});

authentication.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

authentication.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

const login = async (email) => {
    try {
        const response = await await authentication.post(`/login`, {
            email
        });
        if (response.data.token) {
            localStorage.setItem('token', response.data.token); 
        }
        return response.data;
    } catch (error) {
        throw error.response ? error.response.data : error.message;
    }
};

const register = async (firstName, lastName, email) => {
    try {
        const response = await authentication.post(`/register`, {
            firstName,
            lastName,
            email
        });
        return response.data;
    } catch (error) {
        throw error.response ? error.response.data : error.message;
    }
};

const logout = () => {
    localStorage.removeItem('token'); 
};

const getCurrentUser = () => {
    return localStorage.getItem('token');
};

export default {
    login,
    register,
    logout,
    getCurrentUser
};