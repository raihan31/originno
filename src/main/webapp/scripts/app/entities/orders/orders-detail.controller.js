'use strict';

angular.module('originnoApp')
    .controller('OrdersDetailController', function ($scope, $rootScope, $stateParams, entity, Orders, User) {
        $scope.orders = entity;
        $scope.load = function (id) {
            Orders.get({id: id}, function(result) {
                $scope.orders = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:ordersUpdate', function(event, result) {
            $scope.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
