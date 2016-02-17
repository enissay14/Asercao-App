'use strict';

angular.module('appApp')
    .factory('Typeintervention', function ($resource) {
        return $resource('api/typeinterventions/:id', {}, {
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
