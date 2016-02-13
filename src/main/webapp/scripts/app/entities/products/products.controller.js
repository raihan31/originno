'use strict';

angular.module('originnoApp')
    .controller('ProductsController', function ($scope, Products, ProductsSearch, ParseLinks) {
        $scope.productss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Products.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.productss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Products.get({id: id}, function(result) {
                $scope.products = result;
                $('#deleteProductsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Products.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ProductsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.productss = result;
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
            $scope.products = {
                name: null,
                logo: null,
                descriptions: null,
                measuredUnit: null,
                unitItem: null,
                price: null,
                availableItem: null,
                soldItem: null,
                status: null,
                sharedNo: null,
                createdAt: null,
                updatedAt: null,
                id: null
            };
        };
    });
