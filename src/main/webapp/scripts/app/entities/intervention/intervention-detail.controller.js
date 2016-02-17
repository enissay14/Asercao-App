'use strict';

angular.module('appApp')
    .controller('InterventionDetailController', function ($scope, $stateParams, Intervention, Salarie, Affaire, Typeintervention) {
        $scope.intervention = {};
        $scope.load = function (id) {
            Intervention.get({id: id}, function(result) {
              $scope.intervention = result;
            });
        };
        $scope.load($stateParams.id);
    });
