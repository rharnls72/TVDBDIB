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
    http.get(`/episode/${data.pno}/${data.season}/${data.episode}`, header())
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

const readReply = (data, callback, errorCallback) => {

    http.post('/reply/episode/read', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯

            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 삭제 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 삭제 실패
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

const deleteEpisodeLike = (data,callback,errorCallback) => {
    // 피드 좋아요 삭제
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.post('/like/episode/delete', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯

            console.log(data)

            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 삭제 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 삭제 실패
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

const createEpisodeLike = (data,callback,errorCallback) => {
    // 피드 좋아요 생성
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.post('/like/episode/create', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 생성 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 생성 실패
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

const createEpisodeReply = (data,callback,errorCallback) => {
    // 피드 좋아요 생성
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출

    http.post('/reply/episode/create', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 생성 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 생성 실패
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

const deleteEpisodeReply = (data,callback,errorCallback) => {
    // 피드 좋아요 생성
    // 성공 : call back 호출
    // 실패 :  errorCallback 호출
    console.log(data)

    http.post('/reply/episode/delete', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 생성 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 생성 실패
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

const readReReply = (data,callback,errorCallback) => {

    http.post('/rereply/episode/read', data, header())
        .then(res => {
            // 서버에서 정상적으로 처리되었으면 res 가 null 이 될 수 없음
            // 서버에서 db 쿼리 하다 오류난 경우일듯
            if(res == null) {
                let error = {msg : '알 수 없는 오류 발생'};
                errorCallback(error);
            }
            // 서버에서 처리되어 데이터가 제대로 넘어왔을 때
            else {
                // 피드 좋아요 생성 성공
                if(res.data.status) {
                    callback(res);
                }
                // 피드 좋아요 생성 실패
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

const createEpisodeDibs = (data,callback,errorCallback) => {
    http.post('/dibs/episode/create', data, header())
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

const deleteEpisodeDibs = (data,callback,errorCallback) => {
    http.delete('/dibs/episode/delete/' + data, header())
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

const programDetail = (data,callback,errorCallback) => {
    http.get('/program/detail/' + data, header())
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

const programFollower = (data,callback,errorCallback) => {

    http.get('/following/program/followers/' + data, header())
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

const programReplyRead = (data,callback,errorCallback) => {

    http.post('/reply/program/read', data, header())
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

const programReplyCreate = (data,callback,errorCallback) => {

    http.post('/reply/program/create', data, header())
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

const programReplyDelete = (data,callback,errorCallback) => {

    http.post('/reply/program/delete', data, header())
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

const programReReplyRead = (data,callback,errorCallback) => {

    http.post('/rereply/program/read', data, header())
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

const programAddFollow = (data,callback,errorCallback) => {

    http.post('/following/program/add', data, header())
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

const programDeleteFollow = (data,callback,errorCallback) => {

    http.post('/following/program/cancel', data, header())
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

const programCreateLike = (data,callback,errorCallback) => {

    http.post('/like/program/create', data, header())
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

const programDeleteLike = (data,callback,errorCallback) => {

    http.post('/like/program/delete', data, header())
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

const CurationApi = {
    requestEpisode:(callback,errorCallback)=>requestEpisode(callback,errorCallback)
    , requestEpisodeDetail:(data,callback,errorCallback)=>requestEpisodeDetail(data,callback,errorCallback)

    , createEpisodeLike:(data,callback,errorCallback)=>createEpisodeLike(data,callback,errorCallback)
    , deleteEpisodeLike:(data,callback,errorCallback)=>deleteEpisodeLike(data,callback,errorCallback)

    , createEpisodeDibs:(data,callback,errorCallback)=>createEpisodeDibs(data,callback,errorCallback)
    , deleteEpisodeDibs:(data,callback,errorCallback)=>deleteEpisodeDibs(data,callback,errorCallback)

    , readReply:(data,callback,errorCallback)=>readReply(data,callback,errorCallback)
    , createEpisodeReply:(data,callback,errorCallback)=>createEpisodeReply(data,callback,errorCallback)
    , deleteEpisodeReply:(data,callback,errorCallback)=>deleteEpisodeReply(data,callback,errorCallback)
    , readReReply:(data,callback,errorCallback)=>readReReply(data,callback,errorCallback)
    , programDetail:(data,callback,errorCallback)=>programDetail(data,callback,errorCallback)
    , programFollower:(data,callback,errorCallback)=>programFollower(data,callback,errorCallback)
    , programReplyRead:(data,callback,errorCallback)=>programReplyRead(data,callback,errorCallback)
    , programReplyCreate:(data,callback,errorCallback)=>programReplyCreate(data,callback,errorCallback)
    , programReplyDelete:(data,callback,errorCallback)=>programReplyDelete(data,callback,errorCallback)
    , programReReplyRead:(data,callback,errorCallback)=>programReReplyRead(data,callback,errorCallback)
    , programAddFollow:(data,callback,errorCallback)=>programAddFollow(data,callback,errorCallback)
    , programDeleteFollow:(data,callback,errorCallback)=>programDeleteFollow(data,callback,errorCallback)
    , programDeleteLike:(data,callback,errorCallback)=>programDeleteLike(data,callback,errorCallback)
    , programCreateLike:(data,callback,errorCallback)=>programCreateLike(data,callback,errorCallback)
}

export default CurationApi
