// pages/friend/showCycle.js
Page({
  data:{
    club:{id:34,clubName:"晓峰朋友圈",level:32,creditLevel:43,money:5432,type:"羽球",createDate:"2015/09/12"},
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
  createCycle:function(){
    wx.navigateTo({
          url: 'createCycle'
        })
  }
})