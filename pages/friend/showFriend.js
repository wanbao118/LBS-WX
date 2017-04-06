// pages/friend/showFriend.js
var app = getApp()
Page({
  data:{
   person:{userName:"我是美女",level:45,creditLevel:30,sex:0,money:3343,favType1:"羽球",favType2:"跑步",favType3:"足球",createDate:"2017/03/21",phoneCertify:1,idcardCertify:1,partner:1,teacher:1,shop:1,intro:"喜欢我就约我吧"},
   userInfo: {},
   grids: [0, 1, 2, 3, 4, 5],
   discuss:[
        {id:"1",title:"大的负担",time:"2017/03/04 13:00",level:5,content:"大家好，很高兴认识大家"},
        {id:"2",title:"我发生地方",time:"2017/02/04 21:00",level:4,content:"大；立刻接受对方"},
        {id:"3",title:"斯蒂芬森",time:"2017/03/04 13:50",level:5,content:"大家好，很高兴认识大家"},
        {id:"4",title:"文无定法",time:"2017/01/04 08:00",level:4,content:"大家好，很看见对方的"},
        {id:"5",title:"让他热认同",time:"2017/03/04 13:00",level:7,content:"大家好，很高兴认识大家"},
        {id:"6",title:"大噶规范",time:"2017/03/04 13:00",level:6,content:"的；为呃呃呃"},
        {id:"7",title:"a发生过",time:"2017/03/04 13:00",level:9,content:"呃呃呃阿斯顿"}
      ],

  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    // 暂时取测试数据
  //   this.setData({
     
  //    person:options,
      
  //  });
   //调用应用实例的方法获取全局数据
    app.getUserInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
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
  btnPartner:function(e){
    wx.navigateTo({
          url: 'partner'
        })
  },
  btnTeacher:function(e){
    wx.navigateTo({
          url: 'teacher'
        })
  },
  btnShop:function(e){
    wx.navigateTo({
          url: 'shop'
        })
  }
})