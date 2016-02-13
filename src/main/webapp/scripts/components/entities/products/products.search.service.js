'use strict';

angular.module('originnoApp')
    .factory('ProductsSearch', function ($resource) {
        return $resource('api/_search/productss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
