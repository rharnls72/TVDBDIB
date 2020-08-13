
import http from "./http-common.js";
import header from "./header.js"

const createVote = (data,callback,errorCallback) => {
    http.post('/vote/create', data, header())
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            else {
                if(res.data.status) {
                    callback(res);
                }
                else {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                }
            }
        })
        .catch(err => {
            err.msg = '서버 요청에서 오류 발생';
            errorCallback(err);
        });
}

const deleteVote = (data,callback,errorCallback) => {
    http.delete('/vote/delete/' + data, header())
        .then(res => {
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            else {
                if(res.data.status) {
                    callback(res);
                }
                else {
                    let error = {msg : res.data.msg};
                    errorCallback(error);
                }
            }
        })
        .catch(err => {
            err.msg = '서버 요청에서 오류 발생';
            errorCallback(err);
        });
}

const VoteApi = {
    createVote:(data,callback, errorCallback)=>createVote(data,callback, errorCallback)
    , deleteVote:(data,callback, errorCallback)=>deleteVote(data,callback, errorCallback)
}

export default VoteApi