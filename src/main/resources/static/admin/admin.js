angular.module('app').controller('adminController', function ($scope, $http) {
  const contextPath = 'http://localhost:8189/market';

  $scope.getAllUsers = function () {
    console.log('getAllUsers');
    $http({
      url: contextPath + '/api/v1/admin/users',
      method: 'GET'
    })
    .then(function (response) {
      console.log(response.data);
      $scope.users = response.data;
    });
  };

  $scope.delete = function (userId) {
    console.log('user ' + userId);
    $http({
      url: contextPath + '/api/v1/profile/' + userId,
      method: 'DELETE'
    })
    .then(function (response) {
      $scope.getAllUsers();
    });
  };

  $scope.enable = function (userId) {
    console.log('user ' + userId);
    $http({
      url: contextPath + '/api/v1/admin/users/' + userId,
      method: 'PUT'
    })
    .then(function (response) {
      $scope.getAllUsers();
    });
  };
  $scope.getAllUsers();

});