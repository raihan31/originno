'use strict';

angular.module('originnoApp')
    .controller('CatPromotionPhotoDetailController', function ($scope, $rootScope, $stateParams, entity, CatPromotionPhoto, Category) {
        $scope.catPromotionPhoto = entity;
        $scope.load = function (id) {
            CatPromotionPhoto.get({id: id}, function(result) {
                $scope.catPromotionPhoto = result;
            });
        };
        var unsubscribe = $rootScope.$on('originnoApp:catPromotionPhotoUpdate', function(event, result) {
            $scope.catPromotionPhoto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
