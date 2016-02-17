'use strict';

angular.module('appApp')
    .factory('Statusaffaire', function ($resource) {
        return $resource('api/statusaffaires/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
