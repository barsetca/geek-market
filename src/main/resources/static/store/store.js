angular.module('app').controller('storeController',
    function ($scope, $http, $localStorage) {
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
            categoriesId: $scope.newFilter != null
                ? $scope.newFilter.categoriesId : [""]
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

      $scope.delete = function (productId) {
        $http({
          url: contextPath + '/api/v1/admin/products/' + productId,
          method: 'DELETE'
        })
        .then(function (response) {
          $scope.filter($scope.Products.number + 1);
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

      $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/api/v1/admin/products', $scope.newProduct)
        .then(function (response) {
          $scope.newProduct = null;
          $(".modal").modal("hide");
          alert('Добавлен новый продукт');
          $scope.filter($scope.Products.totalPages + 1);

        });
      };

      $scope.isAdmin = function () {
        console.log($localStorage.currentUser)
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

      $scope.getAllCategories();

      $scope.filter();

    });