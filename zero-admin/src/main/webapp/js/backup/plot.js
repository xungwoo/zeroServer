function makePlot(chartId, chatList, labelList, xMax) {
	
    $.jqplot(chartId, chatList, {
        highlighter: {
            show: true,
            sizeAdjust: 1,
            tooltipOffset: 9
        },
        grid: {
            background: 'rgba(57,57,57,0.0)',
            drawBorder: false,
            shadow: false,
            gridLineColor: '#666666',
            gridLineWidth: 2
        },
        legend: {
            show: true,
            placement: 'insideGrid'
        },
        seriesDefaults: {
            showMarker: false
        },
        series: labelList,
        axesDefaults: {
            rendererOptions: {
                baselineWidth: 1.5,
                baselineColor: '#444444',
                drawBaseline: false
            }
        },
        axes: {
            xaxis: {
                min: 0,
                max: xMax,
                tickInterval: 2,
                drawMajorGridlines: false
            },
            yaxis: {
                pad: 0,
                rendererOptions: {
                    minorTicks: 1
                },
                tickOptions: {
                    showMark: false
                }
            }
        }
    });
}

function makeBarPlot(targetId, title, items) {
    $('#'+targetId).jqplot([items], {
        title:title,
        seriesDefaults:{
            renderer:$.jqplot.BarRenderer,
            pointLabels: { show: true }
        },
        axes:{
            xaxis:{
                renderer: $.jqplot.CategoryAxisRenderer,
                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                tickOptions: {
    				angle: 45
    			}
            }
        },
        highlighter: { show: false }
    });
}
