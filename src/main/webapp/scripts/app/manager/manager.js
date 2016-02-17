'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('manager', {
                abstract: true,
                parent: 'site'
            });
    });