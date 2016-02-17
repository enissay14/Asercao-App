'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('typepointage', {
                parent: 'entity',
                url: '/typepointage',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'appApp.typepointage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/typepointage/typepointages.html',
                        controller: 'TypepointageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('typepointage');
                        return $translate.refresh();
                    }]
                }
            });
    });
