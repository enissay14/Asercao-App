'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('statussalaire', {
                parent: 'entity',
                url: '/statussalaire',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.statussalaire.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statussalaire/statussalaires.html',
                        controller: 'StatussalaireController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statussalaire');
                        return $translate.refresh();
                    }]
                }
            });
    });
