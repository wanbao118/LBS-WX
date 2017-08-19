//app.js
var bmap = require('/libs/bmap-wx.js');
const ak = 'bfwtSbwjqSnIPWGIjKssrQdsPZn0Q87g';
var wxMarkerData = [];

App({
  gData: {
    openId: "",
    //后台服务器地址！
    iServerUrl: "https://littlebearsports.com",
    markers: [],
    latitude: '',
    longitude: '',
    cityName: '',
    editValue: '',
    userInfo: {}
  },
  onLaunch: function (options) {

    console.log("***** App onLaunch:小程序开始运行");
    console.log("***** 入口参数options：", options);

    this.getCity();
    this.login();
    this.systemInfo();
    // wx.navigateTo({
    //   url: '/pages/setup/setup'
    // })
  },


  //登录
  /*1. 调用微信的登陆接口wx.login,得到code，五分钟有效
    2. 拿到code，发送到lbs服务器后端，后端调用微信接口，用code换来openid和session_key等，lbs后端将openid返回给微信前端
    3. 调用微信接口wx.getUserInfo获取用户信息，并将前面获得的用户的openid加到用户信息里
    4. 将用户信息存到远程数据库里（第一次登陆）。
   */
  login: function () {
    console.log("***** 用户登录");
    var that = this;
    //1.调用微信登录接口获取openId
    wx.login({
      success: function (res) {
        var openId;// 存储OpenId，微信用户唯一值
        if (res.code) {
          //code 换成 openid 和 session_key, 
          wx.request({
            url: that.gData.iServerUrl + '/bearsport/service/user/Login',
            data: {
              code: res.code
            },
            method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
            header: { 'content-type': 'application/json' }, // 设置请求的 header
            success: function (res) {
              console.log("openId:",res);
              openId = res.data.params.openId;
              that.gData.userInfo.openId = openId;
              
              //存储用户信息
              that.getInfo(openId);
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
        res.userInfo.openId = wxid

        res.userInfo.location = {
          "latitude": that.gData.latitude,
          "longitude": that.gData.longitude
        }
        res.userInfo.loginCity = that.gData.cityName

        console.log("登录用户信息：", res.userInfo);
        
        

        res.userInfo.operationCode = 'AD'
        //将用户信息储存到后台数据库
        console.log("将用户信息储存到后台数据库");
        wx.request({
          url: that.gData.iServerUrl + '/bearsport/service/user/userMaintain',
          data: res.userInfo,
          method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
          // header: {}, // 设置请求的 header
          success: function (res) {
            // success
           
           that.getUser();
           
           
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
  //获取城市信息
  getCity: function () {
    var that = this;
    // 新建百度地图对象 
    var BMap = new bmap.BMapWX({
      ak: ak
    });
    var fail = function (data) {
      console.log(data)
    };
    var success = function (data) {
      wxMarkerData = data.wxMarkerData;

      that.gData.markers = wxMarkerData;
      console.log("登录地理信息:", that.gData.markers);

      that.gData.latitude = wxMarkerData[0].latitude
      that.gData.longitude = wxMarkerData[0].longitude
      that.gData.cityName = wxMarkerData[0].city
    }
    // 发起regeocoding检索请求 
    BMap.regeocoding({
      fail: fail,
      success: success,
      //  iconPath: '../../img/marker_red.png', 
      //  iconTapPath: '../../img/marker_red.png' 
    });

  },

  //获取用户信息
  getUser: function () {
    var that = this;
    var iData = {};
    iData.operationCode = "UFO";
    iData.openId = that.gData.userInfo.openId;
    console.log("iData:",iData);
    wx.request({
      url: that.gData.iServerUrl + '/bearsport/service/user/userMaintain',
      data: iData,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      // header: {}, // 设置请求的 header
      success: function (res) {
        console.log("获取用户详情信息：", res);
        // res.data.listData[0].lastLoginTime = util.getLocalTime(res.data.listData[0].lastLoginTime);
        // res.data.listData[0].firstLoginTime = util.getLocalTime(res.data.listData[0].firstLoginTime);

          that.gData.userInfo=res.data.listData[0]

      },
      fail: function (res) {
        // fail
      },
      complete: function (res) {
        // complete
      }
    })
  }
})