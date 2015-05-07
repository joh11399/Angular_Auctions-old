package msse_auctions.pages

import geb.Page

class AccountsPage extends Page {

    static url = '#/accounts'

    static at = {
        waitFor { $("#loginBtn").css('display') == "block" }
    }

    static content = {
        loginLbl { $("#loginLbl") }
        loginBtn { $("#loginBtn") }
        createAccountBtn { $("#createAccountBtn") }
        viewAccountBtn { $("#viewAccountBtn") }
        loginUsername { $("#username") }
        loginPassword { $("#password") }
        loginSubmitBtn { $("#submit") }

    }
}

class AccountDetailPage extends Page {

    static url = '#/account'

    static at = {
        waitFor { $("#accountDetailUsername").css('display') == "inline-block" }
    }

    static content = {
        username { waitFor { $("#accountDetailUsername") } }
        email { waitFor { $("#accountDetailEmail") } }
        dateCreated { waitFor { $("#accountDetailDateCreated") } }
        lastUpdated { waitFor { $("#accountDetailLastUpdated") } }
        editBtn { waitFor { $("#accountDetailEditBtn") } }
    }
}

class AccountEditPage extends Page {

    static url = '#/accountEdit'

    static at = {
        waitFor { $("input[name='accountUsername']").css('display') == "inline-block" }
    }

    static content = {
        username { waitFor { $("input[name='accountUsername']") } }
        email { waitFor { $("input[name='accountEmail']") } }
        name { waitFor { $("input[name='accountName']") } }
        password { waitFor { $("input[name='accountPassword']") } }
        addressStreet { waitFor { $("input[name='accountAddressStreet']") } }
        addressCity { waitFor { $("input[name='accountAddressCity']") } }
        addressState { waitFor { $("input[name='accountAddressState']") } }
        addressZip { waitFor { $("input[name='accountAddressZip']") } }
        submitBtn { waitFor { $("#accountEditSubmitBtn") } }

        deleteBtn {waitFor { $("#accountDeleteBtn") } }
        confirmDeleteBtn { waitFor { $('#confirmDeleteBtn') }}
        confirmCancelBtn { waitFor { $('#confirmCancelBtn') }}

        accountPasswordCheckMark { waitFor { $("#accountPasswordCheckMark") } }
        accountPasswordLength { waitFor { $("#accountPasswordLength") } }
        accountPasswordNumberLetter { waitFor { $("#accountPasswordNumberLetter") } }


        /*
        message { waitFor { $('.modal-body') } }
        okButton { waitFor { $('#ok-btn') } }
        cancelButton { waitFor { $('#cancel-btn') } }
        */

    }

    public void enterAccountInfo(){
        $("input[name='accountUsername']").value('AccountFunctionalTest')
        $("input[name='accountEmail']").value('AccountFunctionalTest@test.com')
        $("input[name='accountName']").value('AccountFunctionalTest')
        $("input[name='accountPassword']").value('abcd1234')
        $("input[name='accountAddressStreet']").value('123 some street')
        $("input[name='accountAddressCity']").value('some city')
        $("input[name='accountAddressState']").value('MN')
        $("input[name='accountAddressZip']").value('12345')
    }
}

/*
class AccountShowPage extends Page {

    static url = 'account/show'

    static content = {
        name { $("#name") }
        email { $("#email") }
        addressStreet { $("#addressStreet") }
        addressCity { $("#addressCity") }
        addressState { $("#addressState") }
        addressZip { $("#addressZip") }
        dateCreated { $("#dateCreated") }
        lastUpdated { $("#lastUpdated") }
    }
}
class AccountEditPage extends Page{
    static url = 'account/show'

    static content = {
        name { $("#name") }
        email { $("#email") }
        addressStreet { $("#addressStreet") }
        addressCity { $("#addressCity") }
        addressState { $("#addressState") }
        addressZip { $("#addressZip") }
        dateCreated { $("#dateCreated") }
        lastUpdated { $("#lastUpdated") }
    }
}
*/