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
    editValue:''
  },
  onLaunch: function (options) {

    console.log("***** App onLaunch:小程序开始运行");
    console.log("***** 入口参数options：", options);

    this.getCity();
    //this.userInfo();
    this.systemInfo();

  },


  //登录
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
            url: that.gData.iServerUrl + '/bearsport/service/user/Login',
            data: {
              code: res.code
            },
            method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
            header: { 'content-type': 'application/json' }, // 设置请求的 header
            success: function (res) {
              console.log("success:", res);
              openId = res.data.params.openId
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
      console.log("登录地理信息:",that.gData.markers);
  
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
})