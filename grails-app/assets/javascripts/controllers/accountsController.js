angular.module('app').controller('accountsController', function($scope, $resource) {

    $scope.alerts = [];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    /*
     Accounts = $resource('api/accounts/:id', {}, {
     save: {method: 'PUT'}
     });

     var Accounts = $resource('api/accounts/:id', {
     create: {method: "POST"},
     save: {method: "PUT"},
     find: {
     method: "GET",
     isArray: true,
     url:"api/accounts/?sort=:sort&offset=:offset",
     params:{offset:0}}
     });
     */

    var Accounts = $resource('api/accounts/');
    $scope.accounts = Accounts.query();

    /*
    var getAccountsList = function(){

    };
    getAccountsList();
    */


});
