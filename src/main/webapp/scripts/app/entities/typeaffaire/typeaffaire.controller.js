'use strict';

angular.module('appApp')
    .controller('TypeaffaireController', function ($scope, Typeaffaire, Affaire, ParseLinks) {
        $scope.typeaffaires = [];
        $scope.affaires = Affaire.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Typeaffaire.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.typeaffaires = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Typeaffaire.update($scope.typeaffaire,
                function () {
                    $scope.loadAll();
                    $('#saveTypeaffaireModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Typeaffaire.get({id: id}, function(result) {
                $scope.typeaffaire = result;
                $('#saveTypeaffaireModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Typeaffaire.get({id: id}, function(result) {
                $scope.typeaffaire = result;
                $('#deleteTypeaffaireConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Typeaffaire.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTypeaffaireConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.typeaffaire = {type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
