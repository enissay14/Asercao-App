'use strict';

angular.module('appApp')
    .factory('Bonfacture', function ($resource) {
        return $resource('api/bonfactures/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
					if( data.date!=null){
                    var dateFrom = data.date.split("-");
                    data.date = new Date(new Date(dateFrom[0], dateFrom[1] - 1, dateFrom[2]));
					}
                    return data;
                }
            },
            'update': { method:'PUT' },
			'getByAffaire': {
				method: 'GET', 
				url: 'api/bonfactures/affaire/:id',
				isArray: true,
				transformResponse: function (data) {
                    data = angular.fromJson(data);
					if( data.date!=null){
						var dateFrom = data.date.split("-");
						data.date = new Date(new Date(dateFrom[0], dateFrom[1] - 1, dateFrom[2]));
					}
                    return data;
                }
			}
        });
    });
