// pages/action/action.js
var sliderWidth = 96; // 需要设置slider的宽度，用于计算中间位置
Page({
  data:{
      tabs: ["附近", "热门", "最新"],
      activeIndex: "0",
      sliderOffset: 0,
      sliderLeft: 0,
      item1:"",
      actObj:[{"actId":"1234","actTitle":"来找我","sprtype":"篮球"},{"actId":"2234","actTitle":"我是谁","sprtype":"羽毛球"}] //测试数据
      },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
     var that = this;
        wx.getSystemInfo({
            success: function(res) {
                that.setData({
                    sliderLeft: (res.windowWidth / that.data.tabs.length - sliderWidth) / 2
                });
            }
        }); 

    try {
      var value = wx.getStorageSync('date')
      if (value) {
          // Do something with return value
          wx.showToast({
          title:res.data,
          icon: 'success',
          duration: 2000
        });
      }
    } catch (e) {
        // Do something when catch error
        wx.showToast({
        title:res.data,
        icon: 'success',
        duration: 2000
      });
    }
        
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
  tabClick: function (e) {
      this.setData({
          sliderOffset: e.currentTarget.offsetLeft,
          activeIndex: e.currentTarget.id
      });
  },
  primary: function (e) {
        wx.navigateTo({
          url: 'createAction'
        })
      },

  selectAction:function(){
    var that = this;
    wx.request({
       url: 'http://localhost:9099/Lbs_back/servlet/SelectAction', 
       data: {
          activeIndex:activeIndex
        },
        header: {
          'content-type': 'application/json'
         },
         method: 'GET',
        success: function(res) {
           console.log(res.data)
           if(res["status"] === 0){
             if(res["count">0]){
               that.setData({ 
                actObj: res["result"] 
               }); 
             }else{
               //显示无数据
             }
             
           }
         },
         fail:function(res){
           console.log("查询活动失败");
         }
       })
  }
  })