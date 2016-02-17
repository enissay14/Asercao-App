'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('achat', {
                parent: 'entity',
                url: '/achat',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'appApp.achat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/achat/achats.html',
                        controller: 'AchatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('achat');
                        return $translate.refresh();
                    }]
                }
            })
            .state('achatDetail', {
                parent: 'entity',
                url: '/achat/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'appApp.achat.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/achat/achat-detail.html',
                        controller: 'AchatDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('achat');
                        return $translate.refresh();
                    }]
                }
            });
    });
