'use strict';

angular.module('originnoApp').controller('ProfileDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Profile', 'User',
        function($scope, $stateParams, $modalInstance, $q, entity, Profile, User) {

        $scope.profile = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Profile.get({id : id}, function(result) {
                $scope.profile = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:profileUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.profile.id != null) {
                Profile.update($scope.profile, onSaveFinished);
            } else {
                Profile.save($scope.profile, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
