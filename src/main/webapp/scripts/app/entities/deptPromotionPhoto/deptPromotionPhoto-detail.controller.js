'use strict';

angular.module('originnoApp')
    .controller('DeptPromotionPhotoDetailController', function ($scope, $rootScope, $stateParams, entity, DeptPromotionPhoto, Department) {
        $scope.deptPromotionPhoto = entity;
        $scope.load = function (id) {
            DeptPromotionPhoto.get({id: id}, function(result) {
                $scope.deptPromotionPhoto = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:deptPromotionPhotoUpdate', function(event, result) {
            $scope.deptPromotionPhoto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
