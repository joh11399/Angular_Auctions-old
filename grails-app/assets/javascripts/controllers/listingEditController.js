var app = angular.module('app');
app.controller('listingEditController', function($scope, $resource, $location, $routeParams, $filter, confirmDelete, listingService, loginService) {

    $scope.today = function() {
        $scope.dt = moment().format('MM/DD/YYYY');
    };
    $scope.setMytime = function(){
        $scope.mytime = new Date(moment().add(1, 'hour').format('M/DD/YY hh:00 a'));
    };

    $scope.newListing = {};
    if($routeParams.id) {
        listingService.getListing($routeParams.id).then(function (data) {
            $scope.newListing = data;

            $scope.dt = moment(data.startDate).format('MM/DD/YYYY');
            $scope.mytime = new Date(moment(data.startDate).format('M/DD/YY hh:mm a'));
        });
    }else{
        loginService.getLoggedInUser().then(function(result) {
            $scope.newListing.seller = {};
            $scope.newListing.seller.id = result.data[0].id;
        });

        $scope.newListing.deliverOption = 'US Only';

        $scope.today();
        $scope.setMytime();
    }

    $scope.save = function(){

        $scope.alerts = [];

        $scope.newListing.startDate = moment($scope.dt).format('MM/DD/YYYY ') + moment($scope.mytime).format('hh:mm a');

        if($scope.newListing.id) {
                listingService.updateListing($scope.newListing).then(function(){
                        $location.path("listings");
                    },
                    function () {
                        $scope.alerts.push({type: 'danger', msg: 'there was a problem updating this listing.'});
                    });
                }
        else {
            listingService.createListing($scope.newListing).then(function(){
                    $location.path("listings");
                },
                function(){
                    $scope.alerts.push({type: 'danger', msg: 'there was a problem creating this listing.'});
                });
        }
    };

    $scope.clear = function () {
        $scope.dt = null;
    };

    $scope.toggleMin = function() {
        $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();

    $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };




    $scope.hstep = 1;
    $scope.mstep = 15;
    $scope.ismeridian = true;






    $scope.deleteClick = function(){
        confirmDelete().result.then(function() {
            listingService.deleteListing($routeParams.id).then(
                function(){
                    $location.path("listings");
                }, function(result){
                    $scope.alerts.push({type: 'danger', msg: result.status + ': '+ result.statusText});
                });
        });
    };




});