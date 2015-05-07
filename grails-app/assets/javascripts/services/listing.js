angular.module('app').factory('listingService', ['$resource', listingService]);

function listingService($resource){
    var Listing = $resource('api/listings/:id', {id:'@id'}, {'create': {method:'POST'}, 'update': {method:'PUT'}});

    var getListing = function(listingId){
        return Listing.get({id: listingId}).$promise;
    };
    var createListing = function(listing){
        return Listing.create(listing).$promise;
    };
    var updateListing = function(listing){
        return Listing.update(listing).$promise;
    };
    var deleteListing = function(listingId){
        return Listing.delete({id: listingId}).$promise;
    };

    return{
        getListing: getListing,
        createListing: createListing,
        updateListing: updateListing,
        deleteListing: deleteListing
    }
}