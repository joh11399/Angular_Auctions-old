angular.module('app').controller('bidDialogController', function ($scope, $modalInstance, bidService, loginService, listingService, bidId, listingId) {

    if(bidId){
        bidService.getBid(bidId).then(function(data){
            $scope.editBid = data;
            $scope.editBid.amount = $scope.editBid.amount.toFixed(2);
            $scope.minimumBid = $scope.editBid.amount;
        });
    }
    else if(listingId){

        $scope.editBid = {};

        loginService.getLoggedInUser().then(function(data){
            $scope.editBid.bidder = {};
            $scope.editBid.bidder.id = data.data[0].id;
        });

        listingService.getListing(listingId).then(function(data){
            $scope.editBid.listing = {};
            $scope.editBid.listing.id = data.id;

            if(data.highestBidStr){
                $scope.editBid.amount = (data.highestBidAmount +.5).toFixed(2);
            }else{
                $scope.editBid.amount = (data.startingPrice).toFixed(2);
            }

            $scope.minimumBid = $scope.editBid.amount;
        });
    }

    $scope.belowMinimum = false;
    $scope.amountKeyup = function(){
        $scope.belowMinimum = ( parseFloat($scope.editBid.amount) < parseFloat($scope.minimumBid) )
    };

    $scope.ok = function () {
        if($scope.editBid.id==null){
            bidService.createBid($scope.editBid).then(function(){
                $modalInstance.close();

            });
        }
        else{
            bidService.updateBid($scope.editBid).then(function(){
                $modalInstance.close();
            });
        }
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});