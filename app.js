//app.js
App({
  globalData: {
    od: ""
  },
  onLaunch: function () {

   // console.log("onLaunch");
    var that = this;
    that.systemInfo();
    //that.userInfo();


     
  },
  onLaunch: function () {
    var that=this;
    that.systemInfo();
   that.userInfo();
    

  },

  //获取用户信息
  /*1. 调用微信的登陆接口wx.login,得到code，五分钟有效
    2. 拿到code，发送到lbs服务器后端，后端调用微信接口，用code换来openid和session_key等，lbs后端将openid返回给微信前端
    3. 调用微信接口wx.getUserInfo获取用户信息，并将前面获得的用户的openid加到用户信息里
    4. 将用户信息存到本机和远程数据库里（第一次登陆）。
   */

  //全局方法，index.js中调用
  userInfo: function () {
    var that=this;
   // console.log("userInfo:");
    //1.调用登录接口
    wx.login({
      success: function (res) {
        // wx.login:success
       // console.log("wx.login:" + res.data);
        var od;// 存储OpenId，微信用户唯一值
        if (res.code) {
          //code 换成 openid 和 session_key, 
          wx.request({
            url: 'https://littlebearsports.com/bearsport/service/user/Login',
            data: {
              code: res.code
            },
            method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
            // header: {}, // 设置请求的 header
            success: function (res) {
              od = res.data.params.openId 
              that.getInfo(od);
            },
            fail: function (res) {
              // fail
            },
            complete: function (res) {
              // complete
            }
          })

          
        } else {
          console.log('获取用户登录态失败！' + res.errMsg);
        }
      },
      fail: function (res) {
        // wx.login:fail
      },
      complete: function (res) {
        // wx.login:complete
      }
    })
  },


  //获取系统信息
  systemInfo: function () {


 userInfo:function(){
     
      //调用登录接口
      wx.login({
          success: function(res){
            // wx.login:success
            var od;// 存储OpenId，微信用户唯一值
            if(res.code){
              //code 换成 openid 和 session_key, 
              wx.request({
                url: 'http://127.0.0.1:3000/users/wxlogin',
                data: {
                  code: res.code
},
                method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
                // header: {}, // 设置请求的 header
                success: function(res){
                  var a =JSON.parse(res.data)
                   od = a.openid
                },
                fail: function(res) {
                  // fail
                },
                complete: function(res) {
                  // complete
                }
              })
              //获取用户信息
              wx.getUserInfo({
                success: function(res){
                  //将用户信息储存到本机
                  //增加Openid
                  res.userInfo.openid=od
                 
                  wx.setStorage({
                    key: 'userInfo',
                    data: res.userInfo
,
                    success: function(res){
                      // success
                    },
                    fail: function(res) {
                      // fail
                    },
                    complete: function(res) {
                      // complete
                    }
                  })
                  //将用户信息储存到mangoDB:lbs数据库
                  wx.request({
                    url: 'http://127.0.0.1:3000/users/addUser',
                    data: res.userInfo,
                    method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
                    // header: {}, // 设置请求的 header
                    success: function(res){
                      // success
                    console.log(res.data);
                    },
                    fail: function(res) {
                      // fail
                    },
                    complete: function(res) {
                      // complete
                    }
                  })
                },
                fail: function(res) {
                  // wx.getUserInfo:fail
                },
                complete: function(res) {
                  // wx.getUserInfo:complete
                }
              })
            }else{
              console.log('获取用户登录态失败！' + res.errMsg);
            }
          },
          fail: function(res) {
            // wx.login:fail
          },
          complete: function(res) {
            // wx.login:complete
          }
        })
  },
 

  //获取系统信息
  systemInfo:function () {

    wx.getSystemInfo({
      success: function (res) {
        // success
        wx.setStorage({
          key: 'systemInfo',
          data: res,
          success: function (res) {
            // success
          },
          fail: function () {
            // fail
          },
          complete: function () {
            // complete
          }
        })
      }
    })

  },

  //获取用户信息
  getInfo:function(wxid){
   // console.log("wxid" + wxid)
    //2.获取用户信息
    wx.getUserInfo({
      success: function (res) {
        //将用户信息储存到本机
        //增加Openid
       // console.log("wx.getUserInfo:" + res.data);
        res.userInfo.openId = wxid
        res.userInfo.operationCode = 'AD'

        wx.setStorage({
          key: 'userInfo',
          data: res.userInfo
          ,
          success: function (res) {
            // success
            console.log("wx.setStorage:" + res.data);
          },
          fail: function (res) {
            // fail
          },
          complete: function (res) {
            // complete
          }
        })
        //将用户信息储存到mangoDB:lbs数据库
        wx.request({
          url: 'https://littlebearsports.com/bearsport/service/user/userMaintain',
          data: res.userInfo,
          method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
          // header: {}, // 设置请求的 header
          success: function (res) {
            // success
            console.log(res.data);
          },
          fail: function (res) {
            // fail
          },
          complete: function (res) {
            // complete
          }
        })
      },
      fail: function (res) {
        // wx.getUserInfo:fail
      },
      complete: function (res) {
        // wx.getUserInfo:complete
      }
    })
  },

  

  }

})