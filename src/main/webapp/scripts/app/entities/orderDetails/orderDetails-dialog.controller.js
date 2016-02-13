'use strict';

angular.module('originnoApp').controller('OrderDetailsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'OrderDetails', 'Orders', 'Products',
        function($scope, $stateParams, $modalInstance, entity, OrderDetails, Orders, Products) {

        $scope.orderDetails = entity;
        $scope.orderss = Orders.query();
        $scope.productss = Products.query();
        $scope.load = function(id) {
            OrderDetails.get({id : id}, function(result) {
                $scope.orderDetails = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:orderDetailsUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.orderDetails.id != null) {
                OrderDetails.update($scope.orderDetails, onSaveFinished);
            } else {
                OrderDetails.save($scope.orderDetails, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
