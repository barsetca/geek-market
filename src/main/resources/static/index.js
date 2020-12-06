(function () {
  'use strict';

  angular
  .module('app', ['ngRoute', 'ngStorage'])
  .config(config)
  .run(run);

  function config($routeProvider, $httpProvider) {
    $routeProvider
    .when('/', {
      templateUrl: 'main/main.html'
    })
    .when('/auth', {
      templateUrl: 'auth/auth.html',
      controller: 'authController'
    })
    .when('/reg', {
      templateUrl: 'registration/registration.html',
      controller: 'registrationController'
    })
    .when('/profile', {
      templateUrl: 'profile/profile.html',
      controller: 'profileController'
    })
    .when('/store', {
      templateUrl: 'store/store.html',
      controller: 'storeController'
    })
    .when('/order', {
      templateUrl: 'order/order.html',
      controller: 'orderController'
    })
    .when('/admin', {
      templateUrl: 'admin/admin.html',
      controller: 'adminController'
    })
    .when('/cart', {
      templateUrl: 'cart/cart.html',
      controller: 'cartController'
    });
    $httpProvider.interceptors.push(function ($q, $location) {
      return {
        'responseError': function (rejection, $localStorage, $http) {
          var defer = $q.defer();
          if (rejection.status == 401 || rejection.status == 403) {
            console.log('error: 401-403');
            $location.path('/auth');
            if (!(localStorage.getItem("localUser") === null)) {
              delete $localStorage.currentUser;
              $http.defaults.headers.common.Authorization = '';
              console.log('zxc');
            }
            console.log(rejection.data);
            var answer = JSON.parse(rejection.data);
            console.log(answer);
            // window.alert(answer.message);
          }
          defer.reject(rejection);
          return defer.promise;
        }
      };
    });
  }

  function run($rootScope, $http, $localStorage) {
    if ($localStorage.currentUser) {
      $http.defaults.headers.common.Authorization = 'Bearer '
          + $localStorage.currentUser.token;
    }
  }

})();

angular.module('app').controller('indexController',
    function ($scope, $http, $localStorage) {
      const contextPath = 'http://localhost:8189/market';

      $scope.isUserLoggedIn = function () {

        if ($localStorage.currentUser) {
          return true;
        } else {
          return false
        }
      };

      $scope.isAdmin = function () {

        if ($localStorage.currentUser) {
          var arr = $localStorage.currentUser.roles;
          for (var i = 0; i < arr.length; i++) {
            if ($localStorage.currentUser.roles[0] === 'ROLE_ADMIN') {
              return true;
            }
          }
          return false;
        } else {
          return false
        }
      };

      $scope.tryToLogout = function () {
        console.log('tryToLogout')
        console.log($http.defaults.headers.common.Authorization)
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
    });