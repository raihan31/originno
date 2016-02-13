'use strict';

angular.module('originnoApp')
    .factory('UserReviewsSearch', function ($resource) {
        return $resource('api/_search/userReviewss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
