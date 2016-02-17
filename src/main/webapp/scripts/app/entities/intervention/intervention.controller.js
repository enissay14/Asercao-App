'use strict';

angular.module('appApp')
    .controller('InterventionController', function ($scope, Intervention, Salarie, Affaire, Typeintervention, ParseLinks) {
        $scope.interventions = [];
        $scope.salaries = Salarie.query();
        $scope.affaires = Affaire.query();
        $scope.typeinterventions = Typeintervention.query();
        $scope.page = 1;
		$scope.bareme = null;
        $scope.loadAll = function() {
            Intervention.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.interventions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Intervention.update($scope.intervention,
                function () {
                    $scope.loadAll();
                    $('#saveInterventionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Intervention.get({id: id}, function(result) {
                $scope.intervention = result;
                $('#saveInterventionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Intervention.get({id: id}, function(result) {
                $scope.intervention = result;
                $('#deleteInterventionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Intervention.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInterventionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.intervention = {nbreHeure: null, nbreJour: null, montant: null, montantDeplacement: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
		
		$scope.$watchGroup(['intervention.affaire.id','intervention.nbreJour'], function(newValue, oldValue) {
			if( $scope.intervention != undefined && $scope.intervention.affaire != undefined  ){
				for(var i= 0; i < $scope.affaires.length; i++){
					if( $scope.affaires[i].id === $scope.intervention.affaire.id ){
						$scope.intervention.montantDeplacement = $scope.affaires[i].client.distance.bareme * $scope.intervention.nbreJour ;
						console.log($scope.affaires[i].client.distance.bareme+' * '+$scope.intervention.nbreJour);
						console.log($scope.intervention.montantDeplacement);
					}
				}
			}
		});
		
		$scope.$watchGroup(['intervention.nbreHeure','intervention.tauxHoraire'], function(newValue, oldValue) {
			if( $scope.intervention != undefined  ){
				$scope.intervention.montantCorr = $scope.intervention.tauxHoraire * $scope.intervention.nbreHeure ;
				$scope.intervention.montant = $scope.intervention.tauxHoraire * $scope.intervention.nbreHeure ;
			}	
		});
    });
