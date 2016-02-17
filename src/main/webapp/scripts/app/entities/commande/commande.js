'use strict';

angular.module('appApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('commande', {
                parent: 'entity',
                url: '/commande',
                data: {
                    roles: ['ROLE_MANAGER','ROLE_ADMIN'],
                    pageTitle: 'appApp.commande.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/commande/commandes.html',
                        controller: 'CommandeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('commande');
                        return $translate.refresh();
                    }]
                }
            });
    });
