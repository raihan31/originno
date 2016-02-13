'use strict';

angular.module('originnoApp')
    .controller('ProductsDetailController', function ($scope, $rootScope, $stateParams, entity, Products, Category) {
        $scope.products = entity;
        $scope.load = function (id) {
            Products.get({id: id}, function(result) {
                $scope.products = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:productsUpdate', function(event, result) {
            $scope.products = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
