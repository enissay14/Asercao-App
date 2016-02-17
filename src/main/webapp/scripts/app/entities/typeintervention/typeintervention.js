'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('typeintervention', {
                parent: 'entity',
                url: '/typeintervention',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'appApp.typeintervention.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/typeintervention/typeinterventions.html',
                        controller: 'TypeinterventionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('typeintervention');
                        return $translate.refresh();
                    }]
                }
            });
    });
