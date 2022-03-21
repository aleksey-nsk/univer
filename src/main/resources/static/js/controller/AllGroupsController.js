universityApp.controller('AllGroupsController', function ($scope, $http) {

    $scope.logout = function () {
        const url = '/logout';
        console.log("Method logout(), url: " + url);
        $http.get(url);
    };

    $scope.fillGroupTable = function () {
        const url = contextPath + '/group';
        console.log("Method fillGroupTable(), url: " + url);
        $http.get(url)
                .then(function (resp) {
                    $scope.Groups = resp.data;
                });
    };

    $scope.saveGroup = function () {
        const url = contextPath + '/group';
        console.log("Method saveGroup(), url: " + url);
        console.log($scope.NewGroup);

        if ($scope.NewGroup === undefined) {
            $scope.NewGroup = {}; // пустой объект
            console.log($scope.NewGroup)
        }

        $http.post(url, $scope.NewGroup)
                .then(function (resp) {
                    $scope.NewGroup = null;
                    $scope.fillGroupTable();
                });
    };

    $scope.fillGroupTable();

});
