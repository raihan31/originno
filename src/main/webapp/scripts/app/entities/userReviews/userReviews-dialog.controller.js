'use strict';

angular.module('originnoApp').controller('UserReviewsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'UserReviews', 'User', 'Products',
        function($scope, $stateParams, $modalInstance, entity, UserReviews, User, Products) {

        $scope.userReviews = entity;
        $scope.users = User.query();
        $scope.productss = Products.query();
        $scope.load = function(id) {
            UserReviews.get({id : id}, function(result) {
                $scope.userReviews = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:userReviewsUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.userReviews.id != null) {
                UserReviews.update($scope.userReviews, onSaveFinished);
            } else {
                UserReviews.save($scope.userReviews, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
