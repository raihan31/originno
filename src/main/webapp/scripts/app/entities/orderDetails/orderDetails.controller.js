'use strict';

angular.module('originnoApp')
    .controller('OrderDetailsController', function ($scope, OrderDetails, OrderDetailsSearch, ParseLinks) {
        $scope.orderDetailss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            OrderDetails.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderDetailss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            OrderDetails.get({id: id}, function(result) {
                $scope.orderDetails = result;
                $('#deleteOrderDetailsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            OrderDetailsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.orderDetailss = result;
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
            $scope.orderDetails = {
                noOfProducts: null,
                id: null
            };
        };
    });
