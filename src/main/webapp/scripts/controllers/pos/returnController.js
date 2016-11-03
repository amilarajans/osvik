/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('ReturnController', ['$rootScope', '$scope', '$http', 'toastr', '$uibModal',
    function ($rootScope, $scope, $http, toastr, $uibModal) {

        $rootScope.navigation = {selection: 'return'};

        $scope.invoiceList = [];
        $scope.itemsList = [];
        $scope.invoiceNo = '';
        $scope.currentItem = {};
        $scope.currentInvoice = {};
        $scope.returnDate = new Date();
        $scope.returnItemsList = [];
        $scope.returnItem = {
            qty: 0,
            unitPrice: 0,
            itemCode: '',
            stockId: 0,
            desc: '',
            lotNo: '',
            name: '',
            doe: new Date()
        };
        $scope.causeOfReturn = '';

        $scope.totalQty = 0;
        $scope.totalPrice = 0;
        $scope.totalPriceTmp = 0;

        $scope.editMode = false;
        $scope.invoicePrint = false;
        //pagination
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.loadInvoices = function () {
            $http.get('app/api/v1/return/allInvoices').success(function (rs) {
                $scope.invoiceList = rs;
            }).error(function (e) {
                $scope.invoiceList = [];
                console.log(e);
            });
        };

        $scope.loadItems = function () {
            $http.get('app/api/v1/stock/allStock').success(function (rs) {
                $scope.itemsList = rs;
            }).error(function (e) {
                $scope.itemsList = [];
                console.log(e);
            });
        };

        $scope.changeCurrentItem = function (item) {
            $scope.currentItem = item;
            $scope.returnItem.unitPrice = item.price;
            $scope.returnItem.itemCode = item.code;
            $scope.returnItem.stockId = item.id;
            $scope.returnItem.desc = item.desc;
            $scope.returnItem.lotNo = item.lotNo;
            $scope.returnItem.name = item.name;
            $scope.returnItem.doe = item.doe;
        };

        $scope.changeCurrentInvoice = function (invoice) {
            $scope.currentInvoice = invoice;
            $scope.invoiceNo = invoice.invoiceNo;
        };

        $scope.addItem = function () {
            if ($scope.validate()) {
                $scope.totalQty += $scope.returnItem.qty * 1;
                $scope.totalPrice += $scope.returnItem.qty * $scope.returnItem.unitPrice;
                $scope.totalPriceTmp += $scope.returnItem.qty * $scope.returnItem.unitPrice;

                $scope.returnItemsList.push($scope.returnItem);
                $scope.resetItem();
                toastr.success('Item added to Return invoice');
            }
        };

        $scope.validate = function () {
            var ok = true;
            if (!($scope.returnItem.qty > 0)) {
                toastr.error('Please Enter Return Quantity');
                ok = false;
            }
            if (!$scope.returnItem.itemCode) {
                toastr.error('Please Select Return Item');
                ok = false;
            }
            return ok;
        };

        $scope.returnInvoice = function () {
            if ($scope.validateInvoice()) {
                $http.post('app/api/v1/return/save', {
                    invoiceNo: $scope.invoiceNo,
                    returnItemsList: $scope.returnItemsList,
                    returnDate: $scope.returnDate,
                    causeOfReturn: $scope.causeOfReturn
                }).success(function (data) {
                    toastr.success('Successfully Saved !!');
                    $scope.invoicePrint = true;
                }).error(function (data) {
                    toastr.error(data.message);
                });
            }
        };

        $scope.validateInvoice = function () {
            var ok = true;
            if (!($scope.returnItemsList.length > 0)) {
                toastr.error('No item(s) to Return');
                ok = false;
            }
            if (!$scope.invoiceNo) {
                toastr.error('Please Select a Return Invoice');
                ok = false;
            }
            if (!$scope.causeOfReturn) {
                toastr.error('Please Select a Cause of Return');
                ok = false;
            }
            return ok;
        };

        $scope.resetForm = function () {
            $scope.invoiceNo = '';
            $scope.currentItem = {};
            $scope.currentInvoice = {};
            $scope.returnItemsList = [];
            $scope.returnItem = {
                qty: 0,
                unitPrice: 0,
                itemCode: '',
                stockId: 0,
                desc: '',
                lotNo: '',
                name: '',
                doe: new Date()
            };
            $scope.causeOfReturn = '';

            $scope.totalQty = 0;
            $scope.totalPrice = 0;
            $scope.totalPriceTmp = 0;
        };

        $scope.resetItem = function () {
            $scope.returnItem = {
                qty: 0,
                unitPrice: 0,
                itemCode: '',
                stockId: 0,
                desc: '',
                lotNo: '',
                name: '',
                doe: new Date()
            };
            $scope.currentItem = {};
        };

        $scope.deleteItem = function (item) {
            var index = $scope.returnItemsList.indexOf(item);
            if (index > -1) {
                $scope.returnItemsList.splice(index, 1);
            }
            $scope.totalQty -= item.qty * 1;
            $scope.totalPrice -= item.qty * item.unitPrice;
            $scope.totalPriceTmp -= item.qty * item.unitPrice;
        };

        $scope.loadInvoices();
        $scope.loadItems();
    }]);