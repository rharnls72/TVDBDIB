import axios from "axios";

console.log(sessionStorage.getItem('jwt-token'))

export default axios.create({
    // baseURL: "http://localhost:9000"
    baseURL: "http://i3a106.p.ssafy.io:9000"
});