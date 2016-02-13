'use strict';

angular.module('originnoApp')
    .controller('DepartmentController', function ($scope, Department, DepartmentSearch, ParseLinks) {
        $scope.departments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Department.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.departments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Department.get({id: id}, function(result) {
                $scope.department = result;
                $('#deleteDepartmentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Department.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDepartmentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DepartmentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.departments = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.department = {
                name: null,
                logo: null,
                descriptions: null,
                createdAt: null,
                updatedAt: null,
                id: null
            };
        };
    });
