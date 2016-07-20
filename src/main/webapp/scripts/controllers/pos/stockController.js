/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('StockController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'stock'};

        $scope.itemList = [];
        $scope.itemsList = [];
        $scope.unitList = [];
        $scope.item = {
            location: '',
            invoiceNo: '',
            code: '',
            qty: '',
            unitId: '',
            price: '',
            mfd: new Date(),
            doe: new Date(),
            lotNo: '',
            batchNo: '',
        };
        $scope.selectedItem = {};
        $scope.editMode = false;
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.loadUnit = function () {
            $http.get('app/api/v1/unit/allUnit').success(function (rs) {
                $scope.unitList = rs;
            }).error(function (e) {
                $scope.unitList = [];
                console.log(e);
            });
        };

        $scope.loadItems = function () {
            $http.get('app/api/v1/item/allItems').success(function (rs) {
                $scope.itemsList = rs;
            }).error(function (e) {
                $scope.itemsList = [];
                console.log(e);
            });
        };

        $scope.currentItem = function (item) {
            $scope.selectedItem = item;
            $http.get('app/api/v1/stock/stockByItem', {
                params: {
                    code: item.code
                }
            }).success(function (rs) {
                $scope.selectedItem.qty = rs.qty;
            }).error(function (e) {
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/stock/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
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

        $scope.addItem = function () {
            $http.post('app/api/v1/stock/save', $scope.item).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetItem();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetItem = function () {
            $scope.item = {
                location: '',
                invoiceNo: '',
                code: '',
                qty: '',
                unitId: '',
                price: '',
                mfd: new Date(),
                doe: new Date(),
                lotNo: '',
                batchNo: '',
            };
            $scope.editMode = false;
        };

        $scope.editItem = function (item) {
            $scope.item = item;
            $scope.editMode = true;
        };

        $scope.deleteItem = function (id) {
            $http.delete('app/api/v1/stock/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.pageChanged();
        $scope.loadUnit();
        $scope.loadItems();
    }]);