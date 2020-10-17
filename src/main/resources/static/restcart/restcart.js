angular.module('app').controller('restcartController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.fillCartTable = function () {
        console.log('fillCartTable');
        $http({
            url: contextPath + '/api/v1/restcart',
            method: "GET",
        }).then(function (response) {
            $scope.cart = response.data;
            console.log('response.data');
            console.log(response.data);
        });
    };

    $scope.deleteOrderItem = function (productId) {
        $http.delete(contextPath + '/api/v1/restcart/' + productId)
            .then(function (response) {
                $scope.fillCartTable();
            });
    };

    $scope.fillCartTable();

});