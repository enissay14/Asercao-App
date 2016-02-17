'use strict';

angular.module('appApp')
    .controller('MenuPointageController', function ($scope, $stateParams, Salarie, Intervention, Affaire,  Typeintervention, Pointage, Typepointage) {
        $scope.salarie = {};
		$scope.interventions = [] ;
		$scope.pointages = [];
        $scope.typepointages = Typepointage.query();
		$scope.affaires = Affaire.query();
		$scope.typeinterventions = Typeintervention.query();
		$scope.bareme = null;
		$scope.date = new Date();
        $scope.intervention={};
		$scope.load = function (id) {
            Salarie.get({id: id}, function(result) {
              $scope.salarie = result;
            });
			$scope.loadInterventions (id);
			$scope.loadPointages(id);
        };
		
		$scope.loadInterventions = function (id) {
			Intervention.getBySalarie({id: id}, function(result) {
				$scope.interventions = result ;
				
			});
		};
		
		$scope.loadPointages = function (id) {
			Pointage.getBySalarie({id: id}, function(result) {
				$scope.pointages = result ;
				
			});
		};
		
        $scope.load($stateParams.id);
		
		$scope.createIntervention = function () {
			$scope.intervention.salarie= $scope.salarie;
			$scope.date.setDate(15);
			$scope.intervention.date = $scope.date;
			console.log('in create $scope.intervention.date:' +$scope.intervention.date);
            Intervention.update($scope.intervention,
                function () {
                    $scope.loadInterventions($stateParams.id);
                    $('#saveInterventionModal').modal('hide');
                    $scope.clearIntervention();
                });
        };
		
		$scope.updateIntervention = function (id) {
            Intervention.get({id: id}, function(result) {
                $scope.intervention = result;
                $('#saveInterventionModal').modal('show');
            });
        };

        $scope.deleteIntervention = function (id) {
            Intervention.get({id: id}, function(result) {
                $scope.intervention = result;
                $('#deleteInterventionConfirmation').modal('show');
            });
        };

        $scope.confirmDeleteIntervention = function (id) {
            Intervention.delete({id: id},
                function () {
                    $scope.loadInterventions($stateParams.id);
                    $('#deleteInterventionConfirmation').modal('hide');
                    $scope.clearIntervention();
                });
        };

        $scope.clearIntervention = function () {
            $scope.intervention = { nbreHeure: null, nbreJour: null, tauxHoraire : null, montant: null, montantDeplacement: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
		
		//---------------------------------------------------------------------------------------------------
		// watch for Intervention Form-----------------------------------------------------------------------
		//---------------------------------------------------------------------------------------------------
		
		//calcul montant deplacement
		$scope.$watchGroup(['intervention.affaire.id','intervention.nbreJour'], function(newValue, oldValue) {
			if( $scope.intervention != undefined && $scope.intervention.affaire != undefined  ){
				if($scope.intervention.typeintervention.id === 3){
					for(var i= 0; i < $scope.affaires.length; i++){
						if( $scope.affaires[i].id === $scope.intervention.affaire.id ){
							$scope.intervention.montantDeplacement = $scope.affaires[i].client.distance.bareme * $scope.intervention.nbreJour ;
							console.log($scope.affaires[i].client.distance.bareme+' * '+$scope.intervention.nbreJour);
							console.log('montantDeplacement :'+$scope.intervention.montantDeplacement);
							break;
						}
					}
				}else{
					$scope.intervention.montantDeplacement = 0;
					console.log('montantDeplacement :'+$scope.intervention.montantDeplacement);
				}
			}
		});
		
		//calcul nbreJour
		$scope.$watchGroup(['intervention.typeintervention.id','intervention.nbreHeure'], function(newValue, oldValue) {
			if( $scope.intervention != undefined  && $scope.intervention.typeintervention != undefined ){
				if($scope.intervention.typeintervention.id === 3){
					$scope.intervention.nbreJour = Math.round( $scope.intervention.nbreHeure / 7.4 );
					console.log('nbre jour :'+$scope.intervention.nbreJour );
					}else{
						$scope.intervention.nbreJour = 0;
						console.log('nbre jour :'+$scope.intervention.nbreJour );
					}
				
			}
		});
		
		//calcul montant
		$scope.$watchGroup(['intervention.nbreHeure','intervention.affaire.id'], function(newValue, oldValue) {
			if( $scope.intervention != undefined && $scope.intervention.affaire != undefined ){
				for(var i= 0; i < $scope.affaires.length; i++){
					if( $scope.affaires[i].id === $scope.intervention.affaire.id ){
						$scope.intervention.montant = $scope.affaires[i].tauxHoraire * $scope.intervention.nbreHeure ;
						console.log($scope.affaires[i].tauxHoraire +'*'+ $scope.intervention.nbreHeure);
						console.log($scope.intervention.montant);
						break;
					}
				}
			}				
		});
		
		//------------------------------------------------------------------------------------------
		//Pointage----------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------
		
		$scope.createPointage = function () {
			$scope.pointage.salarie= $scope.salarie;
			$scope.date.setDate(15);
			$scope.pointage.date = $scope.date;
            Pointage.update($scope.pointage,
                function () {
                    $scope.loadPointages($stateParams.id);
                    $('#savePointageModal').modal('hide');
                    $scope.clearPointage();
                });
        };

        $scope.updatePointage = function (id) {
            Pointage.get({id: id}, function(result) {
                $scope.pointage = result;
                $('#savePointageModal').modal('show');
            });
        };

        $scope.deletePointage = function (id) {
            Pointage.get({id: id}, function(result) {
                $scope.pointage = result;
                $('#deletePointageConfirmation').modal('show');
            });
        };

        $scope.confirmDeletePointage = function (id) {
            Pointage.delete({id: id},
                function () {
                    $scope.loadPointages($stateParams.id);
                    $('#deletePointageConfirmation').modal('hide');
                    $scope.clearPointage();
                });
        };

        $scope.clearPointage = function () {
            $scope.pointage = {nbreHeure: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
	
		$scope.shouldHide =function(date){
			var ligneDate = date.split("-");
			var pointageDate = parseInt($scope.date.getMonth(),10) + 1;
			var hide = parseInt(ligneDate[1],10) === pointageDate ? true : false;
			return hide;
		}
		
		$scope.$watch('date', function(newValue, oldValue) {
			$scope.$apply();
		});
		
    })
	.directive('datetimez', function() {
    return {
        restrict: 'A',
        require : 'ngModel',
        link: function(scope, element, attrs, ngModelCtrl) {
          element.datepicker({
           format: "MM-yyyy",
           viewMode: "months", 
            minViewMode: "months",
				language: 'fr',
              pickTime: false,
          }).on('changeDate', function(e) {
            ngModelCtrl.$setViewValue(e.date);
            scope.$apply();
          });
        }
    };
});
