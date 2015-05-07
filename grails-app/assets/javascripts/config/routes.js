angular.module("app").config(function($routeProvider) {

    $routeProvider.when("/", { templateUrl: "templates/listings.html" });

    $routeProvider.when("/listings", { templateUrl: "templates/listings.html" });
    $routeProvider.when("/listing/:id", { templateUrl: "templates/listingDetail.html" });
    $routeProvider.when("/listingCreate", { templateUrl: "templates/listingEdit.html" });
    $routeProvider.when("/listingEdit/:id", { templateUrl: "templates/listingEdit.html" });

    $routeProvider.when("/accounts", { templateUrl: "templates/accounts.html"});
    $routeProvider.when("/account/:id", {templateUrl: "templates/accountDetail.html"});
    $routeProvider.when("/accountEdit", {templateUrl: "templates/accountEdit.html"});
    $routeProvider.when("/accountEdit/:id", {templateUrl: "templates/accountEdit.html"});

    $routeProvider.when("/bids", { templateUrl: "templates/bids.html"});

    $routeProvider.when("/reviews", { templateUrl: "templates/reviews.html"});

    //$routeProvider.otherwise("/listings", { templateUrl: "templates/listings.html" });
});
