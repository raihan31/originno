'use strict';

angular.module('originnoApp')
    .factory('CatPromotionPhotoSearch', function ($resource) {
        return $resource('api/_search/catPromotionPhotos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
