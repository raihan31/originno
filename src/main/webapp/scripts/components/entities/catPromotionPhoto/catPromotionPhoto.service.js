'use strict';

angular.module('originnoApp')
    .factory('CatPromotionPhoto', function ($resource, DateUtils) {
        return $resource('api/catPromotionPhotos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
