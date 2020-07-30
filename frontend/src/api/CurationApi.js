import http from "./http-common.js";
import header from "@/api/header.js"

const requestEpisode = (callback,errorCallback) => {
    http.get('/episode/following/1', header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback({list: res.data.data});
            }
            else {
                let error = {msg : res.data.msg};
                errorCallback(error);
            }
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const requestEpisodeDetail = (data, callback,errorCallback) => {
    http.get('/episode', {params: {id: data.id}}, header())
    .then(res => {
        if(res == null) {
            let error = {msg : '알 수 없는 오류 발생'};
            errorCallback(error);
        }else {
            if(res.data.status) {
                callback({list: res.data.data});
            }
            else {
                let error = {msg : res.data.msg};
                errorCallback(error);
            }
        }
    })
    .catch(error => {
        error.msg = '서버 요청에서 오류 발생';
        errorCallback(error);
    });
}
const CurationApi = {
    requestEpisode:(callback,errorCallback)=>requestEpisode(callback,errorCallback)
    , requestEpisodeDetail:(data,callback,errorCallback)=>requestEpisodeDetail(data,callback,errorCallback)
}

export default CurationApi
