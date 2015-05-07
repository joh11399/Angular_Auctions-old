var app = angular.module('app');

app.controller('loginController', function ($scope, $resource, $modalInstance) {

    setTimeout(function(){
        $('#username').focus();
    }, 50);

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});

app.controller('loginLinksController', function($scope, loginService, $location, loginDialog){

    $scope.loggedInUser = '';
    $scope.loggedInId = '';
    loginService.getLoggedInUser().then(function(result) {
        $scope.loggedInId = result.data[0].id;
        $scope.loggedInUser = result.data[0].username;
    });

    $scope.loginLink = function(){
        loginDialog();
    };

});