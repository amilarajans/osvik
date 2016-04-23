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

        $scope.editCatMode = false;
        $scope.editSubCatMode = false;

        $scope.count = function () {
            $http.get('app/api/v1/category/count').success(function (rs) {
                $scope.itemsPerPage = rs.pageSize;
                $scope.totalItems = rs.count;
                $scope.pageChanged();
            }).error(function (e) {
                console.log(e);
            });
        };
        //
        // $scope.loadCategory = function () {
        //     $http.get('app/api/v1/category/all').success(function (rs) {
        //         $scope.categoryList = rs;
        //     }).error(function (e) {
        //         $scope.categoryList = [];
        //         console.log(e);
        //     });
        // };

        $scope.pageChanged = function () {
            $http.get('app/api/v1/category/all', {
                params: {
                    page: $scope.currentPage,
                    size: $scope.itemsPerPage
                }
            }).success(function (rs) {
                $scope.categoryList = rs;
            }).error(function (e) {
                $scope.categoryList = [];
                console.log(e);
            });
        };

        $scope.addCategory = function () {
            $http.post('app/api/v1/category/save', $scope.category).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetCategory();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.updateCategory = function () {
            $http.post('app/api/v1/category/update', $scope.category).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetCategory();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editCategory = function (category) {
            $scope.category = category;
            $scope.editCatMode = true;
        };

        $scope.resetCategory = function () {
            $scope.category = {};
            $scope.editCatMode = false;
        };

        $scope.addSubCategory = function () {
            $http.post('app/api/v1/category/sub/save', $scope.subCategory).success(function (data) {
                toastr.success('Successfully Saved !!');
                $scope.pageChanged();
                $scope.resetSubCategory();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.updateSubCategory = function () {
            $scope.subCategory.categoryId = $scope.subCategory.category;
            $http.post('app/api/v1/category/sub/update', $scope.subCategory).success(function (data) {
                toastr.success('Successfully Updated !!');
                $scope.pageChanged();
                $scope.resetSubCategory();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.editSubCategory = function (subCategory, category) {
            console.log(category);
            console.log(subCategory);
            $scope.subCategory = subCategory;
            $scope.subCategory.category = category;
            $scope.editSubCatMode = true;
        };

        $scope.deleteSubCategory = function (id) {
            $http.delete('app/api/v1/category/sub/delete/' + id).success(function (data) {
                toastr.success('Successfully Deleted !!');
                $scope.pageChanged();
            }).error(function (data) {
                toastr.error(data.message);
            });
        };

        $scope.resetSubCategory = function () {
            $scope.subCategory = {};
            $scope.editSubCatMode = false;
        };

        $scope.count();
    }]);