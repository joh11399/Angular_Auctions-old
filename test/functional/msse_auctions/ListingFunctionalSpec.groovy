package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.ListingDetailPage
import msse_auctions.pages.ListingEditPage
import msse_auctions.pages.ListingsPage
import spock.lang.Stepwise

@Stepwise
class ListingFunctionalSpec extends GebSpec {

    @Delegate static FunctionalTestUtils utils = new FunctionalTestUtils()

    def remote = new RemoteControl()

    def 'show a list of 10 open listings'(){
        when:
        to ListingsPage

        then:
        def table = waitFor { $('#listingsTable') }
        def tableTR = waitFor { $('#listingsTable tr') }

        //verify testOpen is displayed
        waitFor{ table.text().indexOf('testOpen') != -1 }
        //testCompleted should not be displayed
        waitFor{ table.text().indexOf('testCompleted') == -1 }

        //10 rows plus the header == 11
        waitFor{ tableTR.size() == 11 }
    }

    def 'show a list of 10 open and completed listings'(){
        when:
        to ListingsPage
        waitFor{ includeCompleted.css('display') == 'inline' }
        waitFor{ includeCompleted.click() }

        then:
        def table = waitFor { $('#listingsTable') }
        def tableTR = waitFor { $('#listingsTable tr') }

        //verify testOpen is displayed
        waitFor{ table.text().indexOf('testOpen') != -1 }
        //verify testCompleted is displayed
        waitFor{ table.text().indexOf('testCompleted') != -1 }

        //10 rows plus the header == 11
        waitFor{ tableTR.size() == 11 }
    }

    def 'create listing'(){
        when:
        to ListingsPage

        //make sure the account is logged in..
        loginBtn.click()
        loginUsername.value("me")
        loginPassword.value("abcd1234")
        loginSubmitBtn.click()


        then:
        waitFor{ createListingBtn.click() }
        at ListingEditPage

        when:
        listingName.value('newListingName')
        listingDescription.value('newListingDescription')

        //startDate and startTime fields are populated with default values
        listingDays.value('2')
        listingStartingPrice.value('25.00')
        listingDeliverOption.value('US Only')
        listingSaveBtn.click()

        then:
        at ListingsPage
        $('body').text().indexOf('newListingName')!=-1
    }

    def 'display listing details'(){
        when:
        def listingId = remote {
            Listing.findByName('newListingName').id
        }
        to ListingsPage
        waitFor{ $('#listingName'+listingId).click() }

        then:
        at ListingDetailPage
        waitFor{ listingName.text() == 'newListingName' }
        waitFor{ listingDeliverOption.text() == 'US Only' }
    }

    def 'update listing'(){
        when:
        def listingId = remote {
            Listing.findByName('newListingName').id
        }
        to ListingsPage
        waitFor{ $('#listingName'+listingId).click() }

        then:
        at ListingDetailPage
        waitFor { listingEditBtn.click() }

        when:
        at ListingEditPage
        listingDeliverOption.value('Worldwide')
        listingSaveBtn.click()

        then:
        at ListingsPage
        waitFor{ $('#listingName'+listingId).click() }

        at ListingDetailPage
        listingDeliverOption.text() == 'Worldwide'

    }

    def 'delete listing'(){
        when:
        to ListingsPage

        waitFor{ $('#listingsTable').text().indexOf('newListingName') != -1 }

        def listingId = remote {
            Listing.findByName('newListingName').id
        }
        then:
        waitFor{ $('#listingName'+listingId).click() }
        at ListingDetailPage
        waitFor { listingEditBtn.click() }

        when:
        at ListingEditPage
        waitFor { listingDeleteBtn.click() }

        then:
        waitFor{ confirmDeleteBtn.click() }

        when:
        at ListingsPage

        then:
        waitFor { $('#listingsTable').text().indexOf('newListingName') == -1 }
    }
}