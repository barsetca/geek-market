angular.module('app').controller('orderController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

    $scope.getAllOrders = function () {
        console.log('getAllOrders');
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET'
        })
        .then(function (response) {
            console.log(response.data);
            $scope.orders = response.data;
        });
    };

    $scope.getAllOrders();

});