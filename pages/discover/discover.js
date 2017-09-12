// pages/friend/friend.js
var sliderWidth = 96; // 需要设置slider的宽度，用于计算中间位置
// 引用百度地图微信小程序JSAPI模块 
var bmap = require('../../libs/bmap-wx.js');
var util = require('../../common/util.js');
var wxMarkerData = [];
const ak = 'bfwtSbwjqSnIPWGIjKssrQdsPZn0Q87g'
var app = getApp()
var hasMore = false;
var hidden = false;
var pageNum = 0;
var len = 0;
var sports = ['羽毛球', '篮球', '网球'];
var location = '';
var query = '';
// 滑动条选项
var order = ['h1','h2','h3','h4','h5'];
var rst=[];   //与好友的距离计算结果

Page({
  data: {
    markers: [],
    latitude: '',
    longitude: '',
    placeData: {},
    tabs: ["找熊友", "场馆"],
    activeIndex: "0",
    sliderOffset: 0,
    sliderLeft: 0,
    cityName: "",
    sportsArray: ['羽毛球', '篮球', '网球'],
    distanceArray: ['5km内', '15km内', '30km内'],
    choiceSport: '',
    index1: 0,
    index2: 0,
    scrollTop: 0,
    scrollHeight: 0,
    selectFlag: false,
    // 页面配置  
    winWidth: 0,
    winHeight: 0, 

    // tab切换 
    currentTab: 0,

     
    //测试数据，等后台服务准备好后替换
    friendList: [

    ],
    

  },

  toNearby:function(){
    wx.navigateTo({
      url: 'map'
    })
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        }); 

      }
    });

    // //获取熊友初始数据
    // that.getUsers();
    
    //调用百度
    //that.getCity();
    that.getLocationInfo();
    that.loadBaiDu();
  },
  // 滑动切换tab 
  bindChange: function (e) {
    var that = this;
    that.setData({ currentTab: e.detail.current });
  },
  // 点击tab切换 
  swichNavi: function (e) {
    var that = this;
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },


  //调用百度api获取数据
  loadBaiDu: function (pageNum, query) {
    var that = this;
    // 新建百度地图对象 
    var BMap = new bmap.BMapWX({ ak: ak });
    var fail = function (data) {
      console.log("loadBaiDu:"+data)
    };
    var success = function (data) {

      wxMarkerData = data.wxMarkerData;
      //    that.sendData(wxMarkerData);
      console.log(wxMarkerData);
      len = wxMarkerData.length;

      if (len === 0) {
        console.log("没有了");
        return;
      };

      //场馆入库
      wx.request({
          url: app.gData.iServerUrl +   '/bearsport/service/venues/venuesAdd',
          data:JSON.stringify(wxMarkerData),
          header: {
            'Content-Type': 'application/json'
          },
          method: 'POST',
          success: function (res) {
            // success 
            console.log(res);
          },
          fail: function () {
            // fail 
          },
          complete: function () {
            // complete 
          }
        })
      //  app.globalDate.globalMarkers=wxMarkerData;
      that.setData({
        markers: wxMarkerData
      });
      that.setData({
        latitude: wxMarkerData[0].latitude
      });
      that.setData({
        longitude: wxMarkerData[0].longitude
      });


    }

    // 发起POI检索请求 
    //console.log(location);
    BMap.search({
      //"query": query || '羽毛球' || '桌球'|| '蓝球',
      "location": '34.329605,34.329605',
      fail: fail,
      success: success,
      // 此处需要在相应路径放置图片文件 
      iconPath: '../../img/marker_red.png',
      // 此处需要在相应路径放置图片文件 
      iconTapPath: '../../img/marker_red.png',
      page_num: pageNum
    });

  },



  //上拉获取更多
  loadMore: function () {
    var that = this;
    that.setData({
      hasMore: true
    });
    pageNum = pageNum + 1;
    this.loadBaiDu(pageNum, query);
    that.setData({
      hasMore: false
    });
    console.log("down" + pageNum);
  },
  //下拉刷新
  doFresh: function () {

    (pageNum == 0) ? pageNum = 0 : pageNum = pageNum - 1;
    this.loadBaiDu(pageNum, query);
    console.log("up" + pageNum);

  },
  //获取城市信息
  getCity: function () {

      this.setData({
        cityName: app.gData.cityName
      })

  },

  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
    //获取熊友初始数据
    this.getUsers();
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },
  tabClick: function (e) {
    this.setData({
      sliderOffset: e.currentTarget.offsetLeft,
      activeIndex: e.currentTarget.id
    });
  },


  selectMore: function (e) {

    if (this.data.selectFlag == false) {
      this.setData({
        selectFlag: true
      });
    }
    else {
      this.setData({
        selectFlag: false
      });
    }
  },
  selectOk: function (e) {

    this.setData({
      selectFlag: false
    });


  },

  //获取熊友初始数据
  getUsers: function (e) {

    var that = this;
    var iData ={};
    iData.operationCode = "UF"
    wx.request({
      url: app.gData.iServerUrl + '/bearsport/service/user/userMaintain',      
      data: iData,
      //method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      method: 'POST',
      // header: {}, // 设置请求的 header
      header: { 'content-type': 'application/json' },
      success: function (res) {
        console.log("获取熊友列表信息：", res.data);

        for (var i = 0; i < res.data.listData.length; i++) {
          res.data.listData[i].lastLoginTime =  util.formatTimestamp(res.data.listData[i].lastLoginTime);
        }

        that.setData({
          friendList: res.data.listData
        });

  //      that.setData({
 //         formatTimestamp: util.formatTimestamp()
 //       });

       console.log("friend",that.data.friendList);
      },
      fail: function (res) {
        // fail
      },
      complete: function (res) {
        // complete
      }
    })

    //计算自己与好友间的距离
    that.getdistance()
  },

  getLocationInfo: function () {
    var that = this;
    wx.getLocation({
      type: 'wgs84',
      success: function (res) {
        var latitude = res.latitude
        var longitude = res.longitude
        var speed = res.speed
        var accuracy = res.accuracy
        console.log("latitue:" + latitude);
        console.log("longitude:" + longitude);
        console.log("speed:" + speed);
        console.log("accuracy:" + accuracy);

        wx.request({
          url: 'https://api.map.baidu.com/geocoder/v2/?ak=bfwtSbwjqSnIPWGIjKssrQdsPZn0Q87g&location=' + latitude + ',' + longitude + '&output=json',
          data: {},
          header: {
            'Content-Type': 'application/json'
          },
          success: function (res) {
            // success 
            console.log(res);
            var city = res.data.result.addressComponent.city;
            that.setData({ cityName: city });
          },
          fail: function () {
            // fail 
          },
          complete: function () {
            // complete 
          }


        })
      }
    })
  },
  //ܱ根据经纬度计算距离
  getdistance: function () {

    wx.request({
      url: 'http://api.map.baidu.com/routematrix/v2/driving?',
      data: {
        output: "json",
        origins: "40.45,116.34",    //坐标格式为：lat<纬度>,lng<经度>|lat<纬度>,lng<经度>  多个用|分开,最多传50个点，且起终点乘积不超过50
        destinations: "40.34,116.45|40.35,116.46", // 同上
        ak: "x01RzuY9Guop6j45QvMhQGO7YqTlUp1i",
      },
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        // success 
        console.log("ret", res.data);
        var status = res.data.status;
        rst = res.data.result;
        console.log("dddd", rst[0].distance.text);
      },
      fail: function () { },
      complete: function () { }
    })
  },


  
})
