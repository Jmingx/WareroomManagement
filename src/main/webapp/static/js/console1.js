"use strict";
layui.use(["okUtils", "okCountUp"], function () {
    var countUp = layui.okCountUp;
    var okUtils = layui.okUtils;
    var $ = layui.jquery;
    okLoading.close();

    /**
     * 全部设备，维修设备，报废设备，申购设备
     */
    function initMediaCont() {
        var elem_nums = $(".media-cont .num");

        $.get("/console/getElemNums", function (rep) {
            if (rep.message === "success") {
                elem_nums.each(function (i, j) {
                    !new countUp({
                        target: j,
                        endVal: rep.content[i]
                    }).start();
                });
            }
        });

    }

    function randomData() {
        return Math.round(Math.random() * 500);
    }

    function dataTrendOption(color) {
        color = color || "#00c292";
        return {
            color: color, toolbox: {show: false, feature: {saveAsImage: {}}},
            grid: {left: '-1%', right: '0', bottom: '0', top: '5px', containLabel: false},
            xAxis: [{
                type: 'category',
                boundaryGap: false,
                splitLine: {show: false},
                data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
            }],
            yAxis: [{type: 'value', splitLine: {show: false}}],
            series: [{
                name: '用户', type: 'line', stack: '总量', smooth: true, symbol: "none", clickable: false, areaStyle: {},
                data: [randomData(), randomData(), randomData(), randomData(), randomData(), randomData(), randomData()]
            }]
        }
    }


    /**
     * 特色图标
     */
    function initDataTrendChart() {
        // 收入
        var echIncome = echarts.init($("#echIncome")[0]);
        // 商品
        var echGoods = echarts.init($('#echGoods')[0]);
        // 博客
        var echBlogs = echarts.init($("#echBlogs")[0]);
        // 用户
        var echUser = echarts.init($('#echUser')[0]);

        echIncome.setOption(dataTrendOption("#00c292"));
        echGoods.setOption(dataTrendOption("#ab8ce4"));
        echBlogs.setOption(dataTrendOption("#03a9f3"));
        echUser.setOption(dataTrendOption("#fb9678"));
        okUtils.echartsResize([echIncome, echGoods, echBlogs, echUser]);
    }


    /**
     * 实验室设备类型价值
     */

    okUtils.ajax('/console/getDeviceValueData', "get", null, false).done(function (rep) {
        var names = [];
        var data = [];

        for (let row in rep.content) {
            names.push(rep.content[row].name);

            data.push(rep.content[row].data);
        }
        var deviceValueDataOption = {
            color: "#00c292",
            xAxis: {type: 'value'},
            yAxis: {type: 'category',data: names},
            series: [{data: data, type: 'bar',itemStyle:{
                    normal:{
                        label:{
                            show:true,
                            position:'right',
                            textStyle:{
                                color: 'black',
                                fontSize: 13
                            }
                        }
                    }}}]
        };
        var deviceValueDataChart = echarts.init($("#deviceValueDataChart")[0], "themez");
        deviceValueDataChart.setOption(deviceValueDataOption);
        okUtils.echartsResize([deviceValueDataChart]);

    }).fail(function (rep) {
        console.log(rep);
    });


    /**
     * 实验室设备类别统计
     */
    okUtils.ajax('/console/getPieData', "get", null, false).done(function (rep) {
        var names = [];
        var pieData = rep.content;
        for (let x in pieData) {
            names.push(pieData[x].name);
        }
        var deviceCategoryChartOption = {
            title: {show:false, text: '实验室设备种类数量', x: 'center'},
            tooltip: {trigger: 'item', formatter: "{a} <br/>{b} : {c} ({d}%)"},
            legend: {orient: 'vertical', left: 'left', data: names},
            series: [
                {
                    name: '设备类别', type: 'pie', radius: '55%', center: ['50%', '60%'],
                    data: rep.content,
                    itemStyle: {emphasis: {shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)'}
                    }
                }
            ]
        };
        var deviceCategoryChart = echarts.init($("#deviceCategoryChart")[0], "themez");
        deviceCategoryChart.setOption(deviceCategoryChartOption);
        okUtils.echartsResize([deviceCategoryChart]);
    });

    /**
     *实验室资金统计
     */
    okUtils.ajax('/console/getDeviceIO', "get", null, false).done(function (rep) {
        var data = [];
        for (let x in rep.content) {

            data.push(rep.content[x]);

        }
        console.log(data);
        var deviceIODataOption = {
            color: "#03a9f3",
            xAxis: {type: 'category', data: ['设备总值', '维修金额', '购买金额', '报废总值']},
            yAxis: {type: 'value'},
            series: [{data: data, type: 'bar',itemStyle:{
                    normal:{
                        label:{
                            show:true,
                            position:'top',
                            textStyle:{
                                color: 'black',
                                fontSize: 13
                            }
                        }
                    }}}]
        };
        var deviceIOChart = echarts.init($("#deviceIOChart")[0], "themez");
        deviceIOChart.setOption(deviceIODataOption);
        okUtils.echartsResize([deviceIOChart]);
    }).fail(function (rep) {

    });


    initMediaCont();
    initDataTrendChart();

});


