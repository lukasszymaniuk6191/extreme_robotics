/* global angular */
/*eslint no-mixed-spaces-and-tabs: ["error", "smart-tabs"]*/
/*eslint no-console: ["error", { allow: ["warn", "error"] }] */

var  mainPage = angular.module('averageRates', []);

mainPage.service('AverageRates', function(){
	
	var averageRates = new Array();
	
	this.getAverageRates  = function()
	{
		return averageRates ;
	}
	
	this.addAverageRate = function(averageRate)
	{
		averageRates.push(averageRate);
	}

    var codes = new Array();

    this.getCodes = function()
    {
        return codes;
    }

    this.addCode = function(code)
    {
        codes.push(code);
    }
	
});


mainPage.controller('averageRatesCtrl', ['$scope','$http','AverageRates', function($scope,$http,AverageRates) {
    
    $scope.getCheckedObjectData = function(averageRate)
    {
        AverageRates.addCode(averageRate.code);
    }

    $scope.codes=AverageRates.getCodes();


    $scope.getDataAverageRatesFromRestApi = function()
    {
        $http({
            url : '/api/average_rates/all',
            method : 'GET',
            contentType: 'application/json'

        }).then(function success(response) {

            var average_rates_all = response.data;

            for(var i=0; i<average_rates_all.length; i++)
            {
                var averageRate = new AverageRate(average_rates_all[i].currency,
                    average_rates_all[i].code, average_rates_all[i].mid);
                AverageRates.addAverageRate(averageRate);
            }

            $scope.averageRates=AverageRates.getAverageRates();

            console.log('Data saved');

        }, function error(response) {
            console.log('Data not saved ');
        });

    }
}]);  


function AverageRate(currency,code, mid)
{
    this.currency = currency;
    this.code = code;
    this.mid = mid;
    
    this.setAverageRateParams = function(currency,code, mid)
    {
        this.currency = currency;
        this.code = code;
        this.mid = mid; 
    }
    
    this.showAverageParams = function()
    {
        return 'AverageRate: [currency: '+currency+', code: '+code+', mid: '+mid+']';
    }
}

