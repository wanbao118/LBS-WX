// pages/action/action.js
var sliderWidth = 96; // 需要设置slider的宽度，用于计算中间位置
Page({
  data:{
      tabs: [ "热门", "最新"],
      activeIndex: "0",
      sliderOffset: 0,
      sliderLeft: 0,
      item1:"",
      actObj:[]
      },

  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
     var that = this;
        wx.getSystemInfo({
            success: function(res) {
                that.setData({
                    sliderLeft: (res.windowWidth / that.data.tabs.length - sliderWidth) / 2
                });

                 //查询活动清单
                that.actionList();
            }
        });   
  },

  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示,从后台j进入前台时刷新页面
    this.actionList();
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

  actionList:function(){
    var that = this;
    wx.request({
       url: 'https://localhost:3000/active/', 
       data: {
          activeIndex:that.data.activeIndex
        },
        header: {
          'content-type': 'application/json'
         },
         method: 'GET',
        success: function(res) {
          let actList = res.data;
          // console.log("aa:"+actList[0])
           if(actList.length > 0){
               that.setData({ 
                actObj: actList 
               });  
           }
         },
         fail:function(res){
           console.log("查询活动失败");
         }
       })
  }
  })