package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.ListingsPage
import spock.lang.Stepwise

@Stepwise
class ReviewFunctionalSpec extends GebSpec {

    @Delegate
    static FunctionalTestUtils utils = new FunctionalTestUtils()

    def remote = new RemoteControl()

    def setupSpec() {
        if (!utils) {
            utils = new FunctionalTestUtils()
        }
        setupSampleData()
    }


    def 'verify review button is not displayed on open listings'() {
        when:
        to ListingsPage

        loginBtn.click()
        loginUsername.value("me")
        loginPassword.value("abcd1234")
        loginSubmitBtn.click()

        then: 'verify the open listing is shown on the page'

        waitFor { $('body').text().indexOf('testOpen') != -1 }
        waitFor { $('#listingReviewBtn' + listingOpenId).css('display') == 'none' }

    }

    def 'open review dialog from the listings page'() {
        when:
        to ListingsPage

        then: 'verify user is logged in'
        waitFor { loginLbl.text().toString() == "logged in as me" }
        waitFor { loginLbl.css("display") == "block" }

        waitFor { includeCompleted.css('display') == 'inline' }
        waitFor { includeCompleted.click() }


        when: 'click review button on completed listing'
        waitFor { $('#listingReviewBtn' + listingCompletedId).css('display') == 'inline-block' }
        waitFor { $('#listingReviewBtn' + listingCompletedId).click() }

        then: 'verify dialog is displayed'
        waitFor { $('body').text().indexOf('Review') != -1 }
        waitFor { reviewSubmitBtn.css('display') == 'inline-block' }

        cleanup:
        reviewCancelBtn.click()
    }

    /*
    def 'display bid minimum amount warning'() {
        when: 'click bid button on open listing'
        to ListingsPage
        waitFor { $('#listingBidBtn' + listingOpenId).css('display') == 'inline-block' }
        waitFor { $('#listingBidBtn' + listingOpenId).click() }

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
    */


    def 'successfully create a review'() {
        when: 'click review button on completed listing'
        to ListingsPage
        waitFor { includeCompleted.css('display') == 'inline' }
        waitFor { includeCompleted.click() }
        waitFor { $('#listingReviewBtn' + listingCompletedId).css('display') == 'inline-block' }
        waitFor { $('#listingReviewBtn' + listingCompletedId).click() }

        then:
        waitFor { reviewOfLbl.text() == 'Buyer' }
        reviewThumbsDown.click()
        reviewDescription.value('good job, I guess..')
        waitFor { reviewSubmitBtn.click() }

        when: 'once a review is created, the user can click the review button again to access that review'
        waitFor { $('#listingReviewBtn' + listingCompletedId).click() }

        then:
        reviewThumbsUp.click()
        waitFor { reviewSubmitBtn.click() }
    }
}
