'use strict';

/* Controllers */

activitiAdminApp.controller('AdminController', ['$scope',
    function ($scope) {
    }]);

activitiAdminApp.controller('LoginController', ['$scope', '$location', 'AuthenticationSharedService', '$timeout',
    function ($scope, $location, AuthenticationSharedService, $timeout) {
        $scope.login = function () {
            AuthenticationSharedService.login({
                username: $scope.username,
                password: $scope.password,
                success: function () {
                }
            });
        };
        
        
        // Fix for browser auto-fill of saved passwords, by default it does not trigger a change
        // and the model is not updated see https://github.com/angular/angular.js/issues/1460
        $timeout(function() {
          jQuery('#username').trigger('change');
          jQuery('#password').trigger('change');
        }, 200);
    }]);

activitiAdminApp.controller('LogoutController', ['$location', 'AuthenticationSharedService',
    function ($location, AuthenticationSharedService) {
        AuthenticationSharedService.logout({
            success: function () {
                $location.path('');
            }
        });
    }]);
