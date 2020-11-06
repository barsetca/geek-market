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
    if ($scope.newProfile.username != $scope.profile.username){
      alert('Вы изменили Nickname! После завршения изменения профиля, пожалуйста, '
          + 'введите повтороно новое имя и действующий пароль на форме входа.'
          + ' Форма откроется автоматически после обновления профиля. Спасибо.');
    }
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

  $scope.tryToAuth = function () {
    console.log($scope.profile.username);
    $scope.user.username = $scope.profile.username;
    console.log($scope.user.username);
    $http.post(contextPath + '/api/v1/profile', $scope.user)
    .then(function successCallback(response) {
      console.log(response.data);
      $scope.user.password = null;
      $scope.submitChangeProfile();

    }, function errorCallback(response) {
      window.alert('Введен Неверный пароль. Нажмите Ок, затем F5 и снова войдите в профиль');
      $scope.getProfile();
    });
  };

  $scope.getProfile();

});