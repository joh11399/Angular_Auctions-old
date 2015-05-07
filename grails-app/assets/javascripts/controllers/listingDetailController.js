angular.module('app').controller('listingDetailController', function($scope, $resource, $location, $routeParams, loginService, listingService, bidDialog, reviewDialog) {

    $scope.loggedInId = '';
    $scope.loggedInName = '';
    loginService.getLoggedInUser().then(function(result) {
        $scope.loggedInId = result.data[0].id;
        $scope.loggedInName = result.data[0].name;
    });

    $scope.listing = {};
    $scope.canBid = false;
    $scope.canReview = false;
    if($routeParams.id) {
        listingService.getListing($routeParams.id).then(function (data) {
            $scope.listing = data;
            $scope.listing.startDate = moment($scope.listing.startDate).format('MM/DD/YYYY hh:mm a');

            var id = $scope.loggedInId;
            var name = $scope.loggedInName;
            var hb = $scope.listing.highestBidStr;
            try{ hb = hb.substring(hb.indexOf(' - ') + 3); }
            catch(ex){}

            if($scope.loggedInId!=''){
                $scope.canBid = $scope.listing.timeRemaining != 'completed';
                $scope.canReview = $scope.listing.timeRemaining == 'completed' && (hb.indexOf(name) != -1 || $scope.listing.seller.id == id);
            }
        });
    }

    $scope.createBid = function(listingId){
        bidDialog(null, listingId).result.then(function() {
            listingService.getListing($routeParams.id).then(function (data) {
                $scope.listing = data;
            });
        });
    };
    $scope.createReview = function(listingId){
        reviewDialog(null, listingId).result.then(function() {
            listingService.getListing($routeParams.id).then(function (data) {
                $scope.listing = data;
            });
        });
    };
});