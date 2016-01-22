'use strict';

angular.module('originnoApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


