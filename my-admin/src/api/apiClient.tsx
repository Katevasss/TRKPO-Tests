import {fetchUtils} from "react-admin";

const apiUrl = function() {
    return 'http://localhost:8080';
}()
const httpClient = fetchUtils.fetchJson;

const apiClient = (path: string, params?: any) => {
    if (path.startsWith("/")) {
        path = path.substring(1)
    }
    return httpClient(`${apiUrl}/${path}`, params);
}

export default apiClient;