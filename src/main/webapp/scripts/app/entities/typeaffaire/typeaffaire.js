'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('typeaffaire', {
                parent: 'entity',
                url: '/typeaffaire',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.typeaffaire.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/typeaffaire/typeaffaires.html',
                        controller: 'TypeaffaireController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('typeaffaire');
                        return $translate.refresh();
                    }]
                }
            });
    });
