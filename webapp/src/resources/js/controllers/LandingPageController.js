angular.module('TeamFed.LandingPageController', []).
    controller("LandingPageController", function ($scope, $state, $http) {
      $scope.chart = "time_series";


      // $scope.linRegResponse = "";
      $scope.LinReg = function () {
        // console.log($scope.user);
        $http({
          url: '/mlWebservice',
          method: "POST",
          data: JSON.stringify({"Inputs":{"Data":{"ColumnNames":["cpi","gdp","medianHomePrice","fed10YearYield"],"Values":[["0","0","0","0"],["0","0","0","0"]]}},"GlobalParameters":{}})
        })
        // $http.post('/mlWebservice', JSON.stringify({"Inputs":{"Data":{"ColumnNames":["cpi","gdp","medianHomePrice","fed10YearYield"],"Values":[["0","0","0","0"],["0","0","0","0"]]}},"GlobalParameters":{}}))
            .success(function (data, status) {
                if (status == 200) {
                  console.log(data)
                  $scope.linRegResponse = data
                }
            })
            .error(function (error) {
                console.log("An error has occurred");
            });
    };
});
