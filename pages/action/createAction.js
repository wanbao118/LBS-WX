//获取应用实例
var app = getApp();
var util = require('../../utils/util.js');
// pages/action/createAction.js
//var openId='';
Page({
  data:{
    subject:"",
    date:"",
    time:"",
    dateDisabled:false,
    userInfo: {},
    actTypes: ["约战","陪练","教学","看比赛"],
    actTypeIndex: 0,
    sprTypes: ["跑步","足球","篮球","羽毛球","网球"],
    sprTypeIndex: 0 ,
    feeTypes: ["AA","我付","比赛决定"],
    feeTypeIndex: 0,
    actTerms: ["每天","每周内","每周末","周一三五","周二四六"],
    actTermIndex: 0,
    acttermDisabled: true,
    message:''

  },
  
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    console.log('createAction onLoad');
    var that = this
    //调用应用实例的方法获取全局数据
      console.log("userInfo:"+app.globalData.userInfo);
      that.setData({
        userInfo:app.globalData.userInfo
      }) 
     // openId=app.globalData.openId;
     // console.log("openId:"+openId);
    that.setDateAndTime()
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

  setDateAndTime:function (){
    var that = this
    //设置当前日期和时间
    that.setData({
      date: util.formatOnlyDate(new Date,'-'),
      time: util.formatHourAndMinu(new Date,':')
    });
    console.log('date:'+that.data.date+",time:"+that.data.time)
  },

  switchChange: function (e){
    if(e.currentTarget.id=="isSetTime"){
      if(e.detail.value==true){
        this.setData({
          acttermDisabled:false,
          dateDisabled:true
        })
      }else if(e.detail.value==false){
        this.setData({
          acttermDisabled:true,
          dateDisabled:false
        })
      }
    };
  },

  bindChange: function (e) {
    if(e.currentTarget.id=="subject")
    {
    this.setData({
      index: e.detail.value
    })
    };
    if(e.currentTarget.id=="acttype")
    {
      this.setData({
      actTypeIndex: e.detail.value
    })
    };
    if(e.currentTarget.id=="sprtype")
    {
      this.setData({
      sprTypeIndex: e.detail.value
    })
    };
    if(e.currentTarget.id=="feetype")
    {
      this.setData({
      feeTypeIndex: e.detail.value
    })
    };
    if(e.currentTarget.id=="actterm")
    {
      this.setData({
      actTermIndex: e.detail.value
    })
    };
    if(e.currentTarget.id=="date")
    {
      this.setData({
      date: e.detail.value
    })
    };
    if(e.currentTarget.id=="time")
    {
      this.setData({
      time: e.detail.value
    })
    }
    ;

    try {
    wx.setStorageSync(e.currentTarget.id,e.detail.value)
} catch (e) {  
  wx.showToast({
  title: '失败',
  icon: 'success',
  duration: 2000
})  
}    

    },
  bindDateChange: function (e) {
        this.setData({
            date: e.detail.value
        })
    },
    bindTimeChange: function (e) {
        this.setData({
            time: e.detail.value
        })
    },
     formSubmit: function(e) {
    var that=this;
    // that.setData({
    //   submitData:e.detail.value
    // })
    var datas=e.detail.value;
    that.sendData(datas);
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
  },


   submit: function (e) {
     this.sendData(e);
      wx.switchTab({
        url: 'actionList',
        complete:function(res){
              console.log(res)
          }
      })
 
    },

    //发送数据请求
    sendData: function(datas){
        console.log(datas);
        wx.request({
            // url: 'http://59.110.165.245/Lbs_back/servlet/PositionInsert', //位置新增接口地址
            url: 'http://localhost:9099/Lbs_back/servlet/PositionInsert' ,
            data: {
                cActInfo: datas ,
               // openId:openId 后续添加ID标记发起用户
            },
            header: {
                'content-type': 'application/json'
            },
            success: function(res) {
                console.log(res.data)
            }
        })
    },
})