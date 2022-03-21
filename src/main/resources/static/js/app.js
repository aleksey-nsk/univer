const contextPath = 'http://localhost:8084/api/v1'; // dev-профиль
console.log("contextPath: " + contextPath);

var universityApp = angular.module('universityApp', ["ngRoute"]).config(function ($routeProvider) {

    $routeProvider.when('/all', {
        templateUrl: 'view/allGroups.html',
        controller: 'AllGroupsController'
    });

    $routeProvider.when('/one/:id', {
        templateUrl: 'view/oneGroup.html',
        controller: 'OneGroupController'
    });

    $routeProvider.otherwise({
        redirectTo: '/all'
    });

});
