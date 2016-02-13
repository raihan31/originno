'use strict';

angular.module('originnoApp')
    .factory('ProPromotionPhoto', function ($resource, DateUtils) {
        return $resource('api/proPromotionPhotos/:id', {}, {
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
