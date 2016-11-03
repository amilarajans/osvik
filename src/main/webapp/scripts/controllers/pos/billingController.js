/**
 * Created by Amila Kumara on 2016-02-10.
 */
activitiAdminApp.controller('BillingController', ['$scope', '$http', 'toastr', '$uibModalInstance', 'invoiceList', 'invoiceDetails',
    function ($scope, $http, toastr, $uibModalInstance, invoiceList, invoiceDetails) {

        $scope.invoiceList = invoiceList;
        $scope.invoiceDetails = invoiceDetails;

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.selectItem = function (result) {
            $uibModalInstance.close(result);
        };

        $scope.printInvoice = function (id) {
            var printContents = document.getElementById(id).innerHTML;
            var popupWin = window.open('', '_blank', 'width=1000,height=600');
            popupWin.document.open();
            popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="styles/bootstrap.print.min.css" /><link rel="stylesheet" type="text/css" href="styles/sb-admin.css" /> <link rel="stylesheet" type="text/css" href="styles/style.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
            popupWin.document.close();
        };

    }]);
