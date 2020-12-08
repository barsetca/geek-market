angular.module('app').controller('adminController', function ($scope, $http) {
  const contextPath = 'http://localhost:8189/market';

  // $scope.getAllCategories = function () {
  //   console.log('getAllCategories');
  //   $http({
  //     url: contextPath + '/api/v1/admin/categories',
  //     method: 'GET'
  //   })
  //   .then(function (response) {
  //     console.log(response.data);
  //     $scope.categories = response.data;
  //   });
  // };
  //
  // $scope.submitCreateNewProduct = function () {
  //   $http.post(contextPath + '/api/v1/admin/products', $scope.newProduct)
  //   .then(function (response) {
  //     $scope.newProduct = null;
  //     alert('Добавлен новый продукт');
  //   });
  // };
  //
  // $scope.getAllCategories();

});