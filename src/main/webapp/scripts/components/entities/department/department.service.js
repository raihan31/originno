'use strict';

angular.module('originnoApp')
    .factory('Department', function ($resource, DateUtils) {
        return $resource('api/departments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createdAt = DateUtils.convertDateTimeFromServer(data.createdAt);
                    data.updatedAt = DateUtils.convertDateTimeFromServer(data.updatedAt);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
