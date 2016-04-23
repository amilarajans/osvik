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

        $scope.count = function () {
            $http.get('app/api/v1/client/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.pageChanged();
            }).error(function (e) {
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/client/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
                }
            }).success(function (rs) {
                $scope.clientList = rs;
            }).error(function (e) {
                $scope.clientList = [];
                console.log(e);
            });
        };

        $scope.addClient = function () {
            $http.post('app/api/v1/client/save', $scope.client).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetClient();
            }).error(function (data) {
                toastr.error(data.message);
            });
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

        $scope.count();
    }]);