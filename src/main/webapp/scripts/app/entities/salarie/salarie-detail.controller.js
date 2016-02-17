'use strict';

angular.module('appApp')
    .controller('SalarieDetailController', function ($scope, $stateParams, Salarie, Intervention) {
        $scope.salarie = {};
		$scope.load = function (id) {
            Salarie.get({id: id}, function(result) {
              $scope.salarie = result;
            });
        };
        $scope.load($stateParams.id);	
    });