// pages/friend/showFriend.js
var app = getApp()
var util = require('../../common/util.js');
Page({
  data:{
   person:[],
   userInfo: {},
   grids: [0, 1, 2, 3, 4, 5],
   myActiveCount:0,
   openid:''
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
  console.log("页面接收参数：",options)
  var that = this;  
  that.setData({   
    userInfo:options,    
  });
  //从storage获取openid
  wx.getStorage({
    key: 'userInfo',
    success: function (res) {
      that.setData({
         openid :res.data.openid
      });
      console.log("oid:" + res.data.openid);
      //获取参加活动数量信息
      that.getActiveCount(); 
    }
  })

 //获取用户信息
  var iData = {};
  iData.operationCode = "UFO";
  iData.openId = this.data.userInfo.openId;
  wx.request({
    url: app.gData.iServerUrl + '/bearsport/service/user/userMaintain',
          data: iData,
          method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
          // header: {}, // 设置请求的 header
          success: function(res){
             console.log("获取用户详情信息：",res); 
             res.data.listData[0].lastLoginTime=util.getLocalTime(res.data.listData[0].lastLoginTime);
            res.data.listData[0].firstLoginTime=util.getLocalTime(res.data.listData[0].firstLoginTime);

             that.setData({ 
                person:res.data.listData[0] 
               }); 
            
          },
          fail: function(res) {
            // fail
          },
          complete: function(res) {
            // complete
          }
        })


},
   


  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
     

  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  },
   
  //查询参加的活动数量
  getActiveCount: function () {
    var that=this;
    console.log("oid111:" + that.data.openid);
    wx.request({
      url: 'https://localhost:3000/active/myActiveCount',
      data: { openid: that.data.openid },
      method: 'get',
      success: function (res) {
        console.log("count:" + res.data);
        that.setData({
          myActiveCount: res.data
        })
      },
      fail: function (res) {
        console.log("showfriend fail");
      },
      complete: function (res) {
        // complete
      
      }
    })
  },

  addComment:function(){
    wx.navigateTo({
      url: '../common/editValue?value=' + this.data.userInfo.openId + '&type=text&desc=请留言'
    })
  }
})