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
      $scope.oldUserName = $scope.profile.username;
      $scope.oldBirtday = $scope.profile.birthday;
    });
  };

  $scope.submitChangeProfile = function () {
    console.log($scope.profile.birthday)
    if ($scope.oldUserName != $scope.profile.username){
      alert('Вы изменили Nickname! После сообщения "Профиль изменен", пожалуйста, '
          + 'введите повторно новое имя и действующий пароль на форме входа.'
          + ' Форма откроется автоматически после обновления профиля. Спасибо.');
    }
    $scope.profile.birthday = $scope.profile.birthday == null ? $scope.oldBirtday : $scope.profile.birthday;

    $http.put(contextPath + '/api/v1/profile', $scope.profile)
    .then(function (response) {
      $(".modal").modal("hide");
      $scope.getProfile();
      alert('Профиль изменен');
    });
  };

  $scope.tryToAuth = function () {
    console.log($scope.oldUserName);
    // $scope.user.username = $scope.profile.username;
    $scope.user.username = $scope.oldUserName;
    console.log($scope.user.username);
    $http.post(contextPath + '/api/v1/profile', $scope.user)
    .then(function successCallback(response) {
      console.log(response.data);
      $scope.user.password = null;
      $scope.submitChangeProfile();

    }, function errorCallback(response) {
      window.alert('Введен неверный пароль!');
      $scope.getProfile();
    });
  };

  $scope.getProfile();

});