var app = angular.module('app');
app.controller('accountEditController', function($scope, $location, $resource, $modal, $routeParams, accountService, confirmDelete) {

    $scope.alerts = [];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.states = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                     "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                     "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                     "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                     "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];

    $scope.newAccount = {};

    if($routeParams.id) {
        accountService.getAccount($routeParams.id).then(function(data){
            $scope.newAccount = data;

            $scope.newAccount.dateCreated = moment(data.dateCreated).format('MM/DD/YYYY hh:mm a');
            $scope.newAccount.lastUpdated = moment(data.lastUpdated).format('MM/DD/YYYY hh:mm a');
        });
    }
    else{
        $scope.newAccount.addressState = 'MN';
    }

    $scope.save = function(){

        //reset alerts
        //  the user may have previously gotten an error but not cleared it
        $scope.alerts = [];

         if($scope.newAccount.id) {
             if($scope.accountForm.accountPassword.$pristine){
                 //due to encryption, don't validate a password that hasn't changed
                 delete $scope.newAccount.password;
             }

            accountService.updateAccount($scope.newAccount).then(
                function () {
                    $location.path("accounts");
                },
                function () {
                    $scope.alerts.push({type: 'danger', msg: 'there was a problem updating this account.'});
                });
        }
        else {
            accountService.createAccount($scope.newAccount).then(function() {
                    $location.path("accounts");
                    //$scope.alerts.push({type: 'success', msg: result.responseText });
                },
                function(result){
                    $scope.alerts.push({type: 'danger', msg: 'there was a problem creating this account.<br>' + result.responseText});
                });
        }
    };

    $scope.passwordClick = function(){
        if($scope.newAccount.password.length >= 60){
            $scope.newAccount.password='';
            $scope.accountForm.accountPassword.$dirty = true;
        }
    };

    $scope.deleteClick = function(){
        confirmDelete().result.then(function() {
            accountService.deleteAccount($routeParams.id).then(
                function(){
                    $location.path("accounts");
                }, function(result){
                    $scope.alerts.push({type: 'danger', msg: result.status + ': '+ result.statusText});
                });
        });
    };

});