'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pointage', {
                parent: 'entity',
                url: '/pointage',
                data: {
                    roles: ['ROLE_MANAGER','ROLE_ADMIN'],
                    pageTitle: 'appApp.pointage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pointage/pointages.html',
                        controller: 'PointageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pointage');
                        return $translate.refresh();
                    }]
                }
            });
    });
