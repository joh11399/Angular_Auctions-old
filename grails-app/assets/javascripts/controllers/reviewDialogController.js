angular.module('app').controller('reviewDialogController', function ($scope, $modalInstance, $resource, loginService, listingService, bidService, reviewService, reviewId, listingId) {

    $scope.loggedInId = '';
    $scope.loggedInName = '';
    loginService.getLoggedInUser().then(function(result) {
        $scope.loggedInId = result.data[0].id;
        $scope.loggedInName = result.data[0].name;


        //this is nested within getLoggedInUser() because it relies on the loggedInId..
        if(reviewId){
            reviewService.getReview(reviewId).then(function(data){
                $scope.editReview = data;
            });
        }
        else if(listingId){

            var Reviews = $resource('api/reviews/');
            Reviews.query().$promise.then(function(data){
                $scope.editReview = {};
                $(data).each(function(i){
                    if(parseInt(data[i].reviewer.id) == parseInt($scope.loggedInId) &&
                        parseInt(data[i].listing.id) == parseInt(listingId)){
                        $scope.editReview = data[i];
                    }
                });

                //if no previous reviews are found  (just test the reviewOf field)
                //  then populate the fields with default values..
                if($scope.editReview.reviewOf == null) {
                    $scope.editReview.thumbs = 'up';
                    $scope.editReview.rating = 5;
                    $scope.editReview.description = '';

                    $scope.editReview.listing = {};
                    $scope.editReview.listing.id = listingId;

                    $scope.editReview.reviewer = {};
                    $scope.editReview.reviewer.id = $scope.loggedInId;

                    listingService.getListing(listingId).then(function (data) {
                        var revieweeId = '';
                        var reviewOf = '';

                        if ($scope.loggedInId == data.highestBidderId) {
                            revieweeId = data.seller.id;
                            reviewOf = 'Seller';
                        }
                        else {
                            revieweeId = data.highestBidderId;
                            reviewOf = 'Buyer';
                        }

                        $scope.editReview.reviewee = {};
                        $scope.editReview.reviewee.id = revieweeId;

                        $scope.editReview.reviewOf = reviewOf;

                    });
                }
            });
        }
    });



        /*
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
        });
        */





    $scope.max = 5;
    $scope.isReadonly = false;

    $scope.hoveringOver = function(value) {
        $scope.overStar = value;
    };

    $scope.ratingStates = [
        {stateOn: 'glyphicon-star', stateOff: 'glyphicon-star-empty'}
    ];






    $scope.ok = function () {
        if($scope.editReview.id==null){
            reviewService.createReview($scope.editReview).then(function(){
                //$scope.alerts.push({type:'success', msg:'Bid Updated'});
                $modalInstance.close();

            }, function(result){
                $scope.alerts.push({type:'danger', msg:'Review Update Failed.. '+result});
            });

        }
        else{
            reviewService.updateReview($scope.editReview).then(function(){
                //$scope.alerts.push({type:'success', msg:'Bid Updated'});
                $modalInstance.close();

            }, function(result){
                $scope.alerts.push({type:'danger', msg:'Review Update Failed.. '+result});
            });
        }
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});