universityApp.controller('OneGroupController', function ($scope, $http, $routeParams) {

    $scope.$on("$routeChangeSuccess", function () {
        var groupId = $routeParams["id"];
        $scope.fillOneGroup(groupId);
    });

    $scope.fillOneGroup = function (groupId) {
        const url = contextPath + '/group/' + groupId;
        console.log("Method fillOneGroup(), url: " + url);
        $http.get(url)
                .then(function (resp) {
                    $scope.OneGroup = resp.data;
                });
    };

    $scope.saveStudent = function (groupId) {
        const url = contextPath + '/student/' + groupId;
        console.log("Method saveStudent(), url: " + url);
        console.log($scope.NewStudent);

        if ($scope.NewStudent === undefined) {
            $scope.NewStudent = {}; // пустой объект
            console.log($scope.NewStudent)
        }

        $http.post(url, $scope.NewStudent)
                .then(function (resp) {
                    $scope.NewStudent = null;
                    $scope.fillOneGroup(groupId);
                });
    };

    $scope.deleteStudent = function (studentId, groupId) {
        const url = contextPath + '/student/' + studentId;
        console.log("Method deleteStudent(), url: " + url);
        $http.delete(url)
                .then(function (resp) {
                    $scope.fillOneGroup(groupId);
                });
    };

});
