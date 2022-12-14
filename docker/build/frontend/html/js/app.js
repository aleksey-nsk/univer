const contextPath = 'http://localhost:8086/api/v1'; // prod-профиль
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
