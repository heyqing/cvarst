<template>
  <view>
    <view>
      <img :src="photoPath" />
    </view>
    <view class="charts-box">
      <van-dialog id="van-dialog" />
      <qiun-data-charts type="column" :opts="optsColumn" :chartData="chartDataColumn" />
      <qiun-data-charts type="radar" :opts="optsRadar" :chartData="chartDataRadar" />
      <qiun-data-charts type="mount" :opts="optsMount" :chartData="chartDataMount" />
      <qiun-data-charts type="ring" :opts="optsRing" :chartData="chartDataRing" />
      <qiun-data-charts type="line" :opts="optsLine" :chartData="chartDataLine" />
      <qiun-data-charts type="scatter" :opts="optsScatter" :chartData="chartDataScatter" />
    </view>
  </view>
</template>

<script>
  import Dialog from '../../wxcomponents/vant/dialog/dialog';
  export default {
    data() {
      return {
        photoPath: '../../static/about/HeYiQing_LOGO_IT_01.jpg',
        chartDataColumn: {},
        chartDataMount: {},
        chartDataLine: {},
        chartDataRadar: {},
        chartDataRing: {},
        chartDataScatter: {},
        optsColumn: {
          color: ["#FAC858", "#EE6666", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4", "#ea7ccc"],
          padding: [15, 15, 0, 5],
          enableScroll: false,
          legend: {},
          xAxis: {
            disableGrid: true
          },
          yAxis: {
            data: [{
              min: 0
            }]
          },
          extra: {
            column: {
              type: "group",
              width: 30,
              activeBgColor: "#000000",
              activeBgOpacity: 0.08,
              linearType: "custom",
              seriesGap: 5,
              linearOpacity: 0.5,
              barBorderCircle: true,
              customColor: [
                "#FA7D8D",
                "#EB88E2"
              ]
            }
          }
        },
        optsMount: {
          color: ["#1890FF", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4", "#ea7ccc"],
          padding: [15, 15, 0, 5],
          enableScroll: false,
          legend: {},
          xAxis: {
            disableGrid: true
          },
          yAxis: {
            data: [{
              min: -100,
              max: 100
            }],
            splitNumber: 4
          },
          extra: {
            mount: {
              type: "mount",
              widthRatio: 1.5,
              borderWidth: 0,
              linearType: "custom"
            }
          }
        },
        optsLine: {
          color: ["#1890FF", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4", "#ea7ccc"],
          padding: [15, 10, 0, 15],
          enableScroll: false,
          legend: {},
          xAxis: {
            disableGrid: true
          },
          yAxis: {
            gridType: "dash",
            dashLength: 2
          },
          extra: {
            line: {
              type: "curve",
              width: 2,
              activeType: "hollow"
            }
          }
        },
        optsRadar: {
          color: ["#1890FF", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4", "#ea7ccc"],
          padding: [5, 5, 5, 5],
          dataLabel: false,
          dataPointShape: false,
          enableScroll: false,
          legend: {
            show: true,
            position: "right",
            lineHeight: 25
          },
          extra: {
            radar: {
              gridType: "circle",
              gridColor: "#CCCCCC",
              gridCount: 3,
              opacity: 0.2,
              max: 200,
              labelShow: true,
              axisLabel: true,
              gridEval: 2,
              border: true
            }
          }
        },
        optsRing: {
          rotate: false,
          rotateLock: false,
          color: ["#1890FF", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4", "#ea7ccc"],
          padding: [5, 5, 5, 5],
          dataLabel: true,
          enableScroll: false,
          legend: {
            show: true,
            position: "right",
            lineHeight: 25
          },
          title: {
            name: "收益率",
            fontSize: 15,
            color: "#666666"
          },
          subtitle: {
            name: "70%",
            fontSize: 25,
            color: "#7cb5ec"
          },
          extra: {
            ring: {
              ringWidth: 60,
              activeOpacity: 0.5,
              activeRadius: 10,
              offsetAngle: 0,
              labelWidth: 15,
              border: true,
              borderWidth: 3,
              borderColor: "#FFFFFF",
              linearType: "custom"
            }
          }
        },
        optsScatter: {
          color: ["#1890FF", "#91CB74", "#FAC858", "#EE6666", "#73C0DE", "#3CA272", "#FC8452", "#9A60B4", "#ea7ccc"],
          padding: [15, 15, 0, 15],
          dataLabel: false,
          enableScroll: false,
          legend: {},
          xAxis: {
            disableGrid: false,
            gridType: "dash",
            splitNumber: 5,
            boundaryGap: "justify",
            min: 0
          },
          yAxis: {
            disableGrid: false,
            gridType: "dash"
          },
          extra: {
            scatter: {}
          }
        }





      };
    },
    onLoad(op) {
      this.dialog();
      this.getPhotoPath(op.photoPath);
    },
    methods: {
      dialog() {
        Dialog.alert({
          title: '声明',
          message: '本功能尚未开发完成，但已实现数据展示效果。\n此功能重要用于渲染后端反馈的数据，但主要功能（即图片的识别、分析等等技术问题尚未解决，后端无法响应数据。\n注：展示数据皆为随机数据并无其他含义）',
        }).then(() => {
          uni.$showMsg('感谢您的理解^_^')
          this.getServerDataColumn();
          this.getServerDataMount();
          this.getServerDataLine();
          this.getServerDataRadar();
          this.getServerDataRing();
          this.getServerDataScatter()
        });
      },
      getPhotoPath(path) {
        if (path !== null) this.photoPath = path
      },
      getServerDataColumn() {
        //模拟从服务器获取数据时的延时
        setTimeout(() => {
          //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
          let res = {
            categories: ["2018", "2019", "2020", "2021", "2022", "2023"],
            series: [{
                name: "期望值",
                data: [35, 36, 31, 33, 13, 34]
              },
              {
                name: "实际值",
                data: [18, 27, 21, 24, 6, 28]
              }
            ]
          };
          this.chartDataColumn = JSON.parse(JSON.stringify(res));
        }, 500);
      },
      getServerDataMount() {
        //模拟从服务器获取数据时的延时
        setTimeout(() => {
          //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
          let res = {
            series: [{
              data: [{
                "name": "一",
                "value": 82
              }, {
                "name": "二",
                "value": -63
              }, {
                "name": "三",
                "value": 86
              }, {
                "name": "四",
                "value": -75
              }, {
                "name": "五",
                "value": 79
              }]
            }]
          };
          this.chartDataMount = JSON.parse(JSON.stringify(res));
        }, 500);
      },
      getServerDataLine() {
        setTimeout(() => {
          //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
          let res = {
            categories: ["2018", "2019", "2020", "2021", "2022", "2023"],
            series: [{
                name: "实际值A",
                lineType: "dash",
                data: [35, 8, 25, 37, 4, 20]
              },
              {
                name: "预测值B",
                data: [70, 40, 65, 100, 44, 68]
              },
              {
                name: "理想值C",
                data: [100, 80, 95, 150, 112, 132]
              }
            ]
          };
          this.chartDataLine = JSON.parse(JSON.stringify(res));
        }, 500);
      },
      getServerDataRadar() {
        setTimeout(() => {
          //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
          let res = {
            categories: ["维度1", "维度2", "维度3", "维度4", "维度5", "维度6"],
            series: [{
                name: "理想值1",
                data: [90, 110, 165, 195, 187, 172]
              },
              {
                name: "实际值2",
                data: [190, 210, 105, 35, 27, 102]
              }
            ]
          };
          this.chartDataRadar = JSON.parse(JSON.stringify(res));
        }, 500);
      },
      getServerDataRing() {
        setTimeout(() => {
          //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
          let res = {
            series: [{
              data: [{
                "name": "一",
                "value": 50
              }, {
                "name": "二",
                "value": 30
              }, {
                "name": "三",
                "value": 20
              }, {
                "name": "四",
                "value": 18
              }, {
                "name": "五",
                "value": 8
              }]
            }]
          };
          this.chartDataRing = JSON.parse(JSON.stringify(res));
        }, 500);
      },
      getServerDataScatter() {
        setTimeout(() => {
          //模拟服务器返回数据，如果数据格式和标准格式不同，需自行按下面的格式拼接
          let res = {
            series: [{
                name: "实际值",
                data: [
                  [10, 8.04],
                  [8.07, 6.95],
                  [13, 7.58],
                  [9.05, 8.81],
                  [11, 8.33],
                  [14, 7.66],
                  [13.4, 6.81],
                  [10, 6.33],
                  [14, 8.96],
                  [12.5, 6.82]
                ]
              },
              {
                name: "预测值",
                data: [
                  [9.15, 7.2],
                  [11.5, 7.2],
                  [3.03, 4.23],
                  [12.2, 7.83],
                  [2.02, 4.47],
                  [1.05, 3.33],
                  [4.05, 4.96],
                  [6.03, 7.24],
                  [12, 6.26],
                  [12, 8.84],
                  [7.08, 5.82],
                  [5.02, 5.68]
                ]
              },
              {
                name: "理想值",
                data: [
                  [6.15, 7.2],
                  [13.5, 7.2],
                  [2.03, 4.99],
                  [1.2, 7.83],
                  [12.02, 4.97],
                  [1.05, 3.33],
                  [11.05, 4.06],
                  [6.23, 7.28],
                  [12.90, 6.26],
                  [12.22, 8.84],
                  [7.08, 5.22],
                  [5.02, 5.48]
                ]
              }
            ]
          };
          this.chartDataScatter = JSON.parse(JSON.stringify(res));
        }, 500);
      }



    }
  };
</script>

<style lang="scss">
  image {
    height: 335rpx;
    width: 100%;
    margin-top: 5rpx;
    margin-bottom: 10rpx;
  }

  .charts-box {
    width: 100%;
    height: 300px;
  }
</style>