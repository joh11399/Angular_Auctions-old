var app = angular.module('app');
app.service('reviewDialog', function($modal) {
    return function(reviewId, listingId) {
        return $modal.open({
            templateUrl: 'templates/reviewDialog.html',
            size: 'med',
            controller: 'reviewDialogController',
            resolve: {
                reviewId: function () {
                    return reviewId;
                },
                listingId: function () {
                    return listingId;
                }
            }
        });
    }
});


app.factory('reviewService', ['$resource', reviewService]);

function reviewService($resource){
    var Review = $resource('api/reviews/:id', {id:'@id'}, {'create': {method:'POST'}, 'update': {method:'PUT'}});

    var getReview = function(reviewId){
        return Review.get({id: reviewId}).$promise;
    };
    var createReview = function(review){
        return Review.create(review).$promise;
    };
    var updateReview = function(review){
        return Review.update(review).$promise;
    };
    var deleteReview = function(reviewId){
        return Review.delete({id: reviewId}).$promise;
    };

    return{
        getReview: getReview,
        createReview: createReview,
        updateReview: updateReview,
        deleteReview: deleteReview
    }
}