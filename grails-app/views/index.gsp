<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE html>
<html>
<head>
    <title>sellit.com</title>

    <asset:stylesheet href="application.css"/>
    <asset:javascript src="application.js"/>

</head>
<body ng-app="app">

<header>
<label style="display: inline-block; color: #333; margin-left: 30px; font-size: 24px;">sellit.com</label>

<div ng-controller="loginLinksController" style="display: inline;">
<a id="createAccountBtn" class="btn btn-default loginLink" href="#/accountEdit" ng-show="loggedInUser==''">create account</a>
<a id="loginBtn" class="btn btn-primary loginLink" ng-click="loginLink()" >login</a>
<a id="viewAccountBtn" class="btn btn-default loginLink" href="#/account/{{loggedInId}}" ng-show="loggedInUser!=''">view account</a>
<label id="loginLbl" class="loginLbl" ng-show="loggedInUser!=''">logged in as {{loggedInUser}}</label>
</div>
</header>

<ng-view></ng-view>

</body>
</html>
