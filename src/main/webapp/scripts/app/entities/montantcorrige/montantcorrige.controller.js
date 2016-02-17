'use strict';

angular.module('appApp')
    .controller('MontantcorrigeController', function ($scope, Montantcorrige, Affaire) {
        $scope.montantcorriges = [];
        $scope.affaires = Affaire.query();
        $scope.loadAll = function() {
            Montantcorrige.query(function(result) {
               $scope.montantcorriges = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
			console.log('in creat ...');
			console.log($scope.montantcorrige);
            Montantcorrige.update($scope.montantcorrige,
                function () {
                    $scope.loadAll();
                    $('#saveMontantcorrigeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Montantcorrige.get({id: id}, function(result) {
                $scope.montantcorrige = result;
                $('#saveMontantcorrigeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Montantcorrige.get({id: id}, function(result) {
                $scope.montantcorrige = result;
                $('#deleteMontantcorrigeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Montantcorrige.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMontantcorrigeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.montantcorrige = {montantInterCorr: null, montantAchatCorr: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
		
		
    });
