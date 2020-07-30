const getUser = (func) => {
    var user = JSON.parse(localStorage.getItem('tvility'));
    func({user: user});
}
const GetUserApi = {
    getUser:(func)=>getUser(func)
    
}

export default GetUserApi
