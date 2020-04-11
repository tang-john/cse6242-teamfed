angular.module('TeamFed.LandingPageController', []).
    controller("LandingPageController", function ($scope, $state, $http) {
      $scope.chart = 0;
	var fNames = ["GDP","Student_Debt","Household_Debt"];

	$scope.respSuccess = false;
	$scope.result = "";
	$scope.resStyle = "";

	
	
	$scope.dirty = function() {
		$scope.resStyle = {'font-style':'italic'};
		$scope.respSuccess = false;
	}
	
	$scope.parseDate = function(s) {
		var parts = s.split("/");
		var date = new Date(parts[2],parts[0]-1,parts[1]);
		return date.toLocaleDateString();
	}
	
	$http.get("resources/js/views/marketData.json").success(function(data) {
		$scope.marketData = data;
		
		
	$scope.cpi = data.CPI;
	$scope.ppi = data.PPI;
	$scope.consumerRent = data.ConsumerRent;
	$scope.caseShiller = data.CaseShiller;
	$scope.fed10Y = data.Fed10Year;
	$scope.fed30Y = data.Fed30Year;
	$scope.fed3M = data.Fed3Month;
	$scope.ger10Y = data.German10Y;
	$scope.unemp = data.Unemployment;
		
		
		
	});
	
	$scope.exportSVG = function() {
		var f = document.forms.export;
		f.fname.value = fNames[$scope.chart];
		f.data.value = frames[$scope.chart].document.querySelector("svg").outerHTML;
		f.submit();
	};
	
	$scope.print = function() {
		frames[$scope.chart].print();
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
					scrollTo(0,0);
                }
            })
            .error(function (error) {
                console.log("An error has occurred");
            });
    };
});
