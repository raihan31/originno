'use strict';

angular.module('originnoApp')
    .controller('DepartmentDetailController', function ($scope, $rootScope, $stateParams, entity, Department) {
        $scope.department = entity;
        $scope.load = function (id) {
            Department.get({id: id}, function(result) {
                $scope.department = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:departmentUpdate', function(event, result) {
            $scope.department = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
