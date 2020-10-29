angular.module('app').controller('registrationController',
    function ($scope, $http, $localStorage) {
      const contextPath = 'http://localhost:8189/market';

      $scope.tryToAuth = function () {

        $http.post(contextPath + '/reg', $scope.user)
        .then(function successCallback(response) {
          if (response.data.token) {
            $http.defaults.headers.common.Authorization = 'Bearer '
                + response.data.token;
            $localStorage.currentUser = {
              username: $scope.user.username,
              token: response.data.token
            };

            $scope.user.username = null;
            $scope.user.password = null;
            $scope.user.email = null;

            console.log($localStorage.currentUser);

          }
        }, function errorCallback(response) {
          console.log($localStorage.currentUser);
          window.alert("Пользователь с такими данными уже существует");
          $scope.clearUser();
        });
      };

      $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
          $scope.user.username = null;
        }
        if ($scope.user.password) {
          $scope.user.password = null;
        }
      };

      $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
      };

      $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
          return true;
        } else {
          return false;
        }
      };
    });