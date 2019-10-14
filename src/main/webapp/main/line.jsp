<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>



<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ["前一天","前二天","前三天","前四天","前五天","前六天","前一周"]
        },
        yAxis: {},
        series: [{
            name: '用户',
            type: 'line'
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

    $.ajax({
        url:"${pageContext.request.contextPath}/user/query",
        datatype:"json",
        type:"POST",
        success:function (da) {
            myChart.setOption({
                series:[{data:da}]
            });
        }
    });


    var goEasy = new GoEasy({
        appkey: "BC-41105467e17d4745869fc7051df656ee"
    });
    goEasy.subscribe({
        channel: "164channel",
        onMessage: function (message) {
            var data = JSON.parse(message.content);
            myChart.setOption({
                series:[{data:data}]
            });
        }
    });


</script>
