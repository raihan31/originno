'use strict';

angular.module('originnoApp')
    .factory('DeptPromotionPhotoSearch', function ($resource) {
        return $resource('api/_search/deptPromotionPhotos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
