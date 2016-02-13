'use strict';

angular.module('originnoApp').controller('DeptPromotionPhotoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DeptPromotionPhoto', 'Department',
        function($scope, $stateParams, $modalInstance, entity, DeptPromotionPhoto, Department) {

        $scope.deptPromotionPhoto = entity;
        $scope.departments = Department.query();
        $scope.load = function(id) {
            DeptPromotionPhoto.get({id : id}, function(result) {
                $scope.deptPromotionPhoto = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:deptPromotionPhotoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.deptPromotionPhoto.id != null) {
                DeptPromotionPhoto.update($scope.deptPromotionPhoto, onSaveFinished);
            } else {
                DeptPromotionPhoto.save($scope.deptPromotionPhoto, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
