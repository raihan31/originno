'use strict';

angular.module('originnoApp')
    .controller('ProPromotionPhotoController', function ($scope, ProPromotionPhoto, ProPromotionPhotoSearch, ParseLinks) {
        $scope.proPromotionPhotos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ProPromotionPhoto.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.proPromotionPhotos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ProPromotionPhoto.get({id: id}, function(result) {
                $scope.proPromotionPhoto = result;
                $('#deleteProPromotionPhotoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ProPromotionPhoto.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProPromotionPhotoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ProPromotionPhotoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.proPromotionPhotos = result;
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
            $scope.proPromotionPhoto = {
                name: null,
                photo: null,
                descriptions: null,
                id: null
            };
        };
    });
