'use strict';

angular.module('originnoApp')
    .controller('OrdersController', function ($scope, Orders, OrdersSearch, ParseLinks) {
        $scope.orderss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Orders.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Orders.get({id: id}, function(result) {
                $scope.orders = result;
                $('#deleteOrdersConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Orders.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrdersConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            OrdersSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.orderss = result;
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
            $scope.orders = {
                descriptions: null,
                totalPrice: null,
                totalProducts: null,
                isViewed: null,
                isDelivered: null,
                isReceived: null,
                totalMoneyReceived: null,
                isPaid: null,
                receivedDocument: null,
                isCanceled: null,
                isTaken: null,
                createdAt: null,
                updatedAt: null,
                id: null
            };
        };
    });
