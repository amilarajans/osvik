/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('ClientController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'client'};

        $scope.clientList = [];
        $scope.client = {};
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.editMode = false;
        $scope.clientName;

        $scope.pageChanged = function () {
            if (!$scope.clientName) {
                name = '*';
            } else {
                name = $scope.clientName;
            }
            $http.get('app/api/v1/client/all', {
                params: {
                    page: $scope.currentPage,
                    name: name
                }
            }).success(function (rs) {
                $scope.clientList = rs.content;
                $scope.totalItems = rs.totalElements;
                $scope.itemsPerPage = rs.size;

                $http.get('app/api/v1/client/count', {}).success(function (rs) {
                    $scope.client.code = rs + 1;
                }).error(function (e) {
                    console.log(e);
                });

            }).error(function (e) {
                $scope.clientList = [];
                console.log(e);
            });
        };

        $scope.addClient = function () {
            if ($scope.validate()) {
                $http.post('app/api/v1/client/save', $scope.client).success(function (data) {
                    toastr.success('Successfully Saved !!');
                    $scope.pageChanged();
                    $scope.resetClient();
                }).error(function (data) {
                    toastr.error(data.message);
                });
            }
        };

        $scope.validate = function () {
            var ok = true;
            if (!$scope.client.name) {
                toastr.error('Please Enter Client Name');
                ok = false;
            }
            if (!$scope.client.tel) {
                toastr.error('Please Enter Client Contact no');
                ok = false;
            }
            return ok;
        };

        $scope.updateClient = function () {
            $http.post('app/api/v1/client/update', $scope.client).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetClient();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editClient = function (client) {
            $scope.client = client;
            $scope.editMode = true;
        };

        $scope.deleteClient = function (id) {
            $http.delete('app/api/v1/client/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetClient = function () {
            $scope.client = {};
            $scope.editMode = false;
        };

        $scope.searchClient = function () {
            $scope.pageChanged();
        };

        $scope.pageChanged();
    }]);