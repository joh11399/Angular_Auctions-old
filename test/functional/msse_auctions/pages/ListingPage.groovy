package msse_auctions.pages

import geb.Page


class ListingsPage extends Page {
    static url = '#/listings'

    static at = {
        waitFor { $("#listingsSearchTxt").css('display') == "inline-block" }
    }

    static content = {
        loginLbl { $("#loginLbl") }
        loginBtn { $("#loginBtn") }
        loginUsername { $("#username") }
        loginPassword { $("#password") }
        loginSubmitBtn { $("#submit") }

        includeCompleted { $('#listingsIncludeCompletedChk') }

        createListingBtn { $('#createListingBtn') }

        bidAmount { $('#bidAmount') }
        bidErrorLbl { $('#bidErrorLbl') }
        bidSubmitBtn { $("#bidSubmitBtn") }
        bidCancelBtn { $('#bidCancelBtn') }

        //reviewErrorLbl { $('#reviewErrorLbl') }
        reviewSubmitBtn { $("#reviewSubmitBtn") }
        reviewCancelBtn { $('#reviewCancelBtn') }

        reviewOfLbl { $('#reviewOfLbl') }
        reviewThumbsUp { $('#reviewThumbsUp') }
        reviewThumbsDown { $('#reviewThumbsDown') }
        reviewDescription { $("input[name='reviewDescription']") }


    }
}

class ListingDetailPage extends Page {
    static url = '#/listing'

    static at = {
        waitFor { $("#listingName").css('display') == "inline-block" }
    }

    static content = {
        listingName { $('#listingName') }
        listingEditBtn { $('#listingEditBtn') }
        listingDeliverOption { $('#listingDeliverOption') }
    }
}

class ListingEditPage extends Page {
    static url = '#/listingEdit'

    static at = {
        waitFor { $("#listingName").css('display') == "inline-block" }
    }
    static content = {

        listingName { $('#listingName') }
        listingDescription { $('#listingDescription') }
        listingStartDate { $('#listingStartDate') }
        listingStartTime { $('#listingStartTime') }
        listingStartingPrice { $('#listingStartingPrice') }
        listingDays { $('#listingDays') }
        listingDeliverOption { $('#listingDeliverOption') }

        listingSaveBtn { $('#listingSaveBtn') }
        listingDeleteBtn { $('#listingDeleteBtn') }
        confirmDeleteBtn { waitFor { $('#confirmDeleteBtn') }}
        confirmCancelBtn { waitFor { $('#confirmCancelBtn') }}
    }
}