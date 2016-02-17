'use strict';

angular.module('appApp')
    .factory('Typeaffaire', function ($resource) {
        return $resource('api/typeaffaires/:id', {}, {
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
