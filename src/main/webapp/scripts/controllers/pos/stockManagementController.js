/**
 * Created by Amila Kumara on 2016-02-10.
 */
activitiAdminApp.controller('StockManagementController', ['$scope', '$http', 'toastr', '$uibModalInstance', 'stockDetails',
    function ($scope, $http, toastr, $uibModalInstance, stockDetails) {

        $scope.stockDetails = stockDetails;

        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.selectItem = function (result) {
            $uibModalInstance.close(result);
        };

        $scope.update = function () {
            if ($scope.validate()) {
                $http.post('app/api/v1/stock/update', {
                    id: $scope.stockDetails.id,
                    price: $scope.stockDetails.price,
                    costPrice: $scope.stockDetails.costPrice,
                    currentQty: $scope.stockDetails.currentQty,
                    qty: $scope.stockDetails.qty
                }).success(function (data) {
                    toastr.success('Stock Update Success !!');
                    $scope.cancel();
                }).error(function (data) {
                    toastr.error('Stock Update Failed !!');
                });
            }
        };

        $scope.validate = function () {
            var ok = true;
            if ($scope.stockDetails.qty < 0) {
                toastr.error('Quantity must be greater than 0');
                ok = false;
            }
            if (!$scope.stockDetails.costPrice) {
                toastr.error('Cost Price cannot be empty');
                ok = false;
            }
            return ok;
        };
    }]);
