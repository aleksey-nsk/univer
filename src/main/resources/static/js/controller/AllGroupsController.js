universityApp.controller('AllGroupsController', function ($scope, $http) {

    $scope.logout = function () {
        const url = '/logout';
        console.log("Method logout(), url: " + url);
        $http.get(url);
    };

    $scope.generatePageIndexes = function (startPage, endPage) {
        console.log("Method generatePageIndexes(), startPage=" + startPage + ", endPage=" + endPage);
        let arr = [];
        for (let i = startPage; i <= endPage; i++) {
            arr.push(i);
        }
        return arr;
    };

    $scope.getGroupPage = function (pageIndex = 1) {
        const url = contextPath + '/group';
        console.log("Method getGroupPage(), url: " + url);

        $http({
            url: url,
            method: 'GET',
            params: {
                pageIndex: pageIndex
            }
        }).then(function (response) {
            $scope.GroupPage = response.data;

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.GroupPage.totalPages) {
                maxPageIndex = $scope.GroupPage.totalPages;
            }

            $scope.PaginationArray = $scope.generatePageIndexes(minPageIndex, maxPageIndex);
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
                    $scope.getGroupPage();
                });
    };

    $scope.getGroupPage();

});
