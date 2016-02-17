'use strict';

angular.module('appApp')
    .controller('CommandeController', function ($scope, Commande, Affaire, ParseLinks) {
        $scope.commandes = [];
        $scope.affaires = Affaire.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Commande.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.commandes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Commande.update($scope.commande,
                function () {
                    $scope.loadAll();
                    $('#saveCommandeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Commande.get({id: id}, function(result) {
                $scope.commande = result;
                $('#saveCommandeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Commande.get({id: id}, function(result) {
                $scope.commande = result;
                $('#deleteCommandeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Commande.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCommandeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.commande = {codeCommande: null, montant: null, montantAchat: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
