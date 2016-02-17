'use strict';

angular.module('appApp')
    .controller('PointageController', function ($scope, Pointage, Typepointage, Salarie, ParseLinks) {
        $scope.pointages = [];
        $scope.typepointages = Typepointage.query();
        $scope.salaries = Salarie.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Pointage.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pointages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Pointage.update($scope.pointage,
                function () {
                    $scope.loadAll();
                    $('#savePointageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Pointage.get({id: id}, function(result) {
                $scope.pointage = result;
                $('#savePointageModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Pointage.get({id: id}, function(result) {
                $scope.pointage = result;
                $('#deletePointageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Pointage.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePointageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.pointage = {nbreHeure: null, date: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
