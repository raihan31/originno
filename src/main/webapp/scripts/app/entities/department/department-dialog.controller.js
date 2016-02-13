'use strict';

angular.module('originnoApp').controller('DepartmentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Department',
        function($scope, $stateParams, $modalInstance, entity, Department) {

        $scope.department = entity;
        $scope.load = function(id) {
            Department.get({id : id}, function(result) {
                $scope.department = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:departmentUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.department.id != null) {
                Department.update($scope.department, onSaveFinished);
            } else {
                Department.save($scope.department, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
