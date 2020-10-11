(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run();

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'main/profile.html'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            });
    }
})();