angular.module('app').controller('reviewsController', function($scope, $resource, reviewDialog) {

    $scope.alerts = [];

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    var Reviews = $resource('api/reviews/');
    $scope.reviews = Reviews.query();

    $scope.editReviewClick = function(reviewId, bidAmount){
        reviewDialog(reviewId, bidAmount).result.then(function() {
        });
    };

    $scope.editReview = function(reviewId){
        reviewDialog(reviewId, null).result.then(function() {
            $scope.getListings();
        });
    };

});