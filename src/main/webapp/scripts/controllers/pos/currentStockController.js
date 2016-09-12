/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('CurrentStockController', ['$rootScope', '$scope', '$http', 'toastr', '$uibModal',
    function ($rootScope, $scope, $http, toastr, $uibModal) {

        $rootScope.navigation = {selection: 'current-stock'};

        $scope.itemList = [];
        $scope.supplierList = [];
        $scope.categoryList = [];
        $scope.subCategoryList = [];

        $scope.searchBy = '';
        $scope.mCat = '*';
        $scope.sCat = '*';
        $scope.supplier = '*';

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

        $scope.loadSupplier = function () {
            $http.get('app/api/v1/supplier/allSupplier').success(function (rs) {
                $scope.supplierList = rs;
            }).error(function (e) {
                $scope.supplierList = [];
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/stock/search', {
                params: {
                    page: $scope.currentPage,
                    q: $scope.searchBy,
                    mc: $scope.mCat,
                    sc: $scope.sCat,
                    s: $scope.supplier
                }
            }).success(function (rs) {
                $scope.itemList = rs.content;
                $scope.totalItems = rs.totalElements;
                $scope.itemsPerPage = rs.size;
            }).error(function (e) {
                $scope.itemList = [];
                console.log(e);
            });
        };

        $scope.stockEdit = function (item) {
            item.currentQty = item.qty;
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/pages/pos/current-stock-edit.html',
                controller: 'StockManagementController',
                size: 'lg',
                resolve: {
                    stockDetails: function () {
                        return item;
                    }
                }
            });

            modalInstance.result.then(function (result) {
                $scope.pageChanged();
            }, function () {
            });
        };

        $scope.pageChanged();
        $scope.loadCategory();
        $scope.loadSubCategory();
        $scope.loadSupplier();
    }]);