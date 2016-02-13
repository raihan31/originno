'use strict';

angular.module('originnoApp')
    .controller('CategoryDetailController', function ($scope, $rootScope, $stateParams, entity, Category, Department) {
        $scope.category = entity;
        $scope.load = function (id) {
            Category.get({id: id}, function(result) {
                $scope.category = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:categoryUpdate', function(event, result) {
            $scope.category = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
