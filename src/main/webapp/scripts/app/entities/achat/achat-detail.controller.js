'use strict';

angular.module('appApp')
    .controller('AchatDetailController', function ($scope, $stateParams, Achat, Typeachat, Affaire) {
        $scope.achat = {};
        $scope.load = function (id) {
            Achat.get({id: id}, function(result) {
              $scope.achat = result;
            });
        };
        $scope.load($stateParams.id);
    });
