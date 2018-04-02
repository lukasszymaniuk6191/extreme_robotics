var app = angular.module('chart', ['zingchart-angularjs']);


app.service('chartService', function(){
	

    
/*
    var chartSingleObjectsAverageRateList = new Array();

	this.getSingleObjectsAverageRateList = function()
	{
		return chartSingleObjectsAverageRateList;
	}
	
	this.addObjectToSingleObjectsAverageRateList = function(single_chart)
	{
		chartSingleObjectsAverageRateList.push(single_chart);
	}



    var chartTraidingRateList = new Array();

    this.getChartTraidingRateList = function()
    {
        return chartTraidingRateList;
    }

    this.addObjectToTraidingRateList = function(single_chart)
    {
        chartTraidingRateList.push(single_chart);
    }*/


    var chartLineColorList = ["#f18973","#f7cac9","#b1cbbb","#d5f4e6","#82b74b","#b5e7a0"];
    var chartBackgroundColorList = ["#bc5a45","#f7786b","#deeaee","#80ced6","#405d27","#e3eaa7"];

    this.getChartLineColorList = function()
    {
        return chartLineColorList;
    }
    
     this.getChartBackgroundColorList = function()
    {
        return chartBackgroundColorList;
    }


    var chartList = new Array();

    this.getChartList = function()
    {
        return chartList;
    }

    this.addObjectToChartList = function(xxx)
    {
        chartList.push(xxx)
    }

    this.deleteChartList = function()
    {
        chartList.length=0;
    }
	
});


app.controller('chartCtrl',['$scope','$http','chartService', function($scope,$http,chartService) {




 /*   var startDate = 1522108800000;
    var chartTitle = "xxx";
    */
    
    function addChart(currency,values,line_color,border_color)
    {
            chartService.addObjectToChartList({
                    "values": values,
                    "text": currency,
                    "line-color": line_color,
                    "legend-marker": {
                        "type": "circle",
                        "size": 5,
                        "background-color": line_color,
                        "border-width": 1,
                        "shadow": 0,
                        "border-color": border_color
                    },
                    "marker": {
                        "background-color": line_color,
                        "border-width": 1,
                        "shadow": 0,
                        "border-color": border_color
                    }
            });    
    }

    $scope.getAverageRatesFromRestApi = function(codes)
    {
        chartService.deleteChartList();
        var colorNumber=0;

        for (var i = 0; i < codes.length; i++) {

            $http({
                url:'/api/average_rates?code=' + codes[i] + '&startDate=' + $scope.startDate + '&stopDate=' + $scope.stopDate,
                method: 'GET',
                contentType: 'application/json'

            }).then(function success(response) {


                var average_rates = response.data;
                var midValues = new Array();
                for (var j = 0; j < average_rates.length; j++) {
                    midValues.push(average_rates[j].mid);
                }

                addChart(average_rates[0].currency,midValues,chartService.getChartLineColorList()[colorNumber] ,chartService.getChartBackgroundColorList()[colorNumber]);
                if(colorNumber<5)
                {
                    colorNumber++;
                }

                $scope.myJson["title"]["text"] = "mid";
                var startDate = Date.parse($scope.startDate);
                $scope.myJson["scale-x"]["min-value"] = startDate;
                var jsonSeries = chartService.getChartList();
                $scope.myJson["series"] = jsonSeries;



                console.log("d: "+startDate);
                chartTitle = "mid";

                console.log('Data saved');

            }, function error(response) {
                console.log('Data not saved ');
            });
        }

    }

    
    $scope.getTradingRatesFromRestApi = function(codes)
	 {
         chartService.deleteChartList();
         var colorNumber=0;

             for (var i = 0; i < codes.length; i++) {


	            $http({
                     url:'/api/trading_rates?code=' + codes[i] + '&startDate=' + $scope.startDate + '&stopDate=' + $scope.stopDate,
                     method: 'GET',
                     contentType: 'application/json'

                 }).then(function success(response) {

                     var traiding_rates = response.data;
                     var bidValues = new Array();
                     for (var i = 0; i < traiding_rates.length; i++) {
                         bidValues.push(traiding_rates[i].bid);
                     }

                    addChart(traiding_rates[0].currency,bidValues,chartService.getChartLineColorList()[colorNumber] ,chartService.getChartBackgroundColorList()[colorNumber]);
                    if(colorNumber<5)
                    {
                        colorNumber++;
                    }

                     console.log('Data saved');

                 }, function error(response) {
                     console.log('Data not saved ');
                 });
             }

         }


     $scope.myJson = {
        "type": "line",
            "background-color": "#003849",
            "utc": true,
            "title": {
                "y": "7px",
                "text": "",
                "background-color": "#003849",
                "font-size": "24px",
                "font-color": "white",
                "height": "25px"
            },
            "plotarea": {
                "margin": "20% 8% 14% 12%",
                "background-color": "#003849"
            },
            "legend": {
                "layout": "float",
                "background-color": "none",
                "border-width": 0,
                "shadow": 0,
                "width":"75%",
                "text-align":"middle",
                "x":"25%",
                "y":"10%",
                "item": {
                    "font-color": "#f6f7f8",
                    "font-size": "14px"
                }
            },
            "scale-x": {
                "min-value": "" ,
                "shadow": 0,
                "step": 86400000,
                "line-color": "#f6f7f8",
                "tick": {
                    "line-color": "#f6f7f8"
                },
                "guide": {
                    "line-color": "#f6f7f8"
                },
                "item": {
                    "font-color": "#f6f7f8"
                },
                "transform": {
                    "type": "date",
                    "all": "%D, %d %M<br />%h:%i %A",
                    "guide": {
                        "visible": false
                    },
                    "item": {
                        "visible": false
                    }
                },
                "label": {
                    "visible": false
                },
                "minor-ticks": 0
            },
            "scale-y": {
                "values": "0:14:2",
                /*"line-color": "#f6f7f8",
                "shadow": 0,
                "tick": {
                    "line-color": "#f6f7f8"
                },
                "guide": {
                    "line-color": "#f6f7f8",
                    "line-style": "dashed"
                },
                "item": {
                    "font-color": "#f6f7f8"
                },
                "label": {
                    "text": "Page Views",
                    "font-color": "#f6f7f8"
                },
                "minor-ticks": 0,
                "thousands-separator": ","*/
            },
          /*  "crosshair-x": {
                "line-color": "#f6f7f8",
                "plot-label": {
                    "border-radius": "5px",
                    "border-width": "1px",
                    "border-color": "#f6f7f8",
                    "padding": "10px",
                    "font-weight": "bold"
                },
                "scale-label": {
                    "font-color": "#00baf0",
                    "background-color": "#f6f7f8",
                    "border-radius": "5px"
                }
            },*/
            "tooltip": {
                "visible": false
            },
            "plot": {
                "tooltip-text": "%t views: %v<br>%k",
                "shadow": 0,
                "line-width": "3px",
                "marker": {
                    "type": "circle",
                    "size": 3
                },
                "hover-marker": {
                    "type": "circle",
                    "size": 4,
                    "border-width": "1px"
                }
            },
            "series": ""
     };

    
}]);



function SingleChart(currency,values)
{
    this.currency = currency;
    this.values = values;
    
    this.setSingleChartParams = function(currency,values)
	{
        this.currency = currency;
        this.values = values;
	}
    
    this.showSingleChartParams = function()
    {
        return 'SingleChart: [currency: '+currency+', values: '+values+']';
    }
}