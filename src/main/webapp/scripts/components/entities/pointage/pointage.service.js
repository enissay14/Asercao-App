'use strict';

angular.module('appApp')
    .factory('Pointage', function ($resource) {
        return $resource('api/pointages/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var dateFrom = data.date.split("-");
                    data.date = new Date(new Date(dateFrom[0], dateFrom[1] - 1, dateFrom[2]));
                    return data;
                }
            },
            'update': { method:'PUT' },
			'getBySalarie': {
				method: 'GET', 
				url: 'api/pointages/salarie/:id',
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
