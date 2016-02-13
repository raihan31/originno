'use strict';

angular.module('originnoApp').controller('CatPromotionPhotoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CatPromotionPhoto', 'Category',
        function($scope, $stateParams, $modalInstance, entity, CatPromotionPhoto, Category) {

        $scope.catPromotionPhoto = entity;
        $scope.categorys = Category.query();
        $scope.load = function(id) {
            CatPromotionPhoto.get({id : id}, function(result) {
                $scope.catPromotionPhoto = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:catPromotionPhotoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.catPromotionPhoto.id != null) {
                CatPromotionPhoto.update($scope.catPromotionPhoto, onSaveFinished);
            } else {
                CatPromotionPhoto.save($scope.catPromotionPhoto, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
