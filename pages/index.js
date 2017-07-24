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
    // makertap: function(e) { 
    //     var that = this; 
    //     var id = e.markerId; 
    //     that.showSearchInfo(wxMarkerData, id); 
    //     that.changeMarkerColor(wxMarkerData, id); 
    // }, 

  onLoad: function(options) {
     var that = this; 


 app.userInfo(function(userInfo){
      //更新数据
      that.setData({
        userInfo:userInfo
      })
    })


     wx.request({
        url: 'http://59.110.165.245/', //仅为示例，并非真实的接口地址
  
        header: {
           'content-type': 'application/json'
         },
        success: function(res) {
         // console.log(res.data);
          that.setData({ arrayList: res.data.sites });
         }
       });

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
 
    // loadMore:function(){
    //     var that= this;
    //     that.setData({
    //         hasMore:true
    //     });
    //     pageNum=pageNum+1;
    //   this.loadBaiDu(pageNum);
    //   that.setData({
    //         hasMore:false
    //     });
    //      console.log("down"+pageNum);
    // },
    // doFresh:function(){

    //     (pageNum==0)?pageNum=0:pageNum=pageNum-1;
    //   this.loadBaiDu(pageNum);
    //   console.log("up"+pageNum);
     
    // },
    //调用百度api获取数据
  //  loadBaiDu:function(pageNum){
    //     var that = this; 
    //      // 新建百度地图对象 
    //     var BMap = new bmap.BMapWX({ 
    //         ak: ak
    //     }); 
    //     var fail = function(data) { 
    //         console.log(data) 
    //     }; 
    //     var success = function(data) { 
            
    //         wxMarkerData = data.wxMarkerData; 
	// 		console.log(wxMarkerData.length);
    //         len = wxMarkerData.length;
    //         if(len===0){
    //             console.log("没有了");
    //             return ;
    //         };
    //         app.globalDate.globalMarkers=wxMarkerData;
    //         that.setData({ 
    //             markers: wxMarkerData 
    //         }); 
    //         that.setData({ 
    //             latitude: wxMarkerData[0].latitude 
    //         }); 
    //         that.setData({ 
    //             longitude: wxMarkerData[0].longitude 
    //         }); 
           
    //     } 
    //    // 发起POI检索请求 
    //     BMap.search({ 
    //         "query": '羽毛球', 
    //         fail: fail, 
    //         success: success, 
    //         // 此处需要在相应路径放置图片文件 
    //         iconPath: '../../img/marker_red.png', 
    //         // 此处需要在相应路径放置图片文件 
    //         iconTapPath: '../../img/marker_red.png' ,
    //          page_num:pageNum
    //     }); 

   // },
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
