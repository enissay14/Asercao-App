'use strict';

angular.module('appApp')
    .controller('TypepointageController', function ($scope, Typepointage, Pointage, ParseLinks) {
        $scope.typepointages = [];
        $scope.pointages = Pointage.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Typepointage.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.typepointages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Typepointage.update($scope.typepointage,
                function () {
                    $scope.loadAll();
                    $('#saveTypepointageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Typepointage.get({id: id}, function(result) {
                $scope.typepointage = result;
                $('#saveTypepointageModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Typepointage.get({id: id}, function(result) {
                $scope.typepointage = result;
                $('#deleteTypepointageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Typepointage.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTypepointageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.typepointage = {type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
