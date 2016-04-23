/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('SupplierController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'supplier'};

        $scope.supplierList = [];
        $scope.supplier = {
            supplierType: 'Foreign'
        };
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.editMode = false;

        $scope.count = function () {
            $http.get('app/api/v1/supplier/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.pageChanged();
            }).error(function (e) {
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/supplier/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
                }
            }).success(function (rs) {
                $scope.supplierList = rs;
            }).error(function (e) {
                $scope.supplierList = [];
                console.log(e);
            });
        };

        $scope.addSupplier = function () {
            $http.post('app/api/v1/supplier/save', $scope.supplier).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetSupplier();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.updateSupplier = function () {
            $http.post('app/api/v1/supplier/update', $scope.supplier).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetSupplier();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editSupplier = function (supplier) {
            $scope.supplier = supplier;
            $scope.editMode = true;
        };

        $scope.deleteSupplier = function (id) {
            $http.delete('app/api/v1/supplier/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetSupplier = function () {
            $scope.supplier = {};
            $scope.editMode = false;
        };

        $scope.count();
    }]);