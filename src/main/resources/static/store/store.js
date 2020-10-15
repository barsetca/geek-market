angular.module('app').controller('storeController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.filter();
            });
    };

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
                max_cost: $scope.newFilter != null ? $scope.newFilter.max_cost : ''
            }
        }).then(function (response) {
            $scope.Products = response.data;
        });
    };

    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/api/v1/restcart/add/' + productId)
            .then(function (response) {
                alert('Товар добавлен в корзину')
            });
    };

    $scope.clearFilter = function () {
        $scope.newFilter = null;
        $scope.filter();
    };

    $scope.filter();

});