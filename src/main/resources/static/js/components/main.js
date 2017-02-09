var app = angular.module('votingApp',['ui.router','ngStorage']);
 
app.constant('urls', {
    BASE: 'http://localhost:8080/voting',
    SERVICE_API : 'http://localhost:8080/voting/api/v1/',
    USERS_SERVICE_API : 'http://localhost:8080/voting/api/v1/users/',
    RESTAURANTS_SERVICE_API : 'http://localhost:8080/voting/api/v1/restaurants/',
    VOTES_SERVICE_API : 'http://localhost:8080/voting/api/v1/votes/',
    WINNERS_SERVICE_API : 'http://localhost:8080/voting/api/v1/winners/'
});
 
app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {
 
        $stateProvider
            .state('home', {
                views: {
                	'': {
                    	url: '/',
                		template: '<div class="column"><div ui-view="votes"></div><div ui-view="winners"></div></div>'
                	},
                	'votes@home': {
		            	url: '/votes',
		                templateUrl: 'partials/votes',
		                controller:'VotesController',
		                controllerAs:'votesCtrl',
		                resolve: {
		                    votes: function ($q, VotesService) {
		                        console.log('Load all votes');
		                        var deferred = $q.defer();
		                        VotesService.loadAllVotes().then(deferred.resolve, deferred.resolve);
		                        return deferred.promise;
		                    }
		                }
                	},
                	'winners@home': {
		            	url: '/winners',
		                templateUrl: 'partials/winners',
		                controller:'WinnersController',
		                controllerAs:'winnersCtrl',
		                resolve: {
		                    winners: function ($q, WinnersService) {
		                        console.log('Load all winners');
		                        var deferred = $q.defer();
		                        WinnersService.loadAllWinners().then(deferred.resolve, deferred.resolve);
		                        return deferred.promise;
		                    }
		                }
                	}
                }
            })
            .state('restaurants', {
                url: '/restaurants',
                templateUrl: 'partials/restaurants',
                controller:'RestaurantsController',
                controllerAs:'restaurantsCtrl',
                resolve: {
                    restaurants: function ($q, RestaurantsService) {
                        console.log('Load all restaurants page');
                        var deferred = $q.defer();
                        RestaurantsService.loadAllRestaurants().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            })
            .state('users', {
                url: '/users',
                templateUrl: 'partials/users',
                controller:'UsersController',
                controllerAs:'usersCtrl',
                resolve: {
                    users: function ($q, UsersService) {
                        console.log('Load all users page');
                        var deferred = $q.defer();
                        UsersService.loadAllUsers().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);