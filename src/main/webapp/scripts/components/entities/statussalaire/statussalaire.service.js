'use strict';

angular.module('appApp')
    .factory('Statussalaire', function ($resource) {
        return $resource('api/statussalaires/:id', {}, {
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
