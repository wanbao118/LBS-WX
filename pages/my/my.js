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
  },

  onShow: function () {
    // var iData = this.data.userInfo
    // console.log("app.gData.editValue:", app.gData.editValue);
    // if (app.gData.editValue!=""){
    //   iData.description = app.gData.editValue.value;
    //   app.gData.editValue = ""
    // }
    this.setData({
      userInfo: app.gData.userInfo
    }); 
  },
  
  editDescription:function(){
    wx.navigateTo({
      url: 'editDesc'
    })



  }
})