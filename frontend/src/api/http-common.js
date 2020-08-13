import axios from "axios";

console.log(sessionStorage.getItem('jwt-token'))

export default axios.create({
    // baseURL: "http://192.168.219.106:9100" // My local
    baseURL: "http://localhost:9100"
    // baseURL: "http://i3a106.p.ssafy.io:8999"
});