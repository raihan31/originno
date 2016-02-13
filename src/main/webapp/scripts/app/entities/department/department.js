'use strict';

angular.module('originnoApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('department', {
                parent: 'entity',
                url: '/departments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'originnoApp.department.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/department/departments.html',
                        controller: 'DepartmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('department');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('department.detail', {
                parent: 'entity',
                url: '/department/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'originnoApp.department.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/department/department-detail.html',
                        controller: 'DepartmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('department');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Department', function($stateParams, Department) {
                        return Department.get({id : $stateParams.id});
                    }]
                }
            })
            .state('department.new', {
                parent: 'department',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/department/department-dialog.html',
                        controller: 'DepartmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    logo: null,
                                    descriptions: null,
                                    createdAt: null,
                                    updatedAt: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('department', null, { reload: true });
                    }, function() {
                        $state.go('department');
                    })
                }]
            })
            .state('department.edit', {
                parent: 'department',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/department/department-dialog.html',
                        controller: 'DepartmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Department', function(Department) {
                                return Department.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('department', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
