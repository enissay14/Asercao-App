'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('typeachat', {
                parent: 'entity',
                url: '/typeachat',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.typeachat.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/typeachat/typeachats.html',
                        controller: 'TypeachatController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('typeachat');
                        return $translate.refresh();
                    }]
                }
            });
    });
