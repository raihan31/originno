'use strict';

angular.module('originnoApp')
    .factory('DepartmentSearch', function ($resource) {
        return $resource('api/_search/departments/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
