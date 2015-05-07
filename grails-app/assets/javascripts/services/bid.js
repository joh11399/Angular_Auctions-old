angular.module('app').service('bidDialog', function($modal) {
    return function(bidId, listingId) {
        return $modal.open({
            templateUrl: 'templates/bidDialog.html',
            size: 'med',
            controller: 'bidDialogController',
            resolve: {
                bidId: function () {
                    return bidId;
                },
                listingId: function () {
                    return listingId;
                }
            }
        });
    }
});


angular.module('app').factory('bidService', ['$resource', bidService]);

function bidService($resource){
    var Bid = $resource('api/bids/:id', {id:'@id'}, {'create': {method:'POST'}, 'update': {method:'PUT'}});

    var getBid = function(bidId){
        return Bid.get({id: bidId}).$promise;
    };
    var createBid = function(bid){
        return Bid.create(bid).$promise;
    };
    var updateBid = function(bid){
        return Bid.update(bid).$promise;
    };
    var deleteBid= function(bidId){
        return Bid.delete({id: bidId}).$promise;
    };

    return{
        getBid: getBid,
        createBid: createBid,
        updateBid: updateBid,
        deleteBid: deleteBid
    }
}