'use strict';

angular.module('originnoApp').controller('CategoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Category', 'Department',
        function($scope, $stateParams, $modalInstance, entity, Category, Department) {

        $scope.category = entity;
        $scope.departments = Department.query();
        $scope.load = function(id) {
            Category.get({id : id}, function(result) {
                $scope.category = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:categoryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.category.id != null) {
                Category.update($scope.category, onSaveFinished);
            } else {
                Category.save($scope.category, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
