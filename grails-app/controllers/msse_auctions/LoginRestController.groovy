package msse_auctions

import grails.plugin.springsecurity.annotation.Secured

class LoginRestController {

    def springSecurityService

    static allowedMethods = [index: 'GET']

    @SuppressWarnings("GroovyUnusedDeclaration")
    static responseFormats = ['json', 'xml']

    @Secured('permitAll')
    def index() {
        def id = ""
        def username = ""
        def name = ""
        def account = springSecurityService.currentUser as Account
        try {
            id = account.id
        }catch(ex){}
        try {
            username = account.username
        }catch(ex){}
        try {
            name = account.name
        }catch(ex){}

        response.status = 200;

        //when using  $http.get()  in angular, it expects an array to be returned...
        render(contentType: 'text/json') {
            [
            [
                'id': id,
                'username': username,
                'name': name
            ]
            ]
        }
    }
}