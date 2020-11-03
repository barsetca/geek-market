angular.module('app').controller('profileController', function ($scope, $http) {
  const contextPath = 'http://localhost:8189/market';

  $scope.getProfile = function () {
    console.log('getProfile');
    $http({
      url: contextPath + '/api/v1/profile',
      method: 'GET'
    })
    .then(function (response) {
      console.log(response.data);
      $scope.profile = response.data;
    });
  };

  $scope.submitChangeProfile = function () {
    console.log($scope.newProfile.password);
    $scope.newProfile.userId = $scope.profile.userId;
    $scope.newProfile.id = $scope.profile.id;

    $scope.newProfile.username = $scope.newProfile.username == null
        ? $scope.profile.username : $scope.newProfile.username;
    $scope.newProfile.email = $scope.newProfile.email == null
        ? $scope.profile.email : $scope.newProfile.email;
    $scope.newProfile.phone = $scope.newProfile.phone == null
        ? $scope.profile.phone : $scope.newProfile.phone;
    $scope.newProfile.firstname = $scope.newProfile.firstname == null
        ? $scope.profile.firstname : $scope.newProfile.firstname;
    $scope.newProfile.surname = $scope.newProfile.surname == null
        ? $scope.profile.surname : $scope.newProfile.surname;
    $scope.newProfile.birthday = $scope.newProfile.birthday == null
        ? $scope.profile.birthday : $scope.newProfile.birthday;
    $scope.newProfile.sex = $scope.newProfile.sex == null ? $scope.profile.sex
        : $scope.newProfile.sex;
    $scope.newProfile.city = $scope.newProfile.city == null
        ? $scope.profile.city : $scope.newProfile.city;

    $http.put(contextPath + '/api/v1/profile', $scope.newProfile)
    .then(function (response) {
      $(".modal").modal("hide");
      $scope.getProfile();
      alert('Профиль изменен');
    });
  };

  $scope.getProfile();

});