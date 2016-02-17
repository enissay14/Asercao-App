'use strict';

angular.module('appApp')
    .controller('SalarieController', function ($scope, Salarie, Statussalaire, Intervention, ParseLinks) {
        $scope.salaries = [];
        $scope.statussalaires = Statussalaire.query();
        $scope.interventions = Intervention.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Salarie.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.salaries = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Salarie.update($scope.salarie,
                function () {
                    $scope.loadAll();
                    $('#saveSalarieModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Salarie.get({id: id}, function(result) {
                $scope.salarie = result;
                $('#saveSalarieModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Salarie.get({id: id}, function(result) {
                $scope.salarie = result;
                $('#deleteSalarieConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Salarie.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSalarieConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.salarie = {nom: null, prenom: null, email: null, tel: null, adresse: null, coutRevient: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
		
		$scope.inactif = function(status){
			var hide = false;
			if(status === 'inactif'){
				hide = true;
			}
			return hide;
		};
    });
