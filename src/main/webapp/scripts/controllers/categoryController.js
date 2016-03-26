/**
 * Created by Amila-Kumara on 3/19/2016.
 */
activitiAdminApp.controller('CategoryController', ['$rootScope', '$scope', '$http', 'toastr',
    function ($rootScope, $scope, $http, toastr) {

        $rootScope.navigation = {selection: 'category'};

        $scope.categoryList = [];
        $scope.category = {};
        $scope.subCategory = {};
        $scope.maxSize = 10;
        $scope.itemsPerPage = 0;
        $scope.totalItems = 0;
        $scope.currentPage = 1;

        $scope.count = function () {
            $http.get('app/api/v1/category/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.loadCategory();
            }).error(function (e) {
                console.log(e);
            });
        };

        $scope.loadCategory = function () {
            $http.get('app/api/v1/category/all').success(function (rs) {
                $scope.categoryList = rs;
            }).error(function (e) {
                $scope.categoryList = [];
                console.log(e);
            });
        };

        // $scope.pageChanged = function () {
        //     $http.get('app/api/v1/category/all', {
        //         params: {
        //             page: $scope.currentPage,
        //             size: $scope.itemsPerPage
        //         }
        //     }).success(function (rs) {
        //         $scope.categoryList = rs;
        //     }).error(function (e) {
        //         $scope.categoryList = [];
        //         console.log(e);
        //     });
        // };

        $scope.addCategory = function () {
            $http.post('app/api/v1/category/save', $scope.category).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.loadCategory();
                $scope.resetCategory();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetCategory = function () {
            $scope.category = {};
        };

        $scope.addSubCategory = function () {
            $http.post('app/api/v1/category/sub/save', $scope.subCategory).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.resetSubCategory();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetSubCategory = function () {
            $scope.subCategory = {};
        };

        $scope.loadCategory();
    }]);