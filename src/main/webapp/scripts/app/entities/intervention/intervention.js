'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('intervention', {
                parent: 'entity',
                url: '/intervention',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'appApp.intervention.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/intervention/interventions.html',
                        controller: 'InterventionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('intervention');
                        return $translate.refresh();
                    }]
                }
            })
            .state('interventionDetail', {
                parent: 'entity',
                url: '/intervention/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'appApp.intervention.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/intervention/intervention-detail.html',
                        controller: 'InterventionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('intervention');
                        return $translate.refresh();
                    }]
                }
            });
    });
