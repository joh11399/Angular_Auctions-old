// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery/dist/jquery
//= require angular/angular
//= require angular-route/angular-route
//= require angular-resource/angular-resource
//= require bootstrap/dist/js/bootstrap
//= require angular-bootstrap/ui-bootstrap-tpls
//= require moment/moment
//= require angular-moment/angular-moment
//= require_self
//= require_tree .

var app = angular.module('app', ['ngRoute', 'ngResource', 'ui.bootstrap', 'angularMoment']);

if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
            $(this).fadeIn();
        }).ajaxStop(function() {
            $(this).fadeOut();
        });
    })(jQuery);
}
