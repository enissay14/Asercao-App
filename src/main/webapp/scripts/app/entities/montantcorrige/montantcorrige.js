'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('montantcorrige', {
                parent: 'entity',
                url: '/montantcorrige',
                data: {
                    roles: ['ROLE_MANAGER','ROLE_ADMIN'],
                    pageTitle: 'appApp.montantcorrige.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/montantcorrige/montantcorriges.html',
                        controller: 'MontantcorrigeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('montantcorrige');
                        return $translate.refresh();
                    }]
                }
            });
    });
