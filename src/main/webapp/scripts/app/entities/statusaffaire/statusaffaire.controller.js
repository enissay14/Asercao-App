'use strict';

angular.module('appApp')
    .controller('StatusaffaireController', function ($scope, Statusaffaire, Affaire, ParseLinks) {
        $scope.statusaffaires = [];
        $scope.affaires = Affaire.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Statusaffaire.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.statusaffaires = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Statusaffaire.update($scope.statusaffaire,
                function () {
                    $scope.loadAll();
                    $('#saveStatusaffaireModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Statusaffaire.get({id: id}, function(result) {
                $scope.statusaffaire = result;
                $('#saveStatusaffaireModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Statusaffaire.get({id: id}, function(result) {
                $scope.statusaffaire = result;
                $('#deleteStatusaffaireConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Statusaffaire.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStatusaffaireConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.statusaffaire = {status: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
