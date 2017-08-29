// pages/club/clubCreate.js

Page({
  data: {
    types: ["足球", "篮球", "羽毛球"],
    typeIndex: 0,
    club: {
      name: "",
      type: "",
      desc: "",
      memberList: []
    }

  },
  // bindChange: function (e) {
  //   if (e.currentTarget.id == "clubName") {
  //     this.data.club.name = e.detail.value
  //   };
  // },
  // bindTextAreaBlur: function (e) {
  //   if (e.currentTarget.id == "clubDesc") {
  //     this.data.club.desc = e.detail.value
  //   };
  // },
  bindTypeChange: function (e) {
    this.setData({
      typeIndex: e.detail.value
    })
    this.data.club.type = this.data.types[e.detail.value]
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
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

  createClub: function (e) {

    console.log("createClub:", e);
   
  },

})