angular.module('app').controller('accountDetailController', function($scope, $routeParams, loginService, accountService) {

    $scope.alerts = [];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.loggedInId = '';
    $scope.loggedInName = '';
    loginService.getLoggedInUser().then(function(result) {
        $scope.loggedInId = result.data[0].id;
        $scope.loggedInName = result.data[0].name;
    });

    $scope.account = {};
    if($routeParams.id) {
        accountService.getAccount($routeParams.id).then(function (data) {
            $scope.account = data;
        },
        function(result){
            $scope.alerts.push({type: 'danger', msg: result.data });
        });
    }
});