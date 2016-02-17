'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bonfacture', {
                parent: 'entity',
                url: '/bonfacture',
                data: {
                    roles: ['ROLE_MANAGER','ROLE_ADMIN'],
                    pageTitle: 'appApp.bonfacture.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bonfacture/bonfactures.html',
                        controller: 'BonfactureController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bonfacture');
                        return $translate.refresh();
                    }]
                }
            });
    });
