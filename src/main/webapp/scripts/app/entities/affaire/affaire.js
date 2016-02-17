'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('affaire', {
                parent: 'entity',
                url: '/affaire',
                data: {
                    roles: ['ROLE_USER','ROLE_MANAGER'],
                    pageTitle: 'appApp.affaire.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/affaire/affaires.html',
                        controller: 'AffaireController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('affaire');
                        return $translate.refresh();
                    }]
                }
            })
            .state('affaireDetail', {
                parent: 'entity',
                url: '/affaire/:id',
                data: {
                    roles: ['ROLE_MANAGER','ROLE_SUPERUSER'],
                    pageTitle: 'appApp.affaire.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/affaire/affaire-detail.html',
                        controller: 'AffaireDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('affaire');
                        return $translate.refresh();
                    }]
                }
            });
    });
