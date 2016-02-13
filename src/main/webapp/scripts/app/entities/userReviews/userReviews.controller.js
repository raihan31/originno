'use strict';

angular.module('originnoApp')
    .controller('UserReviewsController', function ($scope, UserReviews, UserReviewsSearch, ParseLinks) {
        $scope.userReviewss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UserReviews.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userReviewss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UserReviews.get({id: id}, function(result) {
                $scope.userReviews = result;
                $('#deleteUserReviewsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserReviews.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserReviewsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UserReviewsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.userReviewss = result;
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
            $scope.userReviews = {
                rating: null,
                comments: null,
                isFavourite: null,
                createdAt: null,
                updatedAt: null,
                id: null
            };
        };
    });
