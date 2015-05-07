angular.module('app').service('confirmDelete', function($modal) {
    return function() {
        return $modal.open({
            templateUrl: 'templates/confirmDelete.html',
            size: 'med',
            controller: 'confirmDeleteController'
        });
    }
});
