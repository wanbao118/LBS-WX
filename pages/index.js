//index.js
var common = require('../common/common.js')
var app = getApp()
//var hasMore=false;
//var pageNum=0;
//var len = 0;
Page({
  data: {
    imgUrls: [
      'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,

    inputShowed: false,
    inputVal: "",
    arrayList: [], 
    userInfo: {},
    cityName:"",
    grids: [0, 1, 2, 3, 4, 5, 6, 7, 8]
  }, 
  
  onLoad: function(options) {
    console.log("cityName:" + app.gData.cityName);
     var that = this; 
     that.setData({
       cityName: app.gData.cityName
     }); 
      
     
 app.userInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })



 
       
  },
  
})
