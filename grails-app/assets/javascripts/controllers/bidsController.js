angular.module('app').controller('bidsController', function($scope, $resource, bidDialog) {

    $scope.alerts = [];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    var Bids = $resource('api/bids/');
    $scope.bids = Bids.query();

    $scope.editBidClick = function(bidId){
        var listingId = 1;
        bidDialog(bidId, listingId).result.then(function() {
            $scope.bids = Bids.query();
        });
    };



});
