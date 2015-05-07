package msse_auctions

class BidService {

    def setListingHighestBid(Listing listing){
        def bid = getHighestBid(listing)
        if(bid){

            listing.highestBidStr = "\$" + bid.amount.round(2) + " - " + bid.bidder

            //add "Winner" to completed listings
            if(listing.timeRemaining == 'completed') {
                listing.highestBidStr = "Winner: " + listing.highestBidStr
            }
        }
    }


    def setListingHighestBidAmount(Listing listing){
        listing.highestBidAmount = listing.startingPrice

        def bid = getHighestBid(listing)
        if(bid){
            listing.highestBidId = bid.id
            listing.highestBidderId = bid.bidder.id
            listing.highestBidAmount = bid.amount
        }
    }

    def getNextAvailableBidAmount(Listing listing){
        def highestBidAmount = listing.startingPrice
        def highestBid = getHighestBid(listing)
        if(highestBid) {
            highestBidAmount = highestBid.amount + 0.5
        }
        highestBidAmount
    }


    def getHighestBid(Listing listing){
        def bids = Bid.findAll("from Bid as b where b.listing.id=:listingId order by dateCreated desc", [listingId: listing?.id])

        def j = 0;
        Bid returnBid
        bids.each(){
            if(j==0){
               returnBid = it
            }
            j++
        }

        returnBid
    }


    /*

    def getBids(Listing listing){
        //get all bids for the specified listing
        Bid.findAll("from Bid as b where b.listing.id=:bidListingId order by amount desc", [bidListingId: listing.id])
    }

    def getHighestBid(def bids){
        Bid highestBid = null
        if(bids.size() > 0){
            //loop through the collection of bids and assign the bid with the highest amount as the highestBid
            bids.each(){
                if(highestBid!=null){
                    if(it.amount > highestBid.amount){
                        highestBid = it
                    }
                }
                else{
                    highestBid = it
                }
            }
        }
        highestBid
    }
    */

    def copyBidFromSource(def src, Bid dest) {
        //if the source does not have a value, attempt to use the existing destination value
        dest.listing = Listing.findById(src.listing.id) ?: dest.listing
        dest.bidder = Account.findById(src.bidder.id) ?: dest.bidder
        dest.amount = src?.amount?.toFloat() ?: dest.amount
        //does not return anything, the dest values have been updated
    }
}