// pages/friend/createCycle.js
Page({
  data:{
     types: ["足球", "篮球", "羽毛球"],
        typeIndex: 0,
    
  },

bindTypeChange: function(e) {
        console.log('picker account 发生选择改变，携带值为', e.detail.value);

        this.setData({
            typeIndex: e.detail.value
        })
    },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
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
  
})