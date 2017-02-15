<!DOCTYPE html>
 
<html lang="en" ng-app="votingApp">
    <head>
        <title>${title}</title>
        <link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.css">
        <link rel="stylesheet" href="css/votingApp.css"/>
    </head>
    <body>
		<nav class="navbar navbar-inverse" role="navigation">
		    <div class="navbar-header">
		        <a class="navbar-brand">Lunch Voting System</a>
		    </div>
		    <ul class="nav navbar-nav">
		        <li><a ui-sref="winners">Winners</a></li>
		        <li><a ui-sref="votes">Votes</a></li>
		        <li><a ui-sref="restaurants">Restaurants</a></li>
		        <li ng-controller="LoginController as loginCtrl"><a style="cursor:pointer;" ng-if="!!loginCtrl.isAuth()" ng-click="loginCtrl.logout()">Logout</a></li>
		    </ul>
		</nav>
		<div class="container">
        	<div ui-view></div>
        </div>
		<script src="https://unpkg.com/angular@1.5.8/angular.min.js"></script>
        <script src="https://unpkg.com/angular-ui-router@0.4.2/release/angular-ui-router.min.js"></script>
        <script src="https://unpkg.com/localforage@1.4.3/dist/localforage.min.js" ></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/ngStorage/0.3.11/ngStorage.min.js"></script>
        <script src="js/components/main.js"></script>
        <script src="js/components/login/login.controller.js"></script>
        <script src="js/components/login/login.service.js"></script>
        <script src="js/components/users/users.controller.js"></script>
        <script src="js/components/users/users.service.js"></script>
        <script src="js/components/restaurants/restaurants.controller.js"></script>
        <script src="js/components/restaurants/restaurants.service.js"></script>
        <script src="js/components/votes/votes.controller.js"></script>
        <script src="js/components/votes/votes.service.js"></script>
        <script src="js/components/winners/winners.controller.js"></script>
        <script src="js/components/winners/winners.service.js"></script>
    </body>
</html>