'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('releve', {
                parent: 'entity',
                url: '/releve',
                data: {
                    roles: ['ROLE_MANAGER'],
                    pageTitle: 'Releve'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/releve/releve.html',
                        controller: 'ReleveController'
                    }
                }
            });
    });
