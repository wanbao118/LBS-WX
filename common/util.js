function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()


  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatOnlyDate(date,format) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()

  return [year, month, day].map(formatNumber).join(format) 
}

function formatOnlyTime(date,format) {
  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()

  return [hour, minute, second].map(formatNumber).join(format)
}

function formatHourAndMinu(date,format) {
  var hour = date.getHours()
  var minute = date.getMinutes()

  return [hour, minute].map(formatNumber).join(format)
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

// module.exports = {
//   formatTime: formatTime
// }

module.exports.formatTime = formatTime;
module.exports.formatOnlyDate = formatOnlyDate;
module.exports.formatOnlyTime = formatOnlyTime;
module.exports.formatHourAndMinu = formatHourAndMinu;
module.exports.formatNumber = formatNumber;