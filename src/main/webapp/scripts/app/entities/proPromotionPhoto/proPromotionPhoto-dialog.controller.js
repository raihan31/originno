'use strict';

angular.module('originnoApp').controller('ProPromotionPhotoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ProPromotionPhoto', 'Products',
        function($scope, $stateParams, $modalInstance, entity, ProPromotionPhoto, Products) {

        $scope.proPromotionPhoto = entity;
        $scope.productss = Products.query();
        $scope.load = function(id) {
            ProPromotionPhoto.get({id : id}, function(result) {
                $scope.proPromotionPhoto = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:proPromotionPhotoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.proPromotionPhoto.id != null) {
                ProPromotionPhoto.update($scope.proPromotionPhoto, onSaveFinished);
            } else {
                ProPromotionPhoto.save($scope.proPromotionPhoto, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
