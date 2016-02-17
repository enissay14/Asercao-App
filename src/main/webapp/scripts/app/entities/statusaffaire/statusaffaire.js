'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('statusaffaire', {
                parent: 'entity',
                url: '/statusaffaire',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.statusaffaire.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statusaffaire/statusaffaires.html',
                        controller: 'StatusaffaireController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statusaffaire');
                        return $translate.refresh();
                    }]
                }
            });
    });
