angular.module('TeamFed', [
    'ngRoute',
    'ui.router',
    'TeamFed.LandingPageController',
    'TeamFed.navbar'
])
.
config(function($stateProvider) {

    $stateProvider
        .state('landingPage', {
            url: "/landingPage",
            templateUrl: 'resources/js/views/landingPage.html',
            controller: 'LandingPageController'
        });
});

