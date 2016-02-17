'use strict';

angular.module('appApp')
    .controller('ReleveController', function ($q, $scope, $stateParams, Salarie, Intervention, Pointage) {
        
		$scope.interventions = []; ;
		$scope.pointages = [] ;
		$scope.date = new Date();
		
		$scope.monthInterventions = [];
		$scope.monthPointages = [];
		
		$scope.load = function () {
			console.log('in load...');
			$q.all([
				$scope.loadInterventions(),
				$scope.loadPointages()
			])
			.then( function() {
				$scope.monthData = {salaries : [], totalOeuvre : 0, totalAffaire : 1, totalAffaireHS : 0, totalDivers : 0, totalFormation : 0, totalQualite : 0, totalDevis : 0, totalInfo : 0, totalMaladie : 0,  totalRTT : 0};
				$scope.salariesTotal = [];
				console.log('All Interventions & Pointages loaded');
				
				$scope.monthInterventions = [];
				for(var i = 0 ; i < $scope.interventions.length ; i++ ) {
					if( $scope.shouldHideByDate($scope.interventions[i].date)) {
						$scope.monthInterventions.push($scope.interventions[i]);
					}
				}
				
				$scope.monthPointages = [];
				for(var i = 0 ; i < $scope.pointages.length ; i++ ) {
					if( $scope.shouldHideByDate($scope.pointages[i].date)) {
						$scope.monthPointages.push($scope.pointages[i]);
					}
				}
				
				$scope.monthData.salaries = [];
				for(var i = 0; i < $scope.monthInterventions.length ; i++) {
					if(!$scope.contains($scope.monthData.salaries,$scope.monthInterventions[i].salarie)){
						$scope.monthData.salaries.push($scope.monthInterventions[i].salarie);
					}
				}
				
				for(var i = 0; i < $scope.monthPointages .length ; i++) {
					if(!$scope.contains($scope.monthData.salaries,$scope.monthPointages [i].salarie)){
						$scope.monthData.salaries.push($scope.monthPointages[i].salarie);
					}
				}
			
				for(var i = 0; i < $scope.monthData.salaries.length ; i++ ){
					var id = $scope.monthData.salaries[i].id;
					var salarieTotal = {nom : null, id: null,totalOeuvre : 0, totalAffaire : 1, totalAffaireHS : 0, totalDivers : 0, totalFormation : 0, totalQualite : 0, totalDevis : 0, totalInfo : 0, totalMaladie : 0,  totalRTT : 0};
					salarieTotal.id = id;
					salarieTotal.nom = $scope.monthData.salaries[i].nom;
					salarieTotal.totalAffaire = $scope.calculTotalInterv(id,'Affaire');
					$scope.monthData.totalAffaire += salarieTotal.totalAffaire;
					salarieTotal.totalAffaireHS = $scope.calculTotalInterv(id,'Affaire H.S');
					$scope.monthData.totalAffaireHS += salarieTotal.totalAffaireHS;
					salarieTotal.totalDivers = $scope.calculTotalPointage(id,'Divers');
					$scope.monthData.totalDivers += salarieTotal.totalDivers;
					salarieTotal.totalFormation = $scope.calculTotalPointage(id,'Formation');
					$scope.monthData.totalFormation += salarieTotal.totalFormation;
					salarieTotal.totalDevis = $scope.calculTotalPointage(id,'Devis');
					$scope.monthData.totalDevis += salarieTotal.totalDevis;
					salarieTotal.totalQualite = $scope.calculTotalPointage(id,'Qualité');
					$scope.monthData.totalQualite += salarieTotal.totalQualite;
					salarieTotal.totalInfo = $scope.calculTotalPointage(id,'Informatique');
					$scope.monthData.totalInfo += salarieTotal.totalInfo;
					salarieTotal.totalMaladie = $scope.calculTotalPointage(id,'Maladie');
					$scope.monthData.totalMaladie += salarieTotal.totalMaladie;
					salarieTotal.totalRTT = $scope.calculTotalPointage(id,'RTT+Congés');
					$scope.monthData.totalRTT += salarieTotal.totalRTT;
					salarieTotal.totalOeuvre = salarieTotal.totalRTT+salarieTotal.totalMaladie+salarieTotal.totalInfo+salarieTotal.totalAffaire +salarieTotal.totalAffaireHS+salarieTotal.totalDivers+salarieTotal.totalFormation+salarieTotal.totalDevis+salarieTotal.totalQualite;
					$scope.monthData.totalOeuvre += salarieTotal.totalOeuvre;
					$scope.salariesTotal.push(salarieTotal);
					
				}
				
			});
        };
		
		$scope.loadInterventions = function() {
			var d = $q.defer();
			Intervention.query( function(result) {
				$scope.interventions = result ;
				d.resolve(result);
			});
			return d.promise;
		};
		$scope.loadPointages = function() {
			var d = $q.defer();
			Pointage.query( function(result) {
				$scope.pointages = result ;
				d.resolve(result);
			});
			return d.promise;
		};
		
		$scope.contains = function(array,obj) {
			for(var i =0; i < array.length ; i++ ){
				if(array[i].id === obj.id ){
					return true;
					break;
				}
			}
			return false;
		}
		
		$scope.calculTotalInterv = function(id,type){
			var total = 0;
			for(var i = 0; i < $scope.monthInterventions.length ; i++ ) {
				if( id === $scope.monthInterventions[i].salarie.id ) {
					if ($scope.monthInterventions[i].typeintervention.type === type ) {
						total += $scope.monthInterventions[i].nbreHeure;
					}
				}
			}
			return total;
		};
		
		$scope.calculTotalPointage = function(id,type){
			var total = 0;
			for(var i = 0; i < $scope.monthPointages.length ; i++ ) {
				if( id === $scope.monthPointages[i].salarie.id ) {
					if ($scope.monthPointages[i].typepointage.type === type ) {
						total += $scope.monthPointages[i].nbreHeure;
					}
				}
			}
			return total;
		};
		
		$scope.shouldHideBySalarie =function(salarieID,ligneSalarieID) {
			var hide = salarieID === ligneSalarieID ? true : false;
			return hide;
		};
		
		$scope.shouldHideByDate =function(date){
			var ligneDate = date.split("-");
			var pointageDate = parseInt($scope.date.getMonth(),10) + 1;
			var hide = parseInt(ligneDate[1],10) === pointageDate ? true : false;
			return hide;
		}
		
		$scope.$watch('date', function(newValue, oldValue) {
			$scope.load();
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
