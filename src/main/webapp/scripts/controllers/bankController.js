/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('BankController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'bank'};

        $scope.bankList = [];
        $scope.bank = {};
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.editMode = false;

        $scope.count = function () {
            $http.get('app/api/v1/bank/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.pageChanged();
            }).error(function (e) {
                console.log(e);
            });
        };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/bank/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
                }
            }).success(function (rs) {
                $scope.bankList = rs;
            }).error(function (e) {
                $scope.bankList = [];
                console.log(e);
            });
        };

        $scope.addBank = function () {
            $http.post('app/api/v1/bank/save', $scope.bank).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetBank();
            }).error(function (data) {
                toastr.error('Failed to Save !!');
            });
        };

        $scope.updateBank = function () {
            $http.post('app/api/v1/bank/update', $scope.bank).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetBank();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editBank = function (bank) {
            $scope.bank = bank;
            $scope.editMode = true;
        };

        $scope.deleteBank = function (id) {
            $http.delete('app/api/v1/bank/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetBank = function () {
            $scope.bank = {};
            $scope.editMode = false;
        };

        $scope.count();
    }]);