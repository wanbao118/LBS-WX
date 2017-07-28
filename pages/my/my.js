// pages/my/my.js
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: 'Hello World',
    userInfo: {}

  },
  //事件处理函数
   
  getSystemInfo:function(){
    var res=wx.getStorageSync('systemInfo');
    wx.showModal({
  title: '系统信息',
  //真机换行起作用
  content: "手机型号："+res.model+"\r\n"+
  "设备像素比："+res.pixelRatio+"\r\n"+
  "窗口宽度："+res.windowWidth+"\r\n"+
  "窗口高度："+res.windowHeight+"\r\n"+
  "微信设置的语言："+res.language+"\r\n"+
  "微信版本号："+res.version+"\r\n"+
  "操作系统版本："+res.system+"\r\n"+
  "客户端平台："+res.platform+"\r\n",
  showCancel:false,
  success: function(res) {
    if (res.confirm) {
      console.log('确定')
    }
  }
})

  },

  
  onLoad: function () {
    console.log('onLoad');
     
    var res=wx.getStorageSync('userInfo');
   
    this.setData({
                  userInfo:res
              });

    this.getUserInfo();
  },

  onShow: function () {
    
    console.log("app.gData.editValue:", app.gData.editValue);
    var iData = this.data.userInfo
    iData.description = app.gData.editValue.value;
    this.setData({
      userInfo: iData
    });
      //this.data.userInfo.description = app.gData.editValue.value;
      
    
  },
  //获取用户信息
  getUserInfo:function(){
    var that = this;
    var iData = {};
    iData.operationCode = "UFO";
    iData.openId = this.data.userInfo.openId;
    wx.request({
      url: app.gData.iServerUrl + '/bearsport/service/user/userMaintain',
      data: iData,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      // header: {}, // 设置请求的 header
      success: function (res) {
        console.log("获取用户详情信息：", res);
        // res.data.listData[0].lastLoginTime = util.getLocalTime(res.data.listData[0].lastLoginTime);
        // res.data.listData[0].firstLoginTime = util.getLocalTime(res.data.listData[0].firstLoginTime);

        that.setData({
          userInfo: res.data.listData[0]
        });

      },
      fail: function (res) {
        // fail
      },
      complete: function (res) {
        // complete
      }
    })
  },

  editDescription:function(){
    wx.navigateTo({
      url: '../common/editValue?value=' + this.data.userInfo.description+'&type=text&desc=请输入一句话的自我描述'
    })
  }
})