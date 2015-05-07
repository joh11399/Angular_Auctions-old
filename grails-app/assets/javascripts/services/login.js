var app = angular.module('app').factory('loginDialog', function($modal) {
    return function() {
        return $modal.open({
            templateUrl: 'templates/login.html',
            size: 'med',
            controller: 'loginController'
        });
    }
});


app.factory('loginService', ['$http', loginService]);
function loginService($http){

    var getLoggedInUser = function(){
        return $http.get('api/logins');
    };

    return{
        getLoggedInUser: getLoggedInUser
    }
}