'use strict';

angular.module('originnoApp')
    .factory('OrdersSearch', function ($resource) {
        return $resource('api/_search/orderss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
