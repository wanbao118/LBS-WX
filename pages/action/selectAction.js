//获取应用实例
var app = getApp()
var util = require('../../utils/util.js')
// pages/action/selectAction.js
Page({
  data:{
    actId: "",
    actTitle:"",
    sprType:"",
    actContent:"",
    userInfo: {},
    actType: "",
    feeType: "",
    isSetTime: "",
    actTerm: "",
    actDate: "",
    actTime: "",
    isReserve: "",
    areaInfo: "",
    joinPeople: "",
    pubCredit: "",
    leaveMessage: "",
    joinners:[
      {"joinner":"张三","feeManage":15.0,"joninerLeave":"很有意义"},
      {"joinner":"李四","feeManage":15.0,"joninerLeave":"以后多组织"}
    ]
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    console.log('selectAction onLoad');
    var that = this
    var actId = options.actId;
 
  console.log("actId:"+actId);
    that.selectActive(actId)

  },

  selectActive: function (actId) {
       wx.request({
            url: 'http://59.110.165.245/Lbs_back/actives/selectActive', //活动明细查询接口地址
            data: {
                actId: actId 
            },
            header: {
                'content-type': 'application/json'
            },
            success: function(res) {
                console.log(res.data)
                this.setData({
                    actTitle:res.data.actTitle,
                    sprType:res.data.sprType,
                    actContent:res.data.actContent,
                    actType: res.data.actType,
                    feeType: res.data.feeType,
                    isSetTime: res.data.isSetTime,
                    actTerm: res.data.actTerm,
                    actDate: res.data.actDate,
                    actTime: res.data.actTime,
                    isReserve: res.data.isReserve,
                    areaInfo: res.data.areaInfo,
                    joinPeople: res.data.joinPeople,
                    pubCredit: res.data.pubCredit,
                    leaveMessage: res.data.leaveMessage,
                    joinners:res.data.joinners
               });
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
})