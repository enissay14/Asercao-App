'use strict';

angular.module('appApp')
    .factory('Affaire', function ($resource) {
        return $resource('api/affaires/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
					if( data.dateDebut!=null){
                    var dateDebutFrom = data.dateDebut.split("-");
                    data.dateDebut = new Date(new Date(dateDebutFrom[0], dateDebutFrom[1] - 1, dateDebutFrom[2]));
					}
					if( data.dateFin!=null){
                    var dateFinFrom = data.dateFin.split("-");
                    data.dateFin = new Date(new Date(dateFinFrom[0], dateFinFrom[1] - 1, dateFinFrom[2]));
					}
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
