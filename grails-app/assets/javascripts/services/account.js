angular.module('app').factory('accountService', ['$resource', accountService]);

function accountService($resource){
    var Account = $resource('api/accounts/:id', {id:'@id'}, {'create': {method:'POST'}, 'update': {method:'PUT'}});

    var getAccount = function(accountId){
        return Account.get({id: accountId}).$promise;
    };
    var createAccount = function(account){
        return Account.create(account).$promise;
    };
    var updateAccount = function(account){
        return Account.update(account).$promise;
    };
    var deleteAccount = function(accountId){
        return Account.delete({id: accountId}).$promise;
    };

    return{
        getAccount: getAccount,
        createAccount: createAccount,
        updateAccount: updateAccount,
        deleteAccount: deleteAccount
    }
}