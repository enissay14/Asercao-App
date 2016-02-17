'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('distance', {
                parent: 'entity',
                url: '/distance',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.distance.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/distance/distances.html',
                        controller: 'DistanceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('distance');
                        return $translate.refresh();
                    }]
                }
            });
    });
