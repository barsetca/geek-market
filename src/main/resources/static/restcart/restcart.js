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

    $scope.fillCartTable();

});