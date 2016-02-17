'use strict';

angular.module('appApp')
    .factory('Intervention', function ($resource) {
        return $resource('api/interventions/:id', {}, {
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
			'getBySalarie': {
				method: 'GET', 
				url: 'api/interventions/salarie/:id',
				isArray: true,
				transformResponse: function (data) {
                    data = angular.fromJson(data);
					if( data.date!=null){
						var dateFrom = data.date.split("-");
						data.date = new Date(new Date(dateFrom[0], dateFrom[1] - 1, dateFrom[2]));
					}
                    return data;
                }
			},
			'getByAffaire': {
				method: 'GET', 
				url: 'api/interventions/affaire/:id',
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
