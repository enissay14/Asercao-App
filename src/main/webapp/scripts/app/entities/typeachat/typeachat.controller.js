'use strict';

angular.module('appApp')
    .controller('TypeachatController', function ($scope, Typeachat, Achat, ParseLinks) {
        $scope.typeachats = [];
        $scope.achats = Achat.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Typeachat.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.typeachats = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Typeachat.update($scope.typeachat,
                function () {
                    $scope.loadAll();
                    $('#saveTypeachatModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Typeachat.get({id: id}, function(result) {
                $scope.typeachat = result;
                $('#saveTypeachatModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Typeachat.get({id: id}, function(result) {
                $scope.typeachat = result;
                $('#deleteTypeachatConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Typeachat.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTypeachatConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.typeachat = {type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
		
		$scope.inactif = function(id){
			var hide = false;
			if(id === 3 || id === 4){
				hide = true;
			}
			return hide;
		};
    });
