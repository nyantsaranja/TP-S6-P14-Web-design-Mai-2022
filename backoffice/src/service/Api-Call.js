export const BASE_URL = 'https://backendblogai-production.up.railway.app';
export const CONFIG = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
    }
};