angular.module('app').controller('storeController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.range = function (min, max, step) {
        step = step || 1;
        var input = [];
        for (var i = min; i <= max; i += step) {
            input.push(i);
        }
        return input;
    };

    $scope.filter = function (page) {
        page = page || 1;
        $http({
            url: contextPath + '/api/v1/products',
            method: "GET",
            params: {
                page: page,
                title: $scope.newFilter != null ? $scope.newFilter.title : '',
                min_cost: $scope.newFilter != null ? $scope.newFilter.min_cost : '',
                max_cost: $scope.newFilter != null ? $scope.newFilter.max_cost : '',
                categoriesId: $scope.newFilter != null ? $scope.newFilter.categoriesId : [""]
            }
        }).then(function (response) {
            $scope.Products = response.data;
        });
    };

    $scope.addToCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add/' + productId,
            method: 'GET'
        })
            .then(function (response) {
                console.log('ok');
            });
    };

    $scope.clearFilter = function () {
        $scope.newFilter = null;
        $scope.filter();
    };

    $scope.getAllCategories = function () {
        console.log('getAllCategories');
        $http({
            url: contextPath + '/api/v1/categories',
            method: 'GET'
        })
            .then(function (response) {
                $scope.categories = response.data;
            });
    };

    $scope.getAllCategories();

    $scope.filter();

});