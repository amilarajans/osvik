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
        $scope.bankName;

        $scope.pageChanged = function () {
            if (!$scope.bankName) {
                name = '*';
            } else {
                name = $scope.bankName;
            }
            $http.get('app/api/v1/bank/all', {
                params: {
                    page: $scope.currentPage,
                    name: name
                }
            }).success(function (rs) {
                $scope.bankList = rs.content;
                $scope.totalItems = rs.totalElements;
                $scope.itemsPerPage = rs.size;
            }).error(function (e) {
                $scope.bankList = [];
                console.log(e);
            });
        };

        $scope.addBank = function () {
            if ($scope.validate()) {
                $http.post('app/api/v1/bank/save', $scope.bank).success(function (data) {
                    toastr.success('Successfully Saved !!');
                    $scope.pageChanged();
                    $scope.resetBank();
                }).error(function (data) {
                    toastr.error('Failed to Save !!');
                });
            }
        };

        $scope.validate = function () {
            var ok = true;
            if (!$scope.bank.name) {
                toastr.error('Please Enter Bank Name');
                ok = false;
            }
            if (!$scope.bank.accNo) {
                toastr.error('Please Enter Account No');
                ok = false;
            }
            if (!$scope.bank.accType) {
                toastr.error('Please Enter Account Type');
                ok = false;
            }
            // if (!$scope.bank.tel) {
            //     toastr.error('Please Enter Bank Contact No');
            //     ok = false;
            // }
            return ok;
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

        $scope.searchBank = function () {
            $scope.pageChanged();
        };

        $scope.pageChanged();
    }]);