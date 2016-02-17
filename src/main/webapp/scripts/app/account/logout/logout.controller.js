'use strict';

angular.module('appApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
