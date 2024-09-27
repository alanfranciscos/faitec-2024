import axios from 'axios';

const baseURL = 'http://localhost:8080';

export const api = axios.create({
  baseURL: baseURL,
});

export const setToken = (token: string) => {
  api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
};
