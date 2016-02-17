'use strict';

angular.module('appApp')
    .controller('StatussalaireController', function ($scope, Statussalaire, Salarie) {
        $scope.statussalaires = [];
        $scope.salaries = Salarie.query();
        $scope.loadAll = function() {
            Statussalaire.query(function(result) {
               $scope.statussalaires = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Statussalaire.update($scope.statussalaire,
                function () {
                    $scope.loadAll();
                    $('#saveStatussalaireModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Statussalaire.get({id: id}, function(result) {
                $scope.statussalaire = result;
                $('#saveStatussalaireModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Statussalaire.get({id: id}, function(result) {
                $scope.statussalaire = result;
                $('#deleteStatussalaireConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Statussalaire.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStatussalaireConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.statussalaire = {status: null, id: null};
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
