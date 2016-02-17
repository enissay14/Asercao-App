'use strict';

angular.module('appApp')
    .controller('BonfactureController', function ($scope, Bonfacture, Affaire, ParseLinks) {
        $scope.bonfactures = [];
        $scope.affaires = Affaire.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Bonfacture.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bonfactures = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Bonfacture.update($scope.bonfacture,
                function () {
                    $scope.loadAll();
                    $('#saveBonfactureModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Bonfacture.get({id: id}, function(result) {
                $scope.bonfacture = result;
                $('#saveBonfactureModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Bonfacture.get({id: id}, function(result) {
                $scope.bonfacture = result;
                $('#deleteBonfactureConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Bonfacture.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBonfactureConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.bonfacture = {montant: null, montantAchat: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
