'use strict';

angular.module('appApp')
    .controller('TypeinterventionController', function ($scope, Typeintervention, Intervention, ParseLinks) {
        $scope.typeinterventions = [];
        $scope.interventions = Intervention.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Typeintervention.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.typeinterventions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Typeintervention.update($scope.typeintervention,
                function () {
                    $scope.loadAll();
                    $('#saveTypeinterventionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Typeintervention.get({id: id}, function(result) {
                $scope.typeintervention = result;
                $('#saveTypeinterventionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Typeintervention.get({id: id}, function(result) {
                $scope.typeintervention = result;
                $('#deleteTypeinterventionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Typeintervention.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTypeinterventionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.typeintervention = {type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
