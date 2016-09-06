/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('InvoiceController', ['$rootScope', '$scope', '$http', 'toastr', '$uibModal',
    function ($rootScope, $scope, $http, toastr, $uibModal) {

        $rootScope.navigation = {selection: 'invoice'};

        $scope.invoiceList = [];
        $scope.itemList = [];
        $scope.itemsList = [];
        $scope.clientList = [];
        $scope.repList = [];
        $scope.currentItem = {};
        $scope.currentClient = {};
        $scope.currentRep = {};
        $scope.invoiceNo = '';
        $scope.totalQty = 0;
        $scope.totalPrice = 0;
        $scope.totalPriceTmp = 0;
        $scope.discount = 0;
        $scope.creditPeriod = 0;
        $scope.poCode = '';
        $scope.poDate = '';
        $scope.clientCode = '';
        $scope.paymentMethod;
        $scope.invoiceDate = new Date();
        $scope.item = {
            stockId: 0,
            qty: 0,
            unitPrice: 0,
            itemCode: '',
            desc: '',
            name: '',
            lotNo: ''
        };
        $scope.editMode = false;
        $scope.invoicePrint = false;
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.loadClient = function () {
            $http.get('app/api/v1/client/allClients').success(function (rs) {
                $scope.clientList = rs;
            }).error(function (e) {
                $scope.clientList = [];
                console.log(e);
            });
        };
        $scope.loadReps = function () {
            $http.get('app/api/v1/rep/allReps').success(function (rs) {
                $scope.repList = rs;
            }).error(function (e) {
                $scope.repList = [];
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

        $scope.changeCurrentClient = function (item) {
            $scope.clientCode = item.code;
            $scope.currentClient = item;
        };

        $scope.changeCurrentRep = function (item) {
            $scope.currentRep = item;
        };

        $scope.changeCurrentItem = function (item) {
            // console.log(item);
            $scope.currentItem = item;
            $scope.item.unitPrice = item.price;
            $scope.item.itemCode = item.code;
            $scope.item.stockId = item.id;
            $scope.item.desc = item.desc;
            $scope.item.lotNo = item.lotNo;
            $scope.item.name = item.name;
            $scope.item.doe = item.doe;
            $scope.item.category = item.category;
        };

        $scope.addItem = function () {
            if ($scope.validate()) {
                $scope.totalQty += $scope.item.qty * 1;
                $scope.totalPrice += $scope.item.qty * $scope.item.unitPrice;
                $scope.totalPriceTmp += $scope.item.qty * $scope.item.unitPrice;

                $scope.invoiceList.push($scope.item);
                $scope.resetItem();
                // $scope.pageChanged();
                $scope.changeDiscount();
                toastr.success('Item added to invoice');
            }
        };

        $scope.validate = function () {
            var ok = true;
            if (!($scope.item.qty > 0)) {
                toastr.error('Please Enter Quantity');
                ok = false;
            }
            if (!$scope.item.itemCode) {
                toastr.error('Please Select an Item');
                ok = false;
            } else if (!($scope.currentItem.qty >= $scope.item.qty)) {
                toastr.error('Stock Not Available');
                ok = false;
            }
            if (!$scope.paymentMethod) {
                toastr.error('Please Select a Payment Method');
                ok = false;
            }
            if (!$scope.clientCode) {
                toastr.error('Please Select a Client');
                ok = false;
            }
            return ok;
        };

        $scope.changeDiscount = function () {
            if ($scope.discount * 1 > 0) {
                $scope.totalPrice = $scope.totalPriceTmp - (($scope.totalPriceTmp / 100) * $scope.discount)
            } else {
                $scope.totalPrice = $scope.totalPriceTmp;
            }
        };

        $scope.invoice = function () {
            if ($scope.validateInvoice()) {
                $http.post('app/api/v1/invoice/save', {
                    invoices: $scope.invoiceList,
                    discount: $scope.discount,
                    creditPeriod: $scope.creditPeriod,
                    poCode: $scope.poCode,
                    poDate: $scope.poDate,
                    paymentMethod: $scope.paymentMethod,
                    clientCode: $scope.clientCode,
                    totalPrice: $scope.totalPrice,
                    repId: $scope.currentRep.id
                }).success(function (data) {
                    toastr.success('Successfully Saved !!');
                    $scope.invoiceNo = data.invoiceNo;
                    $scope.invoicePrint = true;
                    $scope.loadItems();
                }).error(function (data) {
                    toastr.error(data.message);
                });
            }
        };

        $scope.validateInvoice = function () {
            var ok = true;
            if (!$scope.paymentMethod) {
                toastr.error('Please select a payment method');
                ok = false;
            }
            if ($scope.invoiceList.length <= 0) {
                toastr.error('No item(s) to Invoice');
                ok = false;
            }
            return ok;
        };

        $scope.printInvoice = function () {
            // toastr.success($scope.invoiceNo);
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/pages/pos/bill.html',
                controller: 'BillingController',
                size: 'lg',
                resolve: {
                    invoiceList: function () {
                        return $scope.invoiceList;
                    },
                    invoiceDetails: function () {
                        return {
                            qty: $scope.totalQty,
                            price: $scope.totalPrice,
                            discount: $scope.discount,
                            client: $scope.currentClient,
                            invoiceNo: $scope.invoiceNo,
                            poCode: $scope.poCode,
                            poDate: $scope.poDate,
                            invoiceDate: $scope.invoiceDate
                        };
                    }
                }
            });

            // modalInstance.result.then(function (result) {
            // }, function () {
            // });
        };

        $scope.resetItem = function () {
            $scope.item = {
                stockId: 0,
                qty: 0,
                unitPrice: 0,
                itemCode: '',
                desc: '',
                name: '',
                lotNo: ''
            };
            $scope.currentItem = {};
            $scope.editMode = false;
        };

        $scope.resetInvoice = function () {
            $scope.item = {
                stockId: 0,
                qty: 0,
                unitPrice: 0,
                itemCode: '',
                desc: '',
                name: '',
                lotNo: ''
            };
            $scope.invoiceNo = '';
            $scope.clientCode = '';
            $scope.poCode = '';
            $scope.poDate = '';
            $scope.totalQty = 0;
            $scope.totalPrice = 0;
            $scope.totalPriceTmp = 0;
            $scope.discount = 0;
            $scope.creditPeriod = 0;
            $scope.paymentMethod = '';
            $scope.currentItem = {};
            $scope.currentClient = {};
            $scope.currentRep = {};
            $scope.invoiceList = [];
            $scope.editMode = false;
            $scope.invoicePrint = false;
        };

        $scope.deleteItem = function (item) {
            var index = $scope.invoiceList.indexOf(item);
            if (index > -1) {
                $scope.invoiceList.splice(index, 1);
            }
            $scope.totalQty -= item.qty * 1;
            $scope.totalPrice -= item.qty * item.unitPrice;
            $scope.totalPriceTmp -= item.qty * item.unitPrice;
            $scope.changeDiscount();
            $scope.editMode = true;
        };

        $scope.loadClient();
        $scope.loadItems();
        $scope.loadReps();
    }]);