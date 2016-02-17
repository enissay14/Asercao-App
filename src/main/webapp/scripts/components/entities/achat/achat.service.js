'use strict';

angular.module('appApp')
    .factory('Achat', function ($resource) {
        return $resource('api/achats/:id', {}, {
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
			'getByAffaire': {
				method: 'GET', 
				url: 'api/achats/affaire/:id',
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
