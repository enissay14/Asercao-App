'use strict';

angular.module('appApp')
    .controller('AchatController', function ($scope, Achat, Typeachat, Affaire, ParseLinks) {
        $scope.achats = [];
        $scope.typeachats = Typeachat.query();
        $scope.affaires = Affaire.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Achat.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.achats = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Achat.update($scope.achat,
                function () {
                    $scope.loadAll();
                    $('#saveAchatModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Achat.get({id: id}, function(result) {
                $scope.achat = result;
                $('#saveAchatModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Achat.get({id: id}, function(result) {
                $scope.achat = result;
                $('#deleteAchatConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Achat.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAchatConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.achat = {nom: null, date: null, montant: null, montantCorr: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
		
		$scope.$watch('achat.montant', function(newValue, oldValue) {
			if( $scope.achat != undefined ){
				$scope.achat.montantCorr = $scope.achat.montant ;
			}	
		});
    });
