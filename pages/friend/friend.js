// pages/friend/friend.js
var sliderWidth = 96; // 需要设置slider的宽度，用于计算中间位置
// 引用百度地图微信小程序JSAPI模块 
var bmap = require('../../libs/bmap-wx.js'); 
var wxMarkerData = []; 
const ak= 'bfwtSbwjqSnIPWGIjKssrQdsPZn0Q87g'
var app = getApp()
var hasMore=false;
var hidden = false;
var pageNum=0;
var len = 0;
var sports= ['羽毛球', '篮球', '网球'];
var   location='';
var query='';
Page({
  data:{
    tabs: ["找熊友", "熊友圈", "场馆"],
      activeIndex: "0",
      sliderOffset: 0,
      sliderLeft: 0,
      cityName:"",
      sportsArray: ['羽毛球', '篮球', '网球'],
      distanceArray: ['5km内', '15km内', '30km内'],
      choiceSport:'',
      index1:0,
      index2:0,
      scrollTop : 0,
    scrollHeight:0,
    selectFlag:false,
    typeArray: ['羽毛球', '篮球', '网球'],
      allArray: ['距离', '热度', '日期'],
      //测试数据，等后台服务准备好后替换
      friendList:[
        {id:"1",title:"大的负担",type:"羽毛球",level:5,intro:"大家好，很高兴认识大家"},
        {id:"2",title:"我发生地方",type:"篮球",level:4,intro:"大家好，很高兴认识大家"},
        {id:"3",title:"斯蒂芬森",type:"乒乓球",level:5,intro:"大家好，很高兴认识大家"},
        {id:"4",title:"文无定法",type:"羽毛球",level:4,intro:"大家好，很高兴认识大家"},
        {id:"5",title:"让他热认同",type:"健身",level:7,intro:"大家好，很高兴认识大家"},
        {id:"6",title:"大噶规范",type:"羽毛球",level:6,intro:"大家好，很高兴认识大家"},
        {id:"7",title:"a发生过",type:"足球",level:9,intro:"大家好，很高兴认识大家"}
      ],
      cycleList:[
        {id:"1",title:"小峰朋友圈",type:"羽毛球",level:5,sum:34,intro:"大家好，欢迎加入！"},
        {id:"2",title:"天下无敌",type:"篮球",level:4,sum:12,intro:"大家好，很高兴认识大家"},
        {id:"3",title:"最爱张继科",type:"乒乓球",level:5,sum:24,intro:"大家好，很高兴认识大家"},
        {id:"4",title:"林丹后援团",type:"羽毛球",level:4,sum:89,intro:"大家好，欢迎加入！"},
        {id:"5",title:"ceshi4",type:"健身",level:7,sum:34,intro:"大家好，很高兴认识大家"},
        {id:"6",title:"李宗伟的院子",type:"羽毛球",level:6,sum:3,intro:"大家好，欢迎加入！"},
        {id:"7",title:"国家队忠实粉丝",type:"足球",level:9,sum:9,intro:"大家好，欢迎加入！"}
      ],
       
      clubList:[
        {id:"1",title:"ceshi",type:"羽毛球",level:5,intro:"大家好，很高兴认识大家"},
        {id:"2",title:"ceshi1",type:"篮球",level:4,intro:"大家好，很高兴认识大家"},
        {id:"3",title:"ceshi2",type:"乒乓球",level:5,intro:"大家好，很高兴认识大家"},
        {id:"4",title:"ceshi3",type:"羽毛球",level:4,intro:"大家好，很高兴认识大家"},
        {id:"5",title:"ceshi4",type:"健身",level:7,intro:"大家好，很高兴认识大家"},
        {id:"6",title:"ceshi5",type:"羽毛球",level:6,intro:"大家好，很高兴认识大家"},
        {id:"7",title:"ceshi6",type:"足球",level:9,intro:"大家好，很高兴认识大家"}
      ],
      name:"nihao"
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    var that = this;
        wx.getSystemInfo({
            success: function(res) {
                that.setData({
                    sliderLeft: (res.windowWidth / that.data.tabs.length - sliderWidth) / 2
                });
                that.setData({
                  scrollHeight:res.windowHeight
              });
            }
        });
    

    
      
    //调用百度
     that.getCity();
    that.loadBaiDu();
  },
  bindSprotChange:function(e){
      var that=this;
      console.log("aaas:"+sports[e.detail.value]);
      this.setData({
      index1: e.detail.value
    })
    query=sports[e.detail.value];
    that.loadBaiDu(0,query);
  },

  //调用百度api获取数据
    loadBaiDu:function(pageNum,query){
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
            //    that.sendData(wxMarkerData);
			console.log(wxMarkerData[0]);
            len = wxMarkerData.length;
            if(len===0){
                console.log("没有了");
                return ;
            };
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

            //发送数据到后台台
           // that.sendData(wxMarkerData);
           
        } 

       // 发起POI检索请求 
       //console.log(location);
        BMap.search({ 
            "query": query||'羽毛球', 
          //  "location": location||'',
            fail: fail, 
            success: success, 
            // 此处需要在相应路径放置图片文件 
            iconPath: '../../img/marker_red.png', 
            // 此处需要在相应路径放置图片文件 
            iconTapPath: '../../img/marker_red.png' ,
             page_num:pageNum
        }); 

    },

    //发送数据请求
    sendData: function(datas){
        console.log(datas);
        wx.request({
            url: 'http://59.110.165.245/Lbs_back/servlet/PositionInsert', //位置新增接口地址
            data: {
                positions: datas 
            },
            header: {
                'content-type': 'application/json'
            },
            success: function(res) {
                console.log(res.data)
            }
        })
    },
 
//上拉获取更多
    loadMore:function(){
        var that= this;
        that.setData({
            hasMore:true
        });
        pageNum=pageNum+1;
      this.loadBaiDu(pageNum,query);
      that.setData({
            hasMore:false
        });
         console.log("down"+pageNum);
    },
//下拉刷新
    doFresh:function(){

        (pageNum==0)?pageNum=0:pageNum=pageNum-1;
      this.loadBaiDu(pageNum,query);
      console.log("up"+pageNum);
     
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
            // that.setData({ 
            //     markers: wxMarkerData 
            // }); 
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


    selectMore: function(e) {
       
       if(this.data.selectFlag==false)
       {this.setData({
            selectFlag:true
                    });
    }
    else{
        this.setData({
            selectFlag:false
                    });
    }
    },
    selectOk: function(e) {
       
       this.setData({
            selectFlag:false
                    });
    
     
    }
})
