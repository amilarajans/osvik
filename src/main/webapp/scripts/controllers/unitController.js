/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('UnitController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'unit'};

        $scope.unitList = [];
        $scope.unit ;
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.editMode = false;

        $scope.count = function () {
            $http.get('app/api/v1/unit/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.pageChanged();
            }).error(function (e) {
                $scope.unitList = [];
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/unit/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
                }
            }).success(function (rs) {
                $scope.unitList = rs;
            }).error(function (e) {
                $scope.unitList = [];
                console.log(e);
            });
        };

        $scope.addUnit = function () {
            $http.post('app/api/v1/unit/save', $scope.unit).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetUnit();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.updateUnit = function () {
            $http.post('app/api/v1/unit/update', $scope.unit).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetUnit();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editUnit = function (unit) {
            $scope.unit = unit;
            $scope.editMode = true;
        };

        $scope.deleteUnit = function (id) {
            $http.delete('app/api/v1/unit/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetUnit = function () {
            $scope.unit = {};
            $scope.editMode = false;
        };

        $scope.count();
    }]);