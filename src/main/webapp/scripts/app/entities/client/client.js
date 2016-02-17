'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('client', {
                parent: 'entity',
                url: '/client',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.client.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/clients.html',
                        controller: 'ClientController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        return $translate.refresh();
                    }]
                }
            })
            .state('clientDetail', {
                parent: 'entity',
                url: '/client/:id',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.client.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/client/client-detail.html',
                        controller: 'ClientDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('client');
                        return $translate.refresh();
                    }]
                }
            });
    });
