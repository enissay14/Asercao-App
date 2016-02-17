'use strict';

angular.module('appApp')
    .controller('AffaireController', function ($scope, Affaire, Client, Typeaffaire, Statusaffaire, Bonfacture, Intervention, Achat, Commande, ParseLinks) {
        $scope.affaires = [];
        $scope.clients = Client.query();
        $scope.typeaffaires = Typeaffaire.query();
        $scope.statusaffaires = Statusaffaire.query();
        $scope.bonfactures = Bonfacture.query();
        $scope.interventions = Intervention.query();
        $scope.achats = Achat.query();
        $scope.commandes = Commande.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Affaire.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.affaires = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
			if($scope.affaire.dateDebut!=null){
				$scope.affaire.dateDebut.setDate($scope.affaire.dateDebut.getDate()+1);
			}
			if($scope.affaire.dateFin!=null){
				$scope.affaire.dateFin.setDate($scope.affaire.dateFin.getDate()+1);
			}
            Affaire.update($scope.affaire,
                function () {
                    $scope.loadAll();
                    $('#saveAffaireModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Affaire.get({id: id}, function(result) {
                $scope.affaire = result;
                $('#saveAffaireModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Affaire.get({id: id}, function(result) {
                $scope.affaire = result;
                $('#deleteAffaireConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Affaire.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAffaireConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.affaire = {codeAffaire: null, description: null, respClient: null, respAffaire: null, tauxHoraire : null, dateDebut: null, dateFin: null, sauvegarde : null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
		
		$scope.$watch('affaire.statusaffaire.id', function(newValue, oldValue) {
			if( $scope.affaire != undefined && $scope.affaire.statusaffaire != undefined ){
				var lettre = '';
				var nombre = 0;
				var affairesM = [];
				var affairesE = [];
				var affairesB = [];
				var affairesG = [];
				for(var i = 0; i < $scope.affaires.length ; i++ ) {
					if($scope.affaires[i].statusaffaire.id === 2 ){
						affairesM.push($scope.affaires[i]);
					}if($scope.affaires[i].statusaffaire.id === 3 ){
						affairesE.push($scope.affaires[i]);
					}if($scope.affaires[i].statusaffaire.id === 4 ){
						affairesB.push($scope.affaires[i]);
					} if($scope.affaires[i].statusaffaire.id === 5 ){
						affairesG.push($scope.affaires[i]);
					}  
				}	
				
				if($scope.affaire.statusaffaire.id === 2 ){lettre = 'M';nombre = 3000;} 
				if($scope.affaire.statusaffaire.id === 3 ){lettre = 'E';nombre = 5000;} 
				if($scope.affaire.statusaffaire.id === 4 ){lettre = 'B';nombre = 1000;} 
				if($scope.affaire.statusaffaire.id === 5 ){lettre = 'G';nombre = 2000;} 
				
				
					if($scope.affaire.id === null ) {
				
				if($scope.affaire.statusaffaire.id === 2 ){
					if(affairesM.length === 0 ){$scope.affaire.codeAffaire = lettre + nombre ;
				console.log('in watchGroup :' + $scope.affaire.codeAffaire);}else{
						nombre += parseInt(affairesM[affairesM.length-1].id) + 1;
						$scope.affaire.codeAffaire = lettre + nombre ;
						console.log('in watchGroup :' + $scope.affaire.codeAffaire);
					}
				}
				if($scope.affaire.statusaffaire.id === 3 ){
					if(affairesE.length === 0 ){$scope.affaire.codeAffaire = lettre + nombre ;
				console.log('in watchGroup :' + $scope.affaire.codeAffaire);}else{
						nombre += affairesE[affairesE.length-1].id + 1;
						$scope.affaire.codeAffaire = lettre + nombre ;
					console.log('in watchGroup :' + $scope.affaire.codeAffaire);
					}
				}
				if($scope.affaire.statusaffaire.id === 4 ){
					if(affairesB.length === 0 ){$scope.affaire.codeAffaire = lettre + nombre ;
				console.log('in watchGroup :' + $scope.affaire.codeAffaire);} else{
						nombre += affairesB[affairesB.length-1].id + 1;
						$scope.affaire.codeAffaire = lettre + nombre ;
					console.log('in watchGroup :' + $scope.affaire.codeAffaire);
					}
				}						
				if($scope.affaire.statusaffaire.id === 5 ){
					if(affairesG.length === 0 ){$scope.affaire.codeAffaire = lettre + nombre ;
				console.log('in watchGroup :' + $scope.affaire.codeAffaire);}else{
						nombre += affairesG[affairesG.length-1].id + 1;
						$scope.affaire.codeAffaire = lettre + nombre ;
						console.log('in watchGroup :' + $scope.affaire.codeAffaire);
					}
				}						
				
					}
							
			}
		});
   
   });
