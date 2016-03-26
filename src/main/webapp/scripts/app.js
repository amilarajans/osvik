'use strict';

/* App Module */

var activitiAdminApp = angular.module('activitiAdminApp', ['http-auth-interceptor', 'ngResource', 'ngRoute', 'ngCookies',
    'pascalprecht.translate', 'ngGrid', 'ui.select2', 'ui.bootstrap', 'angularFileUpload','ngSanitize','toastr']);

var authRouteResolver = ['$rootScope', 'AuthenticationSharedService', function ($rootScope, AuthenticationSharedService) {

    if (!$rootScope.authenticated) {
        // Return auth-promise. On success, the promise resolves and user is assumed authenticated from now on. If
        // promise is rejected, route will not be followed (no unneeded HTTP-calls will be dne, which case a 401 in the end, anyway)
        return AuthenticationSharedService.authenticate();
    } else {
        // Authentication done on rootscope, no need to call service again. Any unauthenticated access to REST will result in
        // a 401 and will redirect to login anyway. Done to prevent additional call to authenticate every route-change
        $rootScope.authenticated = true;
        return true;
    }
}];
activitiAdminApp
    .config(['$routeProvider', '$httpProvider', '$translateProvider', '$provide',
        function ($routeProvider, $httpProvider, $translateProvider, $provide) {
            $routeProvider
                .when('/login', {
                    templateUrl: 'views/login.html',
                    controller: 'LoginController'
                })
                .when('/users', {
                    templateUrl: 'views/users.html',
                    controller: 'UsersController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/monitoring', {
                    templateUrl: 'views/monitoring.html',
                    controller: 'MonitoringController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/logout', {
                    templateUrl: 'views/login.html',
                    controller: 'LogoutController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/dashboard', {
                    templateUrl: 'views/pages/dashboard.html',
                    controller: 'DashboardController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/supplier', {
                    templateUrl: 'views/pages/supplier-master.html',
                    controller: 'SupplierController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/client', {
                    templateUrl: 'views/pages/client-master.html',
                    controller: 'ClientController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/rep', {
                    templateUrl: 'views/pages/rep-master.html',
                    controller: 'RepController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/category', {
                    templateUrl: 'views/pages/category-master.html',
                    controller: 'CategoryController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/unit', {
                    templateUrl: 'views/pages/unit-master.html',
                    controller: 'UnitController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/bank', {
                    templateUrl: 'views/pages/bank-master.html',
                    controller: 'BankController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/item', {
                    templateUrl: 'views/pages/item-master.html',
                    controller: 'ItemController',
                    resolve: authRouteResolver,
                    reloadOnSearch: true
                })
                .when('/', {
                    redirectTo: '/login'
                })
                .otherwise({
                    templateUrl: 'views/login.html',
                    controller: 'LoginController',
                    reloadOnSearch: true
                });

            $translateProvider.useSanitizeValueStrategy('sanitize');
            // Initialize angular-translate
            $translateProvider.useStaticFilesLoader({
                prefix: './i18n/',
                suffix: '.json'
            });

            $translateProvider.registerAvailableLanguageKeys(['en'], {
                'en_*': 'en',
                'en-*': 'en'
            }).determinePreferredLanguage();

        }])

    // Custom Http interceptor that adds the correct prefix to each url
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push(function ($q) {
            return {
                'request': function (config) {

                    // Check if it starts with /app, if so add ., such that it works with a context root
                    if (config.url && config.url.indexOf('/app') === 0) {
                        config.url = '.' + config.url;
                    } else if (config.url && config.url.indexOf('app') === 0) {
                        config.url = './' + config.url;
                    }

                    return config || $q.when(config);
                }
            };
        });
    }])

    /* Filters */

    .filter('dateformat', function () {
        return function (date, format) {
            if (date) {
                if (format == ('full')) {
                    // Format the given value
                    return moment(date).format("LLL");
                } else {
                    // By default, return a pretty date, based on the current time
                    return moment(date).format("lll");
                }
            }
            return '';
        };
    })
    .filter('empty', function () {
        return function (value) {
            if (value) {
                return value;
            }
            return '(None)';
        };
    })
    .filter('humanTime', function () {
        return function (milliseconds) {
            var seconds = milliseconds / 1000;
            var numyears = Math.floor(seconds / 31536000);
            var numdays = Math.floor((seconds % 31536000) / 86400);
            var numhours = Math.floor(((seconds % 31536000) % 86400) / 3600);
            var numminutes = Math.floor((((seconds % 31536000) % 86400) % 3600) / 60);
            var numseconds = Math.floor((((seconds % 31536000) % 86400) % 3600) % 60);
            return numyears + " years " + numdays + " days " + numhours + " hours " + numminutes + " minutes " + numseconds + " seconds";
        };
    })
    .filter('megabytes', function () {
        return function (bytes) {
            return Math.floor((bytes / 1048576)) + 'MB';
        };
    })
    .filter('round', function () {
        return function (number) {
            if (!number) {
                return "0";
            } else {
                return +number.toFixed(3);
            }
        };
    })
    .filter('range', function () {
        return function (input, min, max) {
            min = parseInt(min); //Make string input int
            max = parseInt(max);
            for (var i = min; i < max; i++)
                input.push(i);
            return input;
        };
    })
    .run(['$rootScope', '$location', 'AuthenticationSharedService', 'Account',
        function ($rootScope, $location, AuthenticationSharedService, Account) {
            // Call when the 401 response is returned by the client
            $rootScope.$on('event:auth-loginRequired', function (rejection) {
                $rootScope.authenticated = false;

                if ($location.path() !== "/" && $location.path() !== "") {
                    $rootScope.pageAfterLogin = $location.path();
                    $location.path('/login').replace();
                }
            });

            // Call when the user is authenticated
            $rootScope.$on('event:auth-authConfirmed', function () {
                $rootScope.authenticated = true;
                $rootScope.account = Account.get();
            });

            // Call when the user logs in
            $rootScope.$on('event:auth-loginConfirmed', function () {
                $rootScope.authenticated = true;
                $rootScope.account = Account.get();

                if ($rootScope.pageAfterLogin) {
                    $location.path($rootScope.pageAfterLogin);
                } else {
                    // Show default page
                    $location.path('dashboard');
                }
            });

            // Call when the user logs out
            $rootScope.$on('event:auth-loginCancelled', function () {
                $rootScope.authenticated = false;

                // Explicitly clear cluster data to re-fetch
                $rootScope.activeServer = undefined;
                $rootScope.changeActiveCluster(undefined);
                $rootScope.availableClusters = undefined;
                $rootScope.availableServers = undefined;
                $rootScope.serverLoaded = false;
                $location.path('');
            });
        }])
    .run(['$rootScope', '$http', '$timeout', '$location', '$cookies', '$uibModal', '$translate',
        function ($rootScope, $http, $timeout, $location, $cookies, $uibModal, $translate) {

            var proposedLanguage = $translate.proposedLanguage();
            if (proposedLanguage !== 'en') {

                $translate.use('en');
            }

            $rootScope.showLicenseInfo = function () {
                var modalInstance = $uibModal.open({
                    templateUrl: 'views/license-info-popup.html?version=' + Date.now(),
                    controller: 'LicenseInfoModalInstanceCrtl'
                });
            };

            $rootScope.checkLicenseValidity = function () {
                if ($rootScope.activeServer && $rootScope.activeServer.id) {
                    $http({
                        method: 'GET',
                        url: '/app/rest/license-validity?serverConfigId=' + $rootScope.activeServer.id
                    }).success(function (data, status, headers, config) {
                        var readonlyStatus = false;
                        if (!data.adminLicenseValid || !data.serverLicenseValid) {
                            readonlyStatus = true;
                        }

                        $rootScope.serverStatus = {
                            adminLicenseValid: data.adminLicenseValid,
                            serverLicenseValid: data.serverLicenseValid,
                            readonlyStatus: readonlyStatus
                        };
                    }).error(function (data, status, headers, config) {
                        console.log('Something went wrong: ' + data);
                    });
                }
            };

            // Reference the fixed configuration values on the root scope
            $rootScope.config = ActivitiAdmin.Config;

            // Store empty object for filter-references
            $rootScope.filters = {forced: {}};

            // Alerts
            $rootScope.alerts = {
                queue: []
            };

            $rootScope.showAlert = function (alert) {
                if (alert.queue.length > 0) {
                    alert.current = alert.queue.shift();
                    // Start timout for message-pruning
                    alert.timeout = $timeout(function () {
                        if (alert.queue.length == 0) {
                            alert.current = undefined;
                            alert.timeout = undefined;
                        } else {
                            $rootScope.showAlert(alert);
                        }
                    }, 1500);
                } else {
                    $rootScope.alerts.current = undefined;
                }
            };

            $rootScope.addAlert = function (message, type) {
                var newAlert = {message: message, type: type};
                if (!$rootScope.alerts.timeout) {
                    // Timeout for message queue is not running, start one
                    $rootScope.alerts.queue.push(newAlert);
                    $rootScope.showAlert($rootScope.alerts);
                } else {
                    $rootScope.alerts.queue.push(newAlert);
                }
            };

            $rootScope.dismissAlert = function () {
                if (!$rootScope.alerts.timeout) {
                    $rootScope.alerts.current = undefined;
                } else {
                    $timeout.cancel($rootScope.alerts.timeout);
                    $rootScope.alerts.timeout = undefined;
                    $rootScope.showAlert($rootScope.alerts);
                }
            };

            $rootScope.addAlertPromise = function (promise, type) {
                promise.then(function (data) {
                    $rootScope.addAlert(data, type);
                });
            };

            $rootScope.executeWhenReady = function (callback) {
                if ($rootScope.activeServer) {
                    callback();
                } else {
                    $rootScope.$watch('activeServer', function () {
                        if ($rootScope.activeServer) {
                            callback();
                        }
                    });
                }
            };
        }
    ]);

activitiAdminApp.controller('LicenseInfoModalInstanceCrtl',
    ['$scope', '$uibModalInstance', '$http', function ($scope, $uibModalInstance, $http) {

        $scope.model = {};

        $http({method: 'GET', url: '/app/rest/license-info'}).success(function (data, status, headers, config) {
            $scope.model = data;
        }).error(function (data, status, headers, config) {
            console.log('Something went wrong when fetching license info');
        });

        $scope.close = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);
