'use strict';

/* Controllers */

activitiAdminApp.controller('UsersController', ['$scope', '$rootScope', '$http', '$timeout','$location', '$uibModal', '$translate', '$q',
    function ($scope, $rootScope, $http, $timeout, $location, $uibModal, $translate, $q) {
		$rootScope.navigation = {selection: 'users'};

		$rootScope.checkLicenseValidity();

		$scope.selectedUsers = [];

	    $q.all([$translate('USERS.HEADER.LOGIN'),
              $translate('USERS.HEADER.FIRSTNAME'),
              $translate('USERS.HEADER.LASTNAME'),
              $translate('USERS.HEADER.EMAIL'),
              $translate('USERS.HEADER.CLUSTER_USER')])
              .then(function(headers) {

                  // Config for grid
                  $scope.gridUsers = {
                      data: 'usersData',
                      enableRowReordering: true,
                      multiSelect: false,
                      keepLastSelected : false,
                      enableSorting: false,
                      rowHeight: 36,
                      selectedItems: $scope.selectedUsers,
                      columnDefs: [{ field: 'login', displayName: headers[0]},
                          { field: 'firstName', displayName: headers[1]},
                          { field: 'lastName', displayName: headers[2]},
                          { field: 'email', displayName: headers[3]},
                          { field: 'isClusterUser', displayName: headers[4]}
                      ]
                  };
        });

        $scope.loadUsers = function() {
        	$http({method: 'GET', url: '/app/rest/users'}).
		        success(function(data, status, headers, config) {
		        	$scope.usersData = data;

		        	// Indicate if the user is used for sending events
		        	if($scope.usersData !== null && $scope.usersData !== undefined) {
		        	    for (var userIndex = 0; userIndex < $scope.usersData.length; userIndex++) {
		        	        var userData = $scope.usersData[userIndex];
		        	        if (userData.authorities !== null && userData.authorities !== undefined) {
		        	            for (var authorityIndex = 0; authorityIndex < userData.authorities.length; authorityIndex++) {
		        	                userData.isClusterUser = $translate.instant('GENERAL.NO');
		        	                if (userData.authorities[authorityIndex] === 'ROLE_CLUSTER_MANAGER') {
                                        userData.isClusterUser = $translate.instant('GENERAL.YES');
		        	                }
 		        	            }

		        	        }
		        	    }
		        	}

		        }).
		        error(function(data, status, headers, config) {
		            console.log('Something went wrong when fetching users');
		        });
        };

        $scope.executeWhenReady(function() {
            $scope.loadUsers();
        });

        // Dialogs
		var resolve = {
			// Reference the current task
			user: function () {
			    return $scope.selectedUsers[0];
	        }
		};

		$scope.deleteUser = function() {
			var modalInstance = $uibModal.open({
				templateUrl: 'views/user-delete-popup.html',
				controller: 'DeleteUserModalInstanceCrtl',
				resolve: resolve
			});

			modalInstance.result.then(function (deleteUser) {
				if (deleteUser) {
				    $scope.addAlert($translate('ALERT.USER.DELETED', $scope.selectedUsers[0]), 'info');

				    // Clear selection after delete, or actions will still point to deleted user
				    $scope.selectedUsers.splice(0,1);
				    $scope.loadUsers();
				}
			});
		};

		$scope.editUser = function() {
			var modalInstance = $uibModal.open({
				templateUrl: 'views/user-edit-popup.html',
				controller: 'EditUserModalInstanceCrtl',
				resolve: resolve
			});

			modalInstance.result.then(function (userUpdated) {
				if (userUpdated) {
				  $scope.addAlert($translate('ALERT.USER.UPDATED', $scope.selectedUsers[0]), 'info');
					$scope.loadUsers();
				}
			});
		};

		$scope.changePassword = function() {
			var modalInstance = $uibModal.open({
				templateUrl: 'views/user-change-password-popup.html',
				controller: 'ChangePasswordModalInstanceCrtl',
				resolve: resolve
			});

			modalInstance.result.then(function (userUpdated) {
				if (userUpdated) {
				  $scope.addAlert($translate('ALERT.USER.PASSWORD-CHANGED', $scope.selectedUsers[0]), 'info');
				}
			});
		};

		$scope.newUser = function() {
			var modalInstance = $uibModal.open({
				templateUrl: 'views/user-new-popup.html',
				controller: 'NewUserModalInstanceCrtl',
				resolve: resolve
			});

			modalInstance.result.then(function (userCreated) {
				if (userCreated) {
				  $scope.addAlert($translate('ALERT.USER.CREATED', userCreated), 'info');
					$scope.loadUsers();
				}
			});
		};
    }]);

activitiAdminApp.controller('DeleteUserModalInstanceCrtl',
    ['$scope', '$uibModalInstance', '$http', 'user', function ($scope, $uibModalInstance, $http, user) {

  $scope.user = user;
  $scope.status = {loading: false};

  $scope.ok = function () {
	  $scope.status.loading = true;
	  $http({method: 'DELETE', url: '/app/rest/users/' + $scope.user.login}).
    	success(function(data, status, headers, config) {
    		$uibModalInstance.close(true);
	  		$scope.status.loading = false;
        }).
        error(function(data, status, headers, config) {
        	$uibModalInstance.close(false);
        	$scope.status.loading = false;
        });
  };

  $scope.cancel = function () {
	if(!$scope.status.loading) {
		$uibModalInstance.dismiss('cancel');
	}
  };
}]);

activitiAdminApp.controller('EditUserModalInstanceCrtl',
    ['$scope', '$uibModalInstance', '$http', 'user', function ($scope, $uibModalInstance, $http, user) {

  $scope.user = user;
  $scope.model = {
		  login: user.login,
		  firstName: user.firstName,
		  lastName: user.lastName,
		  email: user.email
  };

  $scope.status = {loading: false};

  $scope.ok = function () {
	  $scope.status.loading = true;
	  $http({method: 'PUT', url: '/app/rest/users/' + $scope.user.login, data: $scope.model}).
  	  success(function(data, status, headers, config) {
  		  $uibModalInstance.close(true);
  		  $scope.status.loading = false;
      }).error(function(data, status, headers, config) {
    	  $uibModalInstance.close(false);
    	  $scope.status.loading = false;
      });
  };

  $scope.cancel = function () {
	if(!$scope.status.loading) {
		$uibModalInstance.dismiss('cancel');
	}
  };
}]);

activitiAdminApp.controller('ChangePasswordModalInstanceCrtl',
    ['$scope', '$uibModalInstance', '$http', 'user', function ($scope, $uibModalInstance, $http, user) {

	  $scope.user = user;
	  $scope.model = {
			  oldPassword: '',
			  newPassword: ''
	  };

	  $scope.status = {loading: false};

	  $scope.ok = function () {
		  $scope.status.loading = true;
		  $http({method: 'PUT', url: '/app/rest/users/' + $scope.user.login + '/change-password', data: $scope.model}).
	  	  success(function(data, status, headers, config) {
	  		  $uibModalInstance.close(true);
	  		  $scope.status.loading = false;
	      }).error(function(data, status, headers, config) {
	        $scope.status.loading = false;

	        if(data.message) {
	          $scope.model.errorMessage = data.message;
	        }
	      });
	  };

	  $scope.cancel = function () {
		if(!$scope.status.loading) {
			$uibModalInstance.dismiss('cancel');
		}
	  };
	}]);

activitiAdminApp.controller('NewUserModalInstanceCrtl',
    ['$scope', '$uibModalInstance', '$http', function ($scope, $uibModalInstance, $http) {

  $scope.model = {
		  login: '',
		  password: '',
		  firstName: '',
		  lastName: '',
		  email: ''
  };

  $scope.status = {loading: false};

  $scope.ok = function () {
	  $scope.status.loading = true;
	  $http({method: 'POST', url: '/app/rest/users', data: $scope.model, ignoreErrors: true}).
  	  success(function(data, status, headers, config) {
  		  $uibModalInstance.close($scope.model);
  		  $scope.status.loading = false;
      }).error(function(data, status, headers, config) {
    	  $scope.status.loading = false;

    	  if(data.message) {
    	    $scope.model.errorMessage = data.message;
    	  }
      });
  };

  $scope.cancel = function () {
	if(!$scope.status.loading) {
		$uibModalInstance.dismiss('cancel');
	}
  };
}]);
