angular.module('TeamFed.LandingPageController', []).
    controller("LandingPageController", function ($scope, $state, $http) {
      $scope.chart = "time_series";

	$scope.respSuccess = false;
	$scope.result = "";
	$scope.resStyle = "";
	
	$scope.cpi = 150;
	$scope.ppi = 200;
	$scope.consumerRent = 150;
	$scope.caseShiller = 100;
	$scope.fed10Y = 5;
	$scope.fed30Y = 5;
	$scope.fed3M = 2;
	$scope.ger10Y = 2;
	$scope.unemp = 5;
	
	$scope.dirty = function() {
		$scope.resStyle = {'font-style':'italic'};
	}
	


      // $scope.linRegResponse = "";
      $scope.LinReg = function () {
		  
		  $scope.respSuccess = false;
	
        // console.log($scope.user);
        $http({
          url: '/mlWebservice',
          method: "POST",
          data: JSON.stringify({"Inputs":{"input1":{"ColumnNames":["caseShillerIndex","cpi","fed10YearYield","fed30YearYield","fed3MonthYield","ppi","consumerRent","unemploymentRate","german10Yr"],"Values":[[$scope.caseShiller,$scope.cpi,$scope.fed10Y,$scope.fed30Y,$scope.fed3M,$scope.ppi,$scope.consumerRent,$scope.unemp,$scope.ger10Y]]}},"GlobalParameters":{}})
        })
        // $http.post('/mlWebservice', JSON.stringify({"Inputs":{"Data":{"ColumnNames":["cpi","gdp","medianHomePrice","fed10YearYield"],"Values":[["0","0","0","0"],["0","0","0","0"]]}},"GlobalParameters":{}}))
            .success(function (data, status) {
                if (status == 200) {
                  console.log(data)
                  $scope.linRegResponse = data
				$scope.respSuccess = true;
					var res = JSON.parse(data).Results.output1.value.Values[0];
					$scope.resStyle = {color:'blue','font-weight':'bold','font-size':'12pt'};
					$scope.result = parseFloat(res[res.length-1]);
                }
            })
            .error(function (error) {
                console.log("An error has occurred");
            });
    };
});
