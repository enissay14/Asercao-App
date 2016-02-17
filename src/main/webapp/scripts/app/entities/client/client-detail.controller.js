'use strict';

angular.module('appApp')
    .controller('ClientDetailController', function ($scope, $stateParams, Client, Distance, Affaire) {
        $scope.client = {};
        $scope.load = function (id) {
            Client.get({id: id}, function(result) {
              $scope.client = result;
            });
        };
        $scope.load($stateParams.id);
    });
