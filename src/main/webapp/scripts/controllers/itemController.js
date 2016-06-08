/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('ItemController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'item'};

        $scope.itemList = [];
        $scope.categoryList = [];
        $scope.subCategoryList = [];
        $scope.unitList = [];
        $scope.supplierList = [];
        $scope.item = {};
        $scope.editMode = false;
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.loadCategory = function () {
            $http.get('app/api/v1/category/allCategories').success(function (rs) {
                $scope.categoryList = rs;
            }).error(function (e) {
                $scope.categoryList = [];
                console.log(e);
            });
        };

        $scope.loadSubCategory = function () {
            $http.get('app/api/v1/category/sub/all').success(function (rs) {
                $scope.subCategoryList = rs;
            }).error(function (e) {
                $scope.subCategoryList = [];
                console.log(e);
            });
        };

        $scope.loadUnit = function () {
            $http.get('app/api/v1/unit/allUnit').success(function (rs) {
                $scope.unitList = rs;
            }).error(function (e) {
                $scope.unitList = [];
                console.log(e);
            });
        };

        $scope.loadSupplier = function () {
            $http.get('app/api/v1/supplier/allSupplier').success(function (rs) {
                $scope.supplierList = rs;
            }).error(function (e) {
                $scope.supplierList = [];
                console.log(e);
            });
        };

        $scope.count = function () {
            $http.get('app/api/v1/item/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.pageChanged();
            }).error(function (e) {
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/item/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
                }
            }).success(function (rs) {
                $scope.itemList = rs;
            }).error(function (e) {
                $scope.itemList = [];
                console.log(e);
            });
        };

        $scope.addItem = function () {
            $http.post('app/api/v1/item/save', $scope.item).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetItem();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetItem = function () {
            $scope.item = {};
            $scope.editMode = false;
        };

        $scope.editItem = function (item) {
            $scope.item = item;
            $scope.editMode = true;
        };

        $scope.deleteItem = function (id) {
            $http.delete('app/api/v1/item/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.count();
        $scope.loadCategory();
        $scope.loadSubCategory();
        $scope.loadUnit();
        $scope.loadSupplier();
    }]);