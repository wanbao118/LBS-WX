// pages/club/clubDetail.js
// pages/friend/showCycle.js
Page({
  data: {
    club: { level:20, creditLevel:34, name: "大秦之都足球队", money: 1000, type: "足球", createDate:"20170818"},
    clubId: "",
    grids: [0, 1, 2, 3, 4, 5]
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    this.setData({
      clubId: options
    });
    this.getClub();
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },
  clubCreate: function () {
    wx.navigateTo({
      url: 'clubCreate'
    })
  },

  clubManage:function(){
    wx.navigateTo({
      url: 'clubManage',
    })
  },
  //获取熊友圈详情
  getClub: function () {
    var that = this;
    wx.request({
      url: 'http://127.0.0.1:3000/club',
      data: {
        _id: this.data.clubId.id

      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      // header: {}, // 设置请求的 header
      success: function (res) {
        // success
        var result = res.data;
        console.log("+++++++++++++++++++++++++++++res:" + res.data);
        that.setData({
          club: result[0]
        });
      },
      fail: function (res) {
        // fail

      },
      complete: function (res) {
        // complete

      }
    })
  }
})