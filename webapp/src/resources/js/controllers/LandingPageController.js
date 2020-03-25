angular.module('TeamFed.LandingPageController', []).
    controller("LandingPageController", function ($scope, $state, $http) {
      $scope.chart = "time_series";

	$scope.respSuccess = false;
	$scope.result = "";
	
	$scope.gdp = 10000;
	$scope.cpi = 150;
	$scope.medHome = 150000;
	$scope.fed10Y = 5;


      // $scope.linRegResponse = "";
      $scope.LinReg = function () {
		  $scope.respSuccess = false;
	
        // console.log($scope.user);
        $http({
          url: '/mlWebservice',
          method: "POST",
          data: JSON.stringify({"Inputs":{"Data":{"ColumnNames":["cpi","gdp","medianHomePrice","fed10YearYield"],"Values":[[$scope.cpi,$scope.gdp,$scope.medHome,$scope.fed10Y]]}},"GlobalParameters":{}})
        })
        // $http.post('/mlWebservice', JSON.stringify({"Inputs":{"Data":{"ColumnNames":["cpi","gdp","medianHomePrice","fed10YearYield"],"Values":[["0","0","0","0"],["0","0","0","0"]]}},"GlobalParameters":{}}))
            .success(function (data, status) {
                if (status == 200) {
                  console.log(data)
                  $scope.linRegResponse = data
				$scope.respSuccess = true;
					var res = JSON.parse(data).Results.output1.value.Values[0];
					$scope.result = res[res.length-1];
                }
            })
            .error(function (error) {
                console.log("An error has occurred");
            });
    };
});
