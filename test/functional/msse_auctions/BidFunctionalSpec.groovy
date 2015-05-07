package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.ListingsPage
import spock.lang.Stepwise

@Stepwise
class BidFunctionalSpec  extends GebSpec {

    @Delegate static FunctionalTestUtils utils = new FunctionalTestUtils()

    def remote = new RemoteControl()

    def setupSpec() {
        if (!utils) {
            utils = new FunctionalTestUtils()
        }
        setupSampleData()
    }

    def 'open create bid dialog from the listings page'() {
        when:
        to ListingsPage

        loginBtn.click()
        loginUsername.value("me")
        loginPassword.value("abcd1234")
        loginSubmitBtn.click()

        then: 'verify user is logged in'
        waitFor{loginLbl.text().toString() == "logged in as me"}
        waitFor{loginLbl.css("display") == "block"}


        when: 'click bid button on open listing'
        waitFor{ $('#listingBidBtn'+listingOpenId).css('display') == 'inline-block' }
        waitFor{ $('#listingBidBtn'+listingOpenId).click() }

        then: 'verify dialog is displayed'
        waitFor{ $('body').text().indexOf('Place Bid') != -1 }
        waitFor{ bidSubmitBtn.css('display') == 'inline-block' }

        cleanup:
        bidCancelBtn.click()
    }

    def 'verify bid button is not displayed on completed listing'(){
        when: 'check the checkbox to include completed listings '
        to ListingsPage
        waitFor{ includeCompleted.css('display') == 'inline' }
        waitFor{ includeCompleted.click() }

        then: 'verify the completed listing is shown on the page'

        //listingCompletedId's name is 'testCompleted'  (this is how it's found on setupSampleData)
        waitFor { $('body').text().indexOf('testCompleted') != -1 }
        waitFor { $('#listingBidBtn'+listingCompletedId).css('display') == 'none' }

    }

    def 'display bid minimum amount warning'() {
        when: 'click bid button on open listing'
        to ListingsPage
        waitFor{ $('#listingBidBtn'+listingOpenId).css('display') == 'inline-block' }
        waitFor{ $('#listingBidBtn'+listingOpenId).click() }

        then:
        waitFor { bidAmount.value() != '' }
        waitFor { bidErrorLbl.css('display') == 'none' }

        when:
        float amount = Float.parseFloat(bidAmount.value())

        //I'm still not able to get float precision working to display 2 decimal places.
        //  I know this value is 10.00, so I'm just adding the last zero on
        String amountStr = amount.toString() + '0'

        bidAmount.value(amount - 1)

        then:
        waitFor { bidErrorLbl.css('display') == 'inline' }
        waitFor { bidErrorLbl.text() == 'The minimum bid is $' + amountStr }

        cleanup:
        bidCancelBtn.click()
    }

    def 'successfully create a bid'() {
        when: 'click bid button on open listing'
        to ListingsPage
        waitFor{ $('#listingBidBtn'+listingOpenId).css('display') == 'inline-block' }
        waitFor{ $('#listingBidBtn'+listingOpenId).click() }

        then:
        waitFor { bidAmount.value() != '' }
        waitFor { bidErrorLbl.css('display') == 'none' }
        float amount = Float.parseFloat(bidAmount.value())

        when:
        waitFor { bidSubmitBtn.click() }

        then:
        waitFor{ $('#listingHighestBidStr'+listingOpenId).text() == '$' + amount + ' - Me Test' }
    }
}