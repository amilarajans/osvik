/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('RepController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'rep'};

        $scope.repList = [];
        $scope.rep = {};
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.editMode = false;

        $scope.count = function () {
            $http.get('app/api/v1/rep/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.pageChanged();
            }).error(function (e) {
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/rep/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
                }
            }).success(function (rs) {
                $scope.repList = rs;
            }).error(function (e) {
                $scope.repList = [];
                console.log(e);
            });
        };

        $scope.addRep = function () {
            $http.post('app/api/v1/rep/save', $scope.rep).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetRep();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.updateRep = function () {
            $http.post('app/api/v1/rep/update', $scope.rep).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetRep();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editRep = function (rep) {
            $scope.rep = rep;
            $scope.editMode = true;
        };

        $scope.deleteRep = function (id) {
            $http.delete('app/api/v1/rep/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetRep = function () {
            $scope.rep = {};
            $scope.editMode = false;
        };

        $scope.count();
    }]);