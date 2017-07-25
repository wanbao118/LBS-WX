//index.js
var app = getApp();
var bmap = require('../libs/bmap-wx.js'); 
const ak= 'bfwtSbwjqSnIPWGIjKssrQdsPZn0Q87g';
var wxMarkerData = []; 
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
    markers: [], 
    latitude: '', 
    longitude: '', 
      
      
    userInfo: {},
    cityName:"",
    grids: [0, 1, 2, 3, 4, 5, 6, 7, 8]
  }, 
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  onLoad: function(options) {
     var that = this; 


 app.userInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })

//调用百度
 that.getCity();

 
       
  },
  showInput: function () {
        this.setData({
            inputShowed: true
        });
    },
    hideInput: function () {
        this.setData({
            inputVal: "",
            inputShowed: false
        });
    },
    clearInput: function () {
        this.setData({
            inputVal: ""
        });
    },
    inputTyping: function (e) {
        this.setData({
            inputVal: e.detail.value
        });
    }, 
 
    //获取城市信息
    getCity:function(){
        var that = this; 
          // 新建百度地图对象 
        var BMap = new bmap.BMapWX({ 
            ak: ak 
        }); 
        var fail = function(data) { 
            console.log(data) 
        }; 
        var success = function(data) { 
            wxMarkerData = data.wxMarkerData; 
           
             that.setData({ 
                 markers: wxMarkerData 
             }); 
             that.setData({ 
                 latitude: wxMarkerData[0].latitude 
             }); 
             that.setData({ 
                 longitude: wxMarkerData[0].longitude 
             }); 
            that.setData({
                cityName:wxMarkerData[0].city
            });
        } 
         // 发起regeocoding检索请求 
        BMap.regeocoding({ 
            fail: fail, 
            success: success, 
          //  iconPath: '../../img/marker_red.png', 
          //  iconTapPath: '../../img/marker_red.png' 
        }); 
       
    },
})
