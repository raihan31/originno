'use strict';

angular.module('originnoApp').controller('ProductsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Products', 'Category',
        function($scope, $stateParams, $modalInstance, entity, Products, Category) {

        $scope.products = entity;
        $scope.categorys = Category.query();
        $scope.load = function(id) {
            Products.get({id : id}, function(result) {
                $scope.products = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('originnoApp:productsUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.products.id != null) {
                Products.update($scope.products, onSaveFinished);
            } else {
                Products.save($scope.products, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
