/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('SupplierController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'supplier'};

        $scope.supplierList = [];
        $scope.supplier = {
            supplierType: 'Foreign'
        };
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.editMode = false;
        $scope.supplierName;

        $scope.countries = [];

        $scope.pageChanged = function () {
            if (!$scope.supplierName) {
                name = '*';
            } else {
                name = $scope.supplierName;
            }
            $http.get('app/api/v1/supplier/all', {
                params: {
                    page: $scope.currentPage,
                    name: name
                }
            }).success(function (rs) {
                $scope.supplierList = rs.content;
                $scope.totalItems = rs.totalElements;
                $scope.itemsPerPage = rs.size;

                $http.get('app/api/v1/supplier/count', {}).success(function (rs) {
                    $scope.supplier.code = rs + 1;
                }).error(function (e) {
                    console.log(e);
                });

            }).error(function (e) {
                $scope.supplierList = [];
                console.log(e);
            });
        };

        $scope.loadCountries = function () {
            $http.get('static/countries.json').success(function (rs) {
                $scope.countries = rs;
            }).error(function (e) {
                $scope.countries = [];
            });
        };

        $scope.addSupplier = function () {
            if ($scope.validate()) {
                $http.post('app/api/v1/supplier/save', $scope.supplier).success(function (data) {
                    toastr.success('Successfully Saved !!');
                    $scope.pageChanged();
                    $scope.resetSupplier();
                }).error(function (data) {
                    toastr.error(data.message);
                });
            }
        };

        $scope.validate = function () {
            var ok = true;
            if (!$scope.supplier.name) {
                toastr.error('Please Enter Supplier Name');
                ok = false;
            }
            if (!$scope.supplier.originCountry) {
                toastr.error('Please Enter Supplier Country');
                ok = false;
            }
            return ok;
        };

        $scope.updateSupplier = function () {
            $http.post('app/api/v1/supplier/update', $scope.supplier).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetSupplier();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editSupplier = function (supplier) {
            $scope.supplier = supplier;
            $scope.editMode = true;
        };

        $scope.deleteSupplier = function (id) {
            $http.delete('app/api/v1/supplier/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetSupplier = function () {
            $scope.supplier = {};
            $scope.editMode = false;
        };

        $scope.searchSupplier = function () {
            $scope.pageChanged();
        };

        $scope.pageChanged();
        $scope.loadCountries();
    }]);