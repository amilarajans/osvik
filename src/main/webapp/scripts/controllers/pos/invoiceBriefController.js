/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('InvoiceBriefController', ['$rootScope', '$scope', '$http', 'toastr', '$uibModal',
    function ($rootScope, $scope, $http, toastr, $uibModal) {

        $rootScope.navigation = {selection: 'invoice-brief'};

        $scope.invoiceList = [];
        $scope.clientList = [];
        $scope.repList = [];
        $scope.currentClient = {};
        $scope.currentRep = {};
        $scope.clientCode;
        $scope.repCode;
        $scope.invoiceNo;
        $scope.paymentMethod = '0';

        $scope.editMode = false;
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

        $scope.changeCurrentClient = function (item) {
            $scope.clientCode = item.code;
            $scope.currentClient = item;
        };

        $scope.changeCurrentRep = function (item) {
            $scope.repCode = item.name;
            $scope.currentRep = item;
        };

        $scope.pageChanged = function () {
            var clientCode;
            var repCode;
            if (!!!$scope.clientCode) {
                clientCode = '*';
            } else {
                clientCode = $scope.clientCode;
            }
            if (!!!$scope.repCode) {
                repCode = '*';
            } else {
                repCode = $scope.repCode;
            }
            $http.get('app/api/v1/invoiceBrief/all', {
                params: {
                    page: $scope.currentPage,
                    clientCode: clientCode,
                    paymentMethod: $scope.paymentMethod,
                    invoiceNo: $scope.invoiceNo,
                    repCode: repCode
                }
            }).success(function (rs) {
                $scope.invoiceList = rs.content;
                $scope.totalItems = rs.totalElements;
                $scope.itemsPerPage = rs.size;
            }).error(function (e) {
                $scope.invoiceList = [];
                console.log(e);
            });
        };

        $scope.addItem = function () {

        };

        $scope.printInvoice = function () {
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

        };

        $scope.resetInvoice = function () {
            $scope.editMode = false;
            $scope.invoicePrint = false;
        };

        $scope.loadClient();
        $scope.loadReps();
    }]);