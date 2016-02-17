'use strict';

angular.module('appApp')
    .controller('ClientController', function ($scope, Client, Distance, Affaire, ParseLinks) {
        $scope.clients = [];
        $scope.distances = Distance.query();
        $scope.affaires = Affaire.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Client.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.clients = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Client.update($scope.client,
                function () {
                    $scope.loadAll();
                    $('#saveClientModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
                $('#saveClientModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Client.get({id: id}, function(result) {
                $scope.client = result;
                $('#deleteClientConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Client.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClientConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.client = {nom: null, email: null, tel: null, adresse: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
