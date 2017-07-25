//app.js
App({
  globalData: {
    openId: "",
    //后台服务器地址！
    iServerUrl:"https://littlebearsports.com"
  },
  onLaunch: function (options) {

    console.log("***** App onLaunch:小程序开始运行");
    console.log("***** 入口参数options：",options);
    
    this.userInfo();
    
    this.systemInfo();

  },

  
  //获取用户信息
  /*1. 调用微信的登陆接口wx.login,得到code，五分钟有效
    2. 拿到code，发送到lbs服务器后端，后端调用微信接口，用code换来openid和session_key等，lbs后端将openid返回给微信前端
    3. 调用微信接口wx.getUserInfo获取用户信息，并将前面获得的用户的openid加到用户信息里
    4. 将用户信息存到本机和远程数据库里（第一次登陆）。
   */
  userInfo: function () {
    console.log("***** 获取用户信息");
    var that = this;
    //1.调用登录接口
    wx.login({
      success: function (res) {
        console.log("success:", res);
        var openId;// 存储OpenId，微信用户唯一值
        if (res.code) {
          //code 换成 openid 和 session_key, 
          wx.request({
            url: that.globalData.iServerUrl+'/user/wxlogin',
            data: {
              code: res.code
            },
            method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
            header: { 'content-type': 'application/json'}, // 设置请求的 header
            success: function (res) {
              console.log("success:",res);
              //var a = JSON.parse(res.data)
              openId = a.openid
              that.getInfo(od);
            },
            fail: function (res) {
              // fail
              console.log("fail:", res);
            },
            complete: function (res) {
              // complete
              console.log("complete:", res);
            }
          })


        } else {
          console.log('获取用户登录态失败！' + res.errMsg);
        }
      },
      fail: function (res) {
        // wx.login:fail
        console.log("fail:", res);
      },
      complete: function (res) {
        // wx.login:complete
        console.log("complete:", res);
      }
    })
  },


  //获取系统信息
  systemInfo: function () {
    console.log("***** 获取用户系统信息");
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
  getInfo: function (wxid) {
    var that = this;
    // console.log("wxid" + wxid)
    //2.获取用户信息
    wx.getUserInfo({
      success: function (res) {
        //将用户信息储存到本机
        //增加Openid
        // console.log("wx.getUserInfo:" + res.data);
        res.userInfo.openid = wxid

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
        //将用户信息储存到后台数据库
        wx.request({
          url: that.globalData.serverUrl + '/user/addUser',
          data: res.userInfo,
          method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
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

})