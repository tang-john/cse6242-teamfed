angular.module('TeamFed.navbar',[]).
    directive('navbar', function($state, $interval, $http){
    return{
        restrict:'E',
        scope : {

        },
        controller:function($scope, $rootScope){
        
        },
        templateUrl:'resources/js/directives/NavbarDirective/navbar.html',
        transclude: false
    }
});
