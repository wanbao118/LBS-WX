// pages/maps/detail.js
var app = getApp();
Page({
  data:{
    title:'',
    address:'',
    telephone:'',
    price:0,
  },
  onLoad:function(options){
    var that = this; 
    // 页面初始化 options为页面跳转所带来的参数
     var index = options.options;
   that.setData({
     title:options.title,
     address:options.address,
     telephone:options.telephone,
     price:options.price,
   });
   
 
  console.log(options);
    
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
  }
})