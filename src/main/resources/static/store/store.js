angular.module('app').controller('storeController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.fillTable = function () {
        $http.get(contextPath + '/api/v1/products')
            .then(function (response) {
                $scope.Products = response.data;
            });
    };


    // $scope.applyFilter = function () {
    //     $http({
    //         url: contextPath + '/api/v1/books',
    //         method: "GET",
    //         params: {obj_title: $scope.obj.title, obj_price: $scope.obj.price}
    //     }).then(function (response) {
    //         ...
    //     });
    // }

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function (response) {
                // $scope.Products.push(response.data);
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

    $scope.filter = function () {
       $http({
           url: contextPath + '/api/v1/products',
           method: "GET",
           params: {title: $scope.newFilter.title, min_cost: $scope.newFilter.min_cost, max_cost: $scope.newFilter.max_cost}
       }).then(function (response){
           $scope.Products = response.data;
        });
    };

    $scope.fillTable();

});