angular.module('TeamFed.navbar',[]).
    directive('navbar', function($state, $interval, $http){
    return{
        restrict:'E',
        scope : {

        },
        controller:function($scope, $rootScope){
            $rootScope.loggedIn = false;

            $scope.loginPage = function(){
                $state.go('login');
            };

            $scope.registerPage = function(){
                $state.go('register');
            };

            $scope.profile = function(){
                $state.go('profile');
            };

            $scope.logout = function(){
                $cookieStore.remove('id');
                $cookieStore.remove('user');
                $state.go('product');
                $rootScope.loggedIn = false;
            };

            $scope.home = function(){

            };

            $scope.viewJobs = function(){
                $state.go('jobs', {
                    mode: "discoverMode"
                });
            };

            $scope.goToConversations = function(conversationId){
                $state.go('conversation', {
                    conversationId: conversationId
                });
            };

            $scope.calendar = function(){
                $state.go('calendar');
            };

            $scope.jobPosting = function(){
                var mainUser = $cookieStore.get('user');
               if(mainUser.recruiter==true){
                   $state.go('jobPosting');
               }else{
                   $state.go('recruiterLogin');

               }
            };

            $scope.mainUser = $cookieStore.get('user');

            if($cookieStore.get('id')){
                $rootScope.loggedIn = true;
                $scope.notifications = $scope.mainUser.notifications;
                var unread = 0;

                for (i = 0; i < $scope.notifications.length; i++) {
                    if(!$scope.notifications[i].read){
                        unread++;
                    }
                }

                $scope.unread = unread;
            }

            $scope.notificationInterval = function () {
                $scope.promise = $interval(function() {
                    $scope.checkNotification();
                }, 8000);
            };
            $scope.notificationInterval();

            $scope.checkNotification = function () {

                if($cookieStore.get('id')){
                    $scope.mainUser = $cookieStore.get('user');
                    console.log($scope.mainUser.id, "Profile");
                    $http.post('/api/restusercontroller/getUserWithId', JSON.stringify($scope.mainUser.id))
                        .success(function (data, status) {
                            if (status == 200) {

                                if(data != 0) {
                                    $scope.mainUser = data;
                                    $cookieStore.put('id',$scope.mainUser.id);
                                    $cookieStore.put('user',$scope.mainUser);
                                    sessionStorage.id = $scope.mainUser.id;
                                    sessionStorage.user = JSON.stringify($scope.mainUser);

                                    $scope.notifications = data.notifications;
                                    console.log(data, "Notification");
                                    var unread = 0;

                                    for (i = 0; i < $scope.notifications.length; i++) {
                                       if(!$scope.notifications[i].read){
                                           unread++;
                                       }
                                    }

                                    $scope.unread = unread;
                                }

                            }
                        })
                        .error(function (error) {
                            console.log("An error has occurred");
                        });
                }
            };
            // $scope.checkNotification();

            $scope.notificationAction = function (notification) {
                if(!notification.read) {
                    $http.post('/api/restnotificationcontroller/markNotificationAsRead', JSON.stringify(notification.id))
                        .success(function (data, status) {
                            if (status == 200) {
                                $scope.unread--;
                            }
                        })
                        .error(function (error) {
                            console.log("An error has occurred");
                        });
                }

                $state.go(notification.action,{
                    applicationId : notification.params
                });
            };


            $scope.conversationInterval = function () {
                $scope.conversationPromise = $interval(function() {
                    $scope.checkConversation();
                }, 6000);
            };

            $scope.conversationInterval();

            $scope.checkConversation = function () {

                if($cookieStore.get('id')){
                    $scope.mainUser = $cookieStore.get('user');
                    $http.post('/api/restconversationcontroller/getConversationByUser', JSON.stringify($scope.mainUser))
                        .success(function (data, status) {
                            if (status == 200) {

                                if(data != 0) {
                                    $rootScope.conversations = data;
                                    $scope.conversations = data;
                                    console.log(data, "conversations");
                                    var unread = 0;

                                    for (var i = 0; i < $scope.conversations.length; i++) {
                                        if($scope.conversations[i].messages.length!=0) {
                                            if(!$scope.conversations[i].messages.slice(-1)[0].read &&
                                                $scope.conversations[i].messages.slice(-1)[0].receiver.id == $scope.mainUser.id){
                                                unread++;
                                                $.snackbar({content: "Message from " + $scope.conversations[i].messages.slice(-1)[0].sender.username});
                                            }
                                        }

                                    }
                                    $scope.unreadConversations = unread;
                                }else {
                                    $scope.unreadConversations = 0;
                                }
                            }
                        })
                        .error(function (error) {
                            console.log("An error has occurred");
                        });
                }
            };

            $scope.getAllUsers = function () {
                $http.get('/api/restusercontroller/getAllUsers')
                    .success(function (data, status) {
                        if (status == 200) {
                           $scope.allUsers = data;
                        }
                    })
                    .error(function (error) {
                        console.log("An error has occurred");
                    });
            };
            $scope.getAllUsers();


            $scope.visitProfile = function (user) {
                $state.go('profile',{
                    id:user.id,
                    fullname:user.firstname+user.lastname
                });
            };

            $scope.goToMyNetwork = function () {
                $state.go('myNetwork');
            };

            $scope.goToUserProfile = function () {
                if(!$scope.mainUser){
                    $state.go('product');
                }
                {
                    $state.go('profile', {
                        id: $scope.mainUser.id,
                        fullname: $scope.mainUser.firstname + $scope.mainUser.lastname
                    });
                }
            };

            $scope.connectionRequestInterval = function () {
                $scope.connectionRequestPromise = $interval(function() {
                    $scope.getUserConnectionRequest();
                }, 10000);
            };
            $scope.connectionRequestInterval();

            $scope.getUserConnectionRequest = function () {
                $scope.connectionRequests = [];
                if($cookieStore.get('id')){
                    $scope.mainUser = $cookieStore.get('user');
                    $http.post('/api/restconnectioncontroller/getConnectionRequestsByUser', JSON.stringify($scope.mainUser))
                        .success(function (data, status) {
                            if (status == 200) {
                                if(data != 0) {
                                    $scope.connectionRequests = data;
                                }

                            }
                        })
                        .error(function (error) {
                            console.log("An error has occurred");
                        });
                }
            };

            $scope.getUserConnectionRequest();

            $scope.acceptConnection = function (connection) {
                delete connection.$$hashKey;
                connection.accpeted = true;
                $http.post('/api/restconnectioncontroller/updateConnection', JSON.stringify(connection))
                    .success(function (data, status) {
                        if (status == 200) {
                            $scope.getUserConnectionRequest();
                        }
                    })
                    .error(function (error) {
                        console.log("An error has occurred");
                    });
            };

        },
        templateUrl:'resources/js/directives/NavbarDirective/navbar.html',
        transclude: false
    }
});
