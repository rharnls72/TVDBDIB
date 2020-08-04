const addReply = (data,actFunction,callback,errorCallback) => {
  actFunction(data,callback,errorCallback)
}

const delReply = (data,actFunction,callback,errorCallback) => {
  actFunction(data,callback,errorCallback)
}

const ReplyApi = {
    addReply:(data,actFunction,callback,errorCallback)=>addReply(data,actFunction,callback,errorCallback)
    , delReply:(data,actFunction,callback,errorCallback)=>delReply(data,actFunction,callback,errorCallback)
}

export default ReplyApi
