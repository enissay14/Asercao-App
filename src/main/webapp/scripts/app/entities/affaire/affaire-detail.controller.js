'use strict';

angular.module('appApp')
	.factory('Excel',function($window){
          var uri='data:application/vnd.ms-excel;base64,',
            template='<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><meta charset="utf-8"><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
            base64=function(s){return $window.btoa(unescape(encodeURIComponent(s)));},
            format=function(s,c){return s.replace(/{(\w+)}/g,function(m,p){return c[p];})};
        return {
            tableToExcel:function(tableId,worksheetName){
                var table=$(tableId),
                    ctx={worksheet:worksheetName,table:table.html()},
                    href=uri+base64(format(template,ctx));
                return href;
            }
        };
    })
    .controller('AffaireDetailController', function (Excel,$timeout,$q,$scope, $stateParams, Affaire, Client, Typeintervention, Bonfacture,Montantcorrige, Intervention, Achat, Typeachat, Commande, $window) {
        
        var reload = false;
        var editCommande = false;
        var commandeEditee = null;
        
        $scope.typeinterventions = Typeintervention.query();
        $scope.typeachats = Typeachat.query();
        $scope.AchatNbreHeureDisabled = true;
        
        $scope.affaire = {};
        $scope.commandes = {} ;
        $scope.interventions = {} ;
        $scope.montantcorriges = {};
        $scope.achats = {} ;
        $scope.bonfactures = {} ;
        
        $scope.loadAffaire = function(id) {
                console.log('DEBUG: In loadAffaire...');
                var d = $q.defer();
                Affaire.get({id: id}, function(result) {
                        $scope.affaire = result;
                        d.resolve(result);
                });
                return d.promise;
        };
        $scope.loadCommandes = function(id) {
                console.log('DEBUG: In loadCommandes...');
                var d = $q.defer();
                Commande.getByAffaire({id: id}, function(result) {
                        $scope.commandes = result ;
                        d.resolve(result);
                });
                return d.promise;
        };
        $scope.loadBonFactures = function(id) {
                console.log('DEBUG: In loadBonFactures...');
                var d = $q.defer();
                Bonfacture.getByAffaire({id: id}, function(result) {
                        $scope.bonfactures = result ;
                        d.resolve(result);
                });
                return d.promise;
        };
        $scope.loadInterventions = function(id) {
                console.log('DEBUG: In loadInterventions...');
                var d = $q.defer();
                Intervention.getByAffaire({id: id}, function(result) {
                        $scope.interventions = result ;
                        d.resolve(result);
                });
                return d.promise;
        };
        $scope.loadAchats = function(id) {
                console.log('DEBUG: In loadAchats...');
                var d = $q.defer();
                Achat.getByAffaire({id: id}, function(result) {
                        $scope.achats = result ;
                        d.resolve(result);
                });
                return d.promise;
        };
        $scope.loadMontantCorriges = function(id) {
                console.log('DEBUG: In loadMontantCorrige...');
                var d = $q.defer();
                Montantcorrige.getByAffaire({id: id}, function(result) {
                        $scope.montantcorriges = result ;
                        console.log('DEBUG: result of load : '+ result);
                        d.resolve(result);
                });
                return d.promise;
        };

        //A
        $scope.calculCommandes = function(){
                console.log('DEBUG: In calculCommandes()');
                //CALCUL
                $scope.tauxHoraire = 0;
                $scope.totalCommandes= 0;
                $scope.totalNbreHeureCommandes = 0;
                $scope.totalAchatsCommandes = 0;
                for(var i= 0; i < $scope.commandes.length; i++){
                        $scope.totalCommandes = $scope.totalCommandes + $scope.commandes[i].montant ;
                        $scope.totalNbreHeureCommandes = $scope.totalNbreHeureCommandes + $scope.commandes[i].nbreHeure ;
                        $scope.totalAchatsCommandes = $scope.totalAchatsCommandes + $scope.commandes[i].montantAchat ;
                }
                $scope.tauxHoraire = $scope.totalCommandes / $scope.totalNbreHeureCommandes ;
                
                if( $scope.affaire.tauxHoraire === null || $scope.tauxHoraire != $scope.affaire.tauxHoraire){
                        $scope.affaire.tauxHoraire = $scope.tauxHoraire;
                        //PUT AFFAIRE
                        if($scope.affaire.dateDebut!=null){
                                $scope.affaire.dateDebut.setDate($scope.affaire.dateDebut.getDate()+1);
                        }
                        if($scope.affaire.dateFin!=null){
                                $scope.affaire.dateFin.setDate($scope.affaire.dateFin.getDate()+1);
                        }
                        Affaire.update($scope.affaire,
                                function() {
                                        //LOAD AFFAIRE
                                        $scope.loadAffaire($stateParams.id);
                                });
                }else{console.log('Taux Moyen non updaté');}
        };
        
        $scope.updateScopeCommande = function(id){
                console.log('DEBUG: In updateScopeCommande()');
                $scope.loadCommandes(id)
                        .then(function(){$scope.calculCommandes();});
        };

        //C
        var actualMonth = new Date();
        actualMonth.setDate(28);
        var endMonth = null;
        $scope.endAffaire = false;
        $scope.dates = [];
        var init = true;

        $scope.calculInit = function(){
                console.log('DEBUG: C In calculInit');
                var d = $q.defer();
                
                //HOW MUCH MONTH
                $scope.endAffaire = false;
                console.log('$scope.affaire.dateFin='+$scope.affaire.dateFin);
                if (!$scope.affaire.dateFin){
                        endMonth = actualMonth.getMonth() ;
                }else{
                        $scope.endAffaire = true;
                        endMonth = $scope.affaire.dateFin.getMonth()  ;
                        console.log('$scope.affaire.dateFin='+$scope.affaire.dateFin);
                        console.log('endMonth : '+endMonth);
                } 
                
                var count = 0;
                var nbreUndefinedMT = 0;
                var nbreUndefinedBF = 0;
                console.log('$scope.affaire.dateDebut='+$scope.affaire.dateDebut);
                for(var i = 0; i < endMonth - $scope.affaire.dateDebut.getMonth() + 1 ; i++){
                        var date = new Date(new Date(actualMonth.getFullYear(), $scope.affaire.dateDebut.getMonth() +i , 15));
                        $scope.dates[i] = date ;
                        console.log('DEBUG: date='+date);
                        if($scope.montantcorriges[i] === undefined) nbreUndefinedMT++;
                        if($scope.bonfactures[i] === undefined) nbreUndefinedBF++;
                }
                console.log('nbreUndefinedMT :'+nbreUndefinedMT);
                console.log('nbreUndefinedBF :'+nbreUndefinedBF);
                
                //CREATING DATES TABLE AND INIT NEW MONTH WITH MTCORR & BF AT 0
                for(var i = 0; i < endMonth - $scope.affaire.dateDebut.getMonth() + 1 ; i++){
                        //init correction
                        if($scope.montantcorriges[i] === undefined)	{
                            
                                console.log('DEBUG: montantcorriges['+i+'] is undifined');
                                $scope.montantcorriges[i] = { montantInterCorr : 0, montantAchatCorr : 0, date : $scope.dates[i], id : null, affaire : $scope.affaire};
                                Montantcorrige.update($scope.montantcorriges[i], function(){
                                        console.log('DEBUG: $scope.montantcorriges updated  and reload set to true ');
                                        count++;
                                });
                                reload = true;
                                
                        }
                        if($scope.bonfactures[i] === undefined)	{
                                console.log('DEBUG: bonfactures['+i+'] is undifined');
                                $scope.bonfactures[i] = {nom : "",  montant: 0, date: $scope.dates[i], id: null, affaire : $scope.affaire  };
                                Bonfacture.update($scope.bonfactures[i], function(){
                                        console.log('DEBUG: $scope.bonfactures updated  ');
                                        count++;
                                });
                        }
                }
                if(reload){
                                $window.location.reload();
                            }
                if (count >  nbreUndefinedMT+nbreUndefinedBF+1 ) d.resolve();
                return d.promise;
        };

        //B
        var totalInterv = 0;
        var totalAchat = 0;
        var CoutInterv = 0;
        var totalBonFactures = 0;
        var CoutDeplacement = 0;
        var CoutAchat = 0;
        $scope.coutAffaire = 0;
        $scope.benefice = 0;
        $scope.caMois = {};
        $scope.montantEnCours = {};
        
        $scope.calcul = function(){
                console.log('DEBUG: B In calcul');
                
                for(var i = 0; i < endMonth - $scope.affaire.dateDebut.getMonth() + 1 ; i++){
                            
                        //init CAmois
                        $scope.caMois[i] = {montant: 0, date: $scope.dates[i]};
                        
                        totalInterv = 0;
                        for(var j=0 ; j < $scope.interventions.length; j++){
                                if($scope.shouldHide( $scope.dates[i], $scope.interventions[j].date)){
                                        totalInterv += $scope.interventions[j].montant;
                                        console.log('DEBUG: j='+j+' || totalInterv = '+totalInterv);
                                }
                        }
                        
                        totalAchat = 0;
                        for(var j=0; j<$scope.achats.length; j++){
                                if($scope.shouldHide($scope.dates[i],$scope.achats[j].date)){
                                        totalAchat += $scope.achats[j].montant;
                                        console.log('DEBUG: j='+j+' || totalAchat = '+totalAchat);
                                }
                        }
                        
                        totalBonFactures = 0;
                        for(var j=0; j<$scope.bonfactures.length; j++){
                                if($scope.shouldHide($scope.dates[i],$scope.bonfactures[j].date)){
                                        totalBonFactures += $scope.bonfactures[j].montant;
                                        console.log('DEBUG: j='+j+' || totalBonFactures = '+totalBonFactures);
                                }
                        }
                        
                        
                        //calculCAMoi
                        for(var j=0; j < $scope.montantcorriges.length ; j++) {
                        console.log('DEBUG: j='+j+' $scope.montantcorriges['+j+'].date = '+$scope.montantcorriges[j].date);
                                if($scope.shouldHide($scope.dates[i],$scope.montantcorriges[j].date)){
                                        console.log('DEBUG: date = $scope.montantcorriges['+j+'].date');
                                        $scope.caMois[i].montant = totalInterv +  $scope.montantcorriges[j].montantInterCorr + totalAchat + $scope.montantcorriges[j].montantAchatCorr;
                                        console.log('DEBUG: j='+j+' || $scope.caMois['+i+'].montant = '+$scope.caMois[i].montant);
                                        console.log('DEBUG: j='+j+' || $scope.caMois['+i+'].date = '+$scope.caMois[i].date);
                                        break;
                                }
                        }
                        
                
                        //calculEncours
                        for(var j=0 ; j < $scope.bonfactures.length ; j++ ) {
                                console.log('DEBUG: j='+j+' $scope.bonfactures['+j+'].date = '+$scope.bonfactures[j].date);
                                if($scope.shouldHide($scope.dates[i],$scope.bonfactures[j].date)){
                                        console.log($scope.bonfactures[j].date);
                                        if(i === 0){
                                                var enCourMois = $scope.caMois[i].montant - $scope.bonfactures[j].montant ;
                                                $scope.montantEnCours[i] = {montant: enCourMois , date: $scope.dates[i]};
                                                console.log('DEBUG: $scope.montantEnCours['+i+'].montant = '+$scope.montantEnCours[i].montant);
                                                console.log('DEBUG: $scope.montantEnCours['+i+'].date = '+$scope.montantEnCours[i].date);
                                                break;
                                        }else{
                                                var enCourMois =  $scope.caMois[i].montant - $scope.bonfactures[j].montant + $scope.montantEnCours[i-1].montant ;
                                                $scope.montantEnCours[i] = {montant: enCourMois , date: $scope.dates[i]};
                                                console.log('DEBUG: $scope.montantEnCours['+i+'].montant = '+$scope.montantEnCours[i].montant);
                                                console.log('DEBUG: $scope.montantEnCours['+i+'].date = '+$scope.montantEnCours[i].date);
                                                break;
                                        }
                                }	
                        } 
                        
                        //calcul coût affaire & benef
                        $scope.coutAffaire = 0;
                        $scope.benefice = 0;
                        CoutInterv = 0;
                        CoutDeplacement=0;
                        for(var j=0 ; j < $scope.interventions.length; j++){
                                if($scope.interventions[j].salarie.coutRevient) {
                                        CoutInterv += $scope.interventions[j].nbreHeure * $scope.interventions[j].salarie.coutRevient;
                                        CoutDeplacement += $scope.interventions[j].montantDeplacement;
                                }else{
                                        alert('Vous n\'avez pas indiquez de Coût de Revient pour le salarié '+ $scope.interventions[j].salarie.nom +' '+$scope.interventions[j].salarie.prenom+' !');
                                }
                        }
                        
                        
                        CoutAchat = 0;
                        for(var j=0; j<$scope.achats.length; j++){
                                        CoutAchat += $scope.achats[j].montant;
                        }
                        
                        for(var j=0; j < $scope.montantcorriges.length ; j++) {
                                if($scope.shouldHide($scope.dates[i],$scope.montantcorriges[j].date)){
                                        CoutInterv += $scope.montantcorriges[j].montantInterCorr ;
                                        CoutAchat += $scope.montantcorriges[j].montantAchatCorr;
                                }
                        }
                        
                        $scope.coutAffaire = CoutAchat +  CoutInterv + CoutDeplacement ;
                        $scope.benefice = totalBonFactures - $scope.coutAffaire ;
                        
                        
                }

              
            
        };
        
        $scope.init = function(id) {
                console.log('DEBUG: In init()');
                $scope.updateScopeCommande(id);
                $q.all([
                        $scope.loadAffaire(id),
                        $scope.loadMontantCorriges(id),
                        $scope.loadBonFactures(id)
                ])
                .then(function(){
                        $scope.calculInit();})
                .then(function(){
                        console.log('DEBUG: waiting for $q.all()');
                        $q.all([
                                $scope.loadInterventions(id),
                                $scope.loadAchats(id)
                        ])
                        .then(function(){ 
                            $scope.calcul();
                            
                        });
                });
        };



        $scope.updateInterventions = function (id) {
                $scope.loadInterventions(id)
                        .then(function(){$scope.calcul();});
        };

        $scope.updateAchats = function (id) {
                $scope.loadAchats(id)
                        .then(function(){$scope.calcul();});
        };

        $scope.updateMontantCorriges = function (id) {
                $scope.loadMontantCorriges(id)
                        .then(function(){$scope.calcul();});
        };

        $scope.updateBonFactures = function (id) {
                $scope.loadBonFactures(id)
                        .then(function(){$scope.calcul();});
        };

        $scope.init($stateParams.id);
        //---------------------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------------------
        //commande créer, éditer, supprimmer
        $scope.createCommande = function () {
                $scope.commande.affaire= $scope.affaire;
                Commande.update($scope.commande,
                        function () {
                            $scope.updateScopeCommande($stateParams.id);
                            $('#saveCommandeModal').modal('hide');
                            $scope.clearCommande();
                        });
                };

        $scope.updateCommande = function (id) {
                        editCommande = true;
            Commande.get({id: id}, function(result) {
                $scope.commande = result;
                $('#saveCommandeModal').modal('show');
            });
        };

        $scope.deleteCommande = function (id) {
            Commande.get({id: id}, function(result) {
                $scope.commande = result;
                $('#deleteCommandeConfirmation').modal('show');
            });
        };

        $scope.confirmDeleteCommande = function (id) {
            Commande.delete({id: id},
                function () {
                    $scope.updateScopeCommande($stateParams.id);
                    $('#deleteCommandeConfirmation').modal('hide');
                    $scope.clearCommande();
                });
        };

        $scope.clearCommande = function () {
            $scope.commande = {codeCommande: null, montant: null, montantAchat: null, nbreHeure: null, date: null, id: null};
                        editCommande = false;
            $scope.editFormCommande.$setPristine();
            $scope.editFormCommande.$setUntouched();
        };

        $scope.$watchGroup(['commande.montant','commande.nbreHeure'], function(newValue, oldValue) {
                if( $scope.commande != undefined ){
                        var totComm = $scope.totalCommandes;
                        var totNbreH = $scope.totalNbreHeureCommandes;
                        if(editCommande){
                                for(var i=0;i<= $scope.commandes.length; i++) {
                                        if($scope.commandes[i].id === $scope.commande.id){
                                                commandeEditee = $scope.commandes[i];
                                                break;
                                        }
                                }
                                totComm = totComm - commandeEditee.montant;
                                totNbreH = totNbreH - commandeEditee.nbreHeure;
                        }
                        totComm = totComm + $scope.commande.montant;
                        totNbreH = totNbreH + $scope.commande.nbreHeure;
                        $scope.tauxHoraire = totComm / totNbreH ;
                        
                        
                }
        });
        //---------------------------------------------------------------------------------------------------
            
        //---------------------------------------------------------------------------------------------------
        //intervention editer 

        $scope.createIntervention = function () {
                console.log('in create $scope.intervention.date:' +$scope.intervention.date);
                Intervention.update($scope.intervention,
                    function () {
                        $scope.updateInterventions($stateParams.id);
                        $('#saveInterventionModal').modal('hide');
                        $scope.clearIntervention();
                    });
            };

        $scope.updateIntervention = function (id) {
            Intervention.get({id: id}, function(result) {
                $scope.intervention = result;
                                if($scope.intervention.tauxHoraire === null){
                                        $scope.intervention.tauxHoraire = $scope.affaire.tauxHoraire;
                                }
                                $('#saveInterventionModal').modal('show');
            });
        };

        $scope.clearIntervention = function () {
            $scope.intervention = { nbreHeure: null, tauxHoraire : null , nbreJour: null, montant: null, montantDeplacement: null, date: null, id: null};
            $scope.editFormInterv.$setPristine();
            $scope.editFormInterv.$setUntouched();
        };
                    
        //---------------------------------------------------------------------------------------------------
        // watch for Intervention Form
        
        //calcul montant déplacement
        $scope.$watchGroup(['intervention.affaire.id','intervention.nbreJour','intervention.typeintervention.id'], function(newValue, oldValue) {
                if( $scope.intervention != undefined && $scope.intervention.affaire != undefined && $scope.intervention.typeintervention != undefined){
                        if($scope.intervention.typeintervention.id === 3){
                                $scope.intervention.montantDeplacement = $scope.affaire.client.distance.bareme * $scope.intervention.nbreJour ;
                                console.log($scope.affaire.client.distance.bareme+' * '+$scope.intervention.nbreJour);
                                console.log($scope.intervention.montantDeplacement);
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
        $scope.$watchGroup(['intervention.nbreHeure','intervention.tauxHoraire'], function(newValue, oldValue) {
                if( $scope.intervention != undefined ){
                        $scope.intervention.montant = $scope.intervention.tauxHoraire * $scope.intervention.nbreHeure ;
                        console.log('in watchGroup '+$scope.intervention.tauxHoraire +'*'+ $scope.intervention.nbreHeure);
                        console.log($scope.intervention.montant);
                }
        });
        
        
        //---------------------------------------------------------------------------------------------------
        //---------------------------------------------------------------------------------------------------
        
        //---------------------------------------------------------------------------------------------------
        //achat créer, éditer, supprimmer
		
        $scope.createAchat = function () {
                $scope.achat.affaire = $scope.affaire;
                $scope.achat.description = null;
                Achat.update($scope.achat,
                function () {
                    $scope.updateAchats($stateParams.id);
                    $('#saveAchatModal').modal('hide');
                    $scope.clearAchat();
                });
        }; 

        $scope.updateAchat = function (id) {
            Achat.get({id: id}, function(result) {
                $scope.achat = result;
                $('#saveAchatModal').modal('show');
            });
        };

        $scope.deleteAchat = function (id) {
            Achat.get({id: id}, function(result) {
                $scope.achat = result;
                $('#deleteAchatConfirmation').modal('show');
            });
        };

        $scope.confirmDeleteAchat = function (id) {
            Achat.delete({id: id},
                function () {
                    $scope.updateAchats($stateParams.id);
                    $('#deleteAchatConfirmation').modal('hide');
                    $scope.clearAchat();
                });
        };

        $scope.setDateAndClearAchat = function(actualMonthDate) {
                $scope.clearAchat();
                $scope.achat.date = actualMonthDate;
        };
            
        $scope.clearAchat = function () {
            $scope.achat = {id: null , nom: null, description : null, date: null, montant: null, nbreHeure : null, tauxHoraire : null};
			$scope.AchatNbreHeureDisabled = true;
            $scope.editFormAchat.$setPristine();
            $scope.editFormAchat.$setUntouched();
        };
		
        $scope.$watch('achat.typeachat.id', function(newValue, oldValue) {
                if($scope.achat != undefined && $scope.achat.typeachat != undefined){
                        console.log($scope.achat.typeachat.id);
                        if($scope.achat.typeachat.id === 3 || $scope.achat.typeachat.id === 4 ) {
                                $scope.AchatNbreHeureDisabled = false;
                        }
                        else{
                                $scope.AchatNbreHeureDisabled = true;
                        }
                }
        });
        
        //calcul montant
        $scope.$watchGroup(['achat.nbreHeure','achat.tauxHoraire'], function(newValue, oldValue) {
                if( $scope.achat != undefined && $scope.achat.typeachat != undefined ){
                        if($scope.achat.typeachat.id === 3 || $scope.achat.typeachat.id === 4 ) {
                                $scope.achat.montant = $scope.achat.tauxHoraire * $scope.achat.nbreHeure ;
                                console.log('in watchGroup '+$scope.achat.tauxHoraire +'*'+ $scope.achat.nbreHeure);
                                console.log($scope.achat.montant);
                        }
                }
        });
                
        //---------------------------------------------------------------------------------------------------
		
        //---------------------------------------------------------------------------------------------------
        //PUT montantcorrige and bonfacture
        $scope.updateMontantCorrige = function(id) {
                console.log('in updateMontantCorrige');
                for(var i=0; i < $scope.montantcorriges.length ; i++) {
                        if( id=== $scope.montantcorriges[i].id ) {
                                console.log($scope.montantcorriges[i]);
                                Montantcorrige.update($scope.montantcorriges[i], function() {
                                        $scope.updateMontantCorriges($stateParams.id);
                                });
                                break;
                        }
                }
        };
        
        $scope.createBonFacture = function () {
            Bonfacture.update($scope.bonfacture,
                function () {
                    $scope.updateBonFactures($stateParams.id);
                $('#saveBonfactureModal').modal('hide');
                    $scope.clearBonFacture();
                });
        }; 
        
        $scope.updateBonFacture = function (id) {
            Bonfacture.get({id: id}, function(result) {
                                $scope.bonfacture = result;
            $('#saveBonfactureModal').modal('show');
            });
        };
        
        $scope.clearBonFacture = function () {
            $scope.bonfacture.nom = null;
            $scope.editFormBonFact.$setPristine();
            $scope.editFormBonFact.$setUntouched();
        };
        
        
        //---------------------------------------------------------------------------------------------------
		
        $scope.shouldHide =function(actualMonthDate,dateIntervention){
                var dateInterventionDate = dateIntervention.split("-");
                var actualMonth = parseInt(actualMonthDate.getMonth(),10) + 1;
                var hide = parseInt(dateInterventionDate[1],10) === actualMonth ? true : false;
                return hide;
        }
        
        $scope.shouldHideInterne =function(actualMonthDate,dateIntervention){
                var hide = dateIntervention.getMonth() === actualMonthDate.getMonth() ? true : false;
                return hide;
        }
        
        $scope.cloturerAffaire = function() {
                $scope.affaire.dateFin = actualMonth ;
                console.log($scope.affaire);
                Affaire.update($scope.affaire,
                        function() {
                                $scope.init($stateParams.id);
                        });
        };
        
        $scope.reouvrirAffaire = function() {
                $scope.affaire.dateFin = null ;
                Affaire.update($scope.affaire,
                        function() {
                                $scope.init($stateParams.id);
                        });
        };
        
        $scope.nbreInterByMonth = function(date){
                var nbreIntervThisMonth =0;
                for(var i=0; i < $scope.interventions.length ; i++){
                        if($scope.shouldHide(date,$scope.interventions[i].date)){
                                nbreIntervThisMonth++;
                        }
                }
                return nbreIntervThisMonth;
        };
        
        $scope.totalNbreHeure = function(){
                var totalNbreHeure =0;
                for(var i=0; i < $scope.interventions.length ; i++){
                        totalNbreHeure += $scope.interventions[i].nbreHeure ;
                }
                return totalNbreHeure;
        };
        
        $scope.totalMontantInterv = function(){
                var totalmontant =0;
                for(var i=0; i < $scope.interventions.length ; i++){
                        totalmontant += $scope.interventions[i].montant ;
                }
                return totalmontant;
        };
        
        $scope.totalBonFacture = function(){
                var totalmontant =0;
                for(var i=0; i < $scope.bonfactures.length ; i++){
                        totalmontant += $scope.bonfactures[i].montant ;
                }
                return totalmontant;
        };
        
        $scope.exportToExcel=function(tableId){ 
                var dt = new Date();
                var day = dt.getDate();
                var month = dt.getMonth() + 1;
                var year = dt.getFullYear();
                var hour = dt.getHours();
                var mins = dt.getMinutes();
                var postfix = day + "." + month + "." + year + "_" + hour + "." + mins;
    var exportHref=Excel.tableToExcel(tableId,'sheet name');
                //creating a temporary HTML link element (they support setting file names)
                var a = document.createElement('a');
                
                a.href = exportHref;
                //setting the file name
                a.download = 'Affaire_'+$scope.affaire.codeAffaire+'_' + postfix + '.xls';
                //triggering the function
                a.click();
                };
        
        $scope.exportToWord = function(){
                //var converted = htmlDocx.asBlob(contentId);
                //saveAs(converted, 'test.docx');
                var loadFile=function(url,callback){
                        JSZipUtils.getBinaryContent(url,callback);
                };
                loadFile("assets/doc/template_bon_livraison.docx",function(err,content){
                        var dt = new Date();
                        var day = dt.getDate();
                        var month = dt.getMonth() + 1;
                        var year = dt.getFullYear();
                        var hour = dt.getHours();
                        var mins = dt.getMinutes();
                        var postfix = day + "." + month + "." + year + "_" + hour + "." + mins;
                        if (err) { throw e};
                        var doc = new Docxgen(content);
                        doc.setData( {
                                "affaire.client.nom":$scope.affaire.client.nom,
                                "affaire.client.adresse":$scope.affaire.client.adresse,
                                "affaire.respAffaire":$scope.affaire.respAffaire,
                                "affaire.codeAffaire":$scope.affaire.codeAffaire,
                                "affaire.respClient":$scope.affaire.respClient,
                                "commande.montant": $scope.totalCommandes,
                                "date": day + "/" + month + "/" + year
                                
                                }
                        );//set the templateVariables
                        doc.render(); //apply them (replace all occurences of {first_name} by Hipp, ...)
                        var out = doc.getZip().generate({type:"blob"}); //Output the document using Data-URI
                        saveAs(out,"BL_"+$scope.affaire.codeAffaire+"_"+$scope.affaire.client.nom+"_"+postfix+".docx");
                });
        };	
        
});
