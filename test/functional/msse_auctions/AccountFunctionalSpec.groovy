package msse_auctions

import geb.spock.GebSpec
import grails.plugin.remotecontrol.RemoteControl
import msse_auctions.pages.AccountsPage
import msse_auctions.pages.AccountDetailPage
import msse_auctions.pages.AccountEditPage
import spock.lang.Stepwise
import spock.lang.Unroll

@Stepwise
class AccountFunctionalSpec extends GebSpec {

    @Delegate static FunctionalTestUtils utils = new FunctionalTestUtils()

    def remote = new RemoteControl()

    def accountId
    def accountId2
    def accountDateCreated
    def accountLastUpdated

    void setup() {
        accountId = remote {
            Account.findByUsername('me').id
        }
        accountId2 = remote {
            def account = Account.findByUsername('test')
            account.id
        }
        accountDateCreated = remote {
            Account.findByUsername('me').dateCreated
        }
        accountLastUpdated = remote {
            Account.findByUsername('me').lastUpdated
        }
    }

    def 'click create account button'(){
        when:
        to AccountsPage

        then: 'verify not logged in'
        loginLbl.text().toString() != "logged in as test"
        loginLbl.css("display") == "none"

        when: 'click Create Account'
        createAccountBtn.click()

        then: 'verify Account Edit fields are blank'
        at AccountEditPage
        username.value()==''
        email.value()==''
        name.value()==''
        password.value()==''
        addressStreet.value()==''
        addressCity.value()==''
        addressZip.value()==''
    }

    @Unroll
    def 'enter a invalid account values'() {
        when: 'enter new account info and click submit'
        to AccountEditPage
        enterAccountInfo()

        email.value(emailVal)
        password.value(passwordVal)

        then:
        waitFor{accountPasswordCheckMark.css('display')== checkMark}
        waitFor{accountPasswordLength.css('display')== length}
        waitFor{accountPasswordNumberLetter.css('display')== numberLetter}
        waitFor{submitBtn.disabled == submitBtnDisabled}

        where:
        passwordVal         |  emailVal                         |  checkMark  |  length    |  numberLetter  |  submitBtnDisabled

        //enter a password that is too short
        'abc123'            |  'AccountFunctionalTest@test.com' |  'none'     |  'inline'  |  'none'        |  true

        //enter a password that is too long
        'abcefghi123456789' |  'AccountFunctionalTest@test.com' |  'none'     |  'inline'  |  'none'        |  true

        //enter a password without a letter
        '12345678'          |  'AccountFunctionalTest@test.com' |  'none'     |  'none'    |  'inline'      |  true

        //enter a password without a number
        'abcefghi'          |  'AccountFunctionalTest@test.com' |  'none'     |  'none'    |  'inline'      |  true

        //enter a password that is blank
        ''                  |  'AccountFunctionalTest@test.com' |  'none'     |  'none'    |  'none'        |  true

        //enter an invalid email address
        'abcd1234'          |  'functionalTest'                 |  'inline'   |  'none'    |  'none'        |  true
    }

    def 'successfully create an account'(){
        when: 'enter new account info and click submit'
        to AccountEditPage
        enterAccountInfo()

        then:
        waitFor{accountPasswordCheckMark.css('display')=='inline'}
        waitFor{accountPasswordLength.css('display')=='none'}
        waitFor{accountPasswordNumberLetter.css('display')=='none'}
        waitFor{submitBtn.disabled == false}

        when:
        submitBtn.click()

        then:
        waitFor{
            $('body').text().toString().indexOf('AccountFunctionalTest@test.com')!=-1
        }

    }

    def 'single page login'(){
        when:
        to AccountsPage

        then: 'not logged in'
        loginLbl.text().toString() != "logged in as test"
        loginLbl.css("display") == "none"

        when: 'type login credentials'
        loginBtn.click()
        loginUsername.value('AccountFunctionalTest')
        loginPassword.value('abcd1234')
        loginSubmitBtn.click()

        then: 'verify user is logged in'
        waitFor{loginLbl.text().toString() == "logged in as AccountFunctionalTest"}
        waitFor{loginLbl.css("display") == "block"}

    }

    def "gets account details"() {
        when:
        viewAccountBtn.click()

        then:
        at AccountDetailPage
        username.text() == 'AccountFunctionalTest'
    }

    def 'successfully update an account without changing the password'(){
        when:
        editBtn.click()

        then:
        at AccountEditPage
        waitFor{ username.value() == 'AccountFunctionalTest' }

        when:
        email.value('AccountFunctional@email.com')
        submitBtn.click()

        then:
        at AccountsPage
        waitFor{ viewAccountBtn.click() }
        at AccountDetailPage
        waitFor{ email.text() == 'AccountFunctional@email.com' }

    }

    def 'delete an account'(){
        when:
        to AccountsPage

        then:
        waitFor{ $('#accountsTable').text().indexOf('AccountFunctionalTest') != -1 }
        waitFor {viewAccountBtn.click()}
        at AccountDetailPage

        when:
        waitFor {editBtn.click() }
        then:
        at AccountEditPage

        when:
        deleteBtn.click()

        then:
        waitFor{ confirmDeleteBtn.click() }

        when:
        at AccountsPage

        then:
        waitFor { $('#accountsTable').text().indexOf('AccountFunctionalTest') == -1 }
    }
}