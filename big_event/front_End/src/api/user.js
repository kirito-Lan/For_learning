//导入request.js请求
import request from '@/utils/request'
//提供调用注册接口的函数
export const userRegisterService = (registerData) => {
    //直接传的话会传成Json格式 我们需要 x-www.form-urlencoding
    const params = new URLSearchParams();
    for (let key in registerData) {
        params.append(key, registerData[key])
    }
    return request.post('/users/register', params);

}

export const userLoginService = (loginData) => {
    const params = new URLSearchParams();
    for (let key in loginData) {
        params.append(key, loginData[key])
    }
    return request.post('/users/login', params)
}

//获取个人信息
export const userInfoGetService = () => {
    return request.get('/users/getUserInfo')
}


//修改个人信息
export const userInfoUpdateService = (userInfo) => {
    return request.put('/users/updateInfo', userInfo)
}


//修改头像
export const userAvatarUpdateService=(avatarUrl)=>{
    let params = new URLSearchParams();
    params.append('imgUrl',avatarUrl)
    return request.patch('/users/setAvatar',params)
}

//修改密码
export const updateUserPasswordService = (passwordData) => {
    return request.patch('users/modifyPwd', passwordData);
}
