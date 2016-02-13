'use strict';

angular.module('originnoApp')
    .controller('ProPromotionPhotoDetailController', function ($scope, $rootScope, $stateParams, entity, ProPromotionPhoto, Products) {
        $scope.proPromotionPhoto = entity;
        $scope.load = function (id) {
            ProPromotionPhoto.get({id: id}, function(result) {
                $scope.proPromotionPhoto = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:proPromotionPhotoUpdate', function(event, result) {
            $scope.proPromotionPhoto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
