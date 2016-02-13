'use strict';

angular.module('originnoApp')
    .controller('CatPromotionPhotoController', function ($scope, CatPromotionPhoto, CatPromotionPhotoSearch, ParseLinks) {
        $scope.catPromotionPhotos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CatPromotionPhoto.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.catPromotionPhotos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            CatPromotionPhoto.get({id: id}, function(result) {
                $scope.catPromotionPhoto = result;
                $('#deleteCatPromotionPhotoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CatPromotionPhoto.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCatPromotionPhotoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            CatPromotionPhotoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.catPromotionPhotos = result;
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
            $scope.catPromotionPhoto = {
                name: null,
                photo: null,
                descriptions: null,
                id: null
            };
        };
    });
