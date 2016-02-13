'use strict';

angular.module('originnoApp').controller('OrdersDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Orders', 'User',
        function($scope, $stateParams, $modalInstance, entity, Orders, User) {

        $scope.orders = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Orders.get({id : id}, function(result) {
                $scope.orders = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:ordersUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.orders.id != null) {
                Orders.update($scope.orders, onSaveFinished);
            } else {
                Orders.save($scope.orders, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
