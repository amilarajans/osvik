/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('RepController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'rep'};

        $scope.repList = [];
        $scope.rep = {};
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.editMode = false;
        $scope.repName;

        $scope.pageChanged = function () {
            if (!$scope.repName) {
                name = '*';
            } else {
                name = $scope.repName;
            }
            $http.get('app/api/v1/rep/all', {
                params: {
                    page: $scope.currentPage,
                    name: name
                }
            }).success(function (rs) {
                $scope.repList = rs.content;
                $scope.totalItems = rs.totalElements;
                $scope.itemsPerPage = rs.size;
            }).error(function (e) {
                $scope.repList = [];
                console.log(e);
            });
        };

        $scope.addRep = function () {
            if ($scope.validate()) {
                $http.post('app/api/v1/rep/save', $scope.rep).success(function (data) {
                    toastr.success('Successfully Saved !!');
                    $scope.pageChanged();
                    $scope.resetRep();
                }).error(function (data) {
                    toastr.error(data.message);
                });
            }
        };

        $scope.validate = function () {
            var ok = true;
            if (!$scope.rep.name) {
                toastr.error('Please Enter Rep Name');
                ok = false;
            }
            if (!$scope.rep.tel) {
                toastr.error('Please Enter Rep Contact no');
                ok = false;
            }
            return ok;
        };

        $scope.updateRep = function () {
            $http.post('app/api/v1/rep/update', $scope.rep).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetRep();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editRep = function (rep) {
            $scope.rep = rep;
            $scope.editMode = true;
        };

        $scope.deleteRep = function (id) {
            $http.delete('app/api/v1/rep/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetRep = function () {
            $scope.rep = {};
            $scope.editMode = false;
        };

        $scope.searchRep = function () {
            $scope.pageChanged();
        };

        $scope.pageChanged();
    }]);