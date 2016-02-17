'use strict';

angular.module('appApp')
    .controller('DistanceController', function ($scope, Distance, Client, ParseLinks) {
        $scope.distances = [];
        $scope.clients = Client.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Distance.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.distances = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Distance.update($scope.distance,
                function () {
                    $scope.loadAll();
                    $('#saveDistanceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Distance.get({id: id}, function(result) {
                $scope.distance = result;
                $('#saveDistanceModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Distance.get({id: id}, function(result) {
                $scope.distance = result;
                $('#deleteDistanceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Distance.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDistanceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.distance = {distance: null, bareme: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
