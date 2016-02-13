'use strict';

angular.module('originnoApp')
    .controller('ProfileDetailController', function ($scope, $rootScope, $stateParams, entity, Profile, User) {
        $scope.profile = entity;
        $scope.load = function (id) {
            Profile.get({id: id}, function(result) {
                $scope.profile = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:profileUpdate', function(event, result) {
            $scope.profile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
