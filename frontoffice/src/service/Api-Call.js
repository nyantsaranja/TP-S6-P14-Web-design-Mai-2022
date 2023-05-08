export const BASE_URL = 'https://backendblogai-production.up.railway.app';
// export const BASE_URL = 'http://localhost:8080';
export const CONFIG = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
    }
};