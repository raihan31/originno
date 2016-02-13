'use strict';

angular.module('originnoApp')
    .controller('ProfileController', function ($scope, Profile, ProfileSearch, ParseLinks) {
        $scope.profiles = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Profile.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.profiles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Profile.get({id: id}, function(result) {
                $scope.profile = result;
                $('#deleteProfileConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Profile.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProfileConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ProfileSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.profiles = result;
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
            $scope.profile = {
                address: null,
                contactNo: null,
                location: null,
                photo: null,
                aboutMe: null,
                createdAt: null,
                UpdatedAt: null,
                id: null
            };
        };
    });
