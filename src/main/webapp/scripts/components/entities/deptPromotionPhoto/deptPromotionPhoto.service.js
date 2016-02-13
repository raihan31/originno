'use strict';

angular.module('originnoApp')
    .factory('DeptPromotionPhoto', function ($resource, DateUtils) {
        return $resource('api/deptPromotionPhotos/:id', {}, {
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
