'use strict';

angular.module('originnoApp')
    .factory('ProfileSearch', function ($resource) {
        return $resource('api/_search/profiles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
