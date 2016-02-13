'use strict';

angular.module('originnoApp')
    .controller('DeptPromotionPhotoController', function ($scope, DeptPromotionPhoto, DeptPromotionPhotoSearch, ParseLinks) {
        $scope.deptPromotionPhotos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DeptPromotionPhoto.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.deptPromotionPhotos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DeptPromotionPhoto.get({id: id}, function(result) {
                $scope.deptPromotionPhoto = result;
                $('#deleteDeptPromotionPhotoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DeptPromotionPhoto.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDeptPromotionPhotoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DeptPromotionPhotoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.deptPromotionPhotos = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.deptPromotionPhoto = {
                name: null,
                photo: null,
                descriptions: null,
                id: null
            };
        };
    });
