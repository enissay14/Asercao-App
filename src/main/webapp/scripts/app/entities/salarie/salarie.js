'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('salarie', {
                parent: 'entity',
                url: '/salarie',
                data: {
                    roles: ['ROLE_USER','ROLE_MANAGER'],
                    pageTitle: 'appApp.salarie.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/salarie/salaries.html',
                        controller: 'SalarieController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('salarie');
                        return $translate.refresh();
                    }]
                }
            })
            .state('salarieDetail', {
                parent: 'entity',
                url: '/salarie/:id',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'appApp.salarie.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/salarie/salarie-detail.html',
                        controller: 'SalarieDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('salarie');
                        return $translate.refresh();
                    }]
                }
            })
			 .state('menuPointage', {
                parent: 'entity',
                url: '/menu-pointage/:id',
                data: {
                    roles: ['ROLE_USER','ROLE_MANAGER'],
                    pageTitle: 'Menu Pointage'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/salarie/menu-pointage.html',
                        controller: 'MenuPointageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('salarie');
                        return $translate.refresh();
                    }]
                }
            });
    });
