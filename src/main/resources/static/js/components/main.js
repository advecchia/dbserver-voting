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
        	.state('login', {
        		url: '/',
                templateUrl: 'partials/login',
                controller:'LoginController',
                controllerAs:'loginCtrl',
                resolve: {
                	auth: function (LoginService) {
                		console.log('Verifying authorization');
                		return LoginService.isAuth();
                	}
                }
        	})
            .state('votes', {
            	url: '/votes',
                templateUrl: 'partials/votes',
                controller:'VotesController',
                controllerAs:'votesCtrl',
                resolve: { /* @ngInject */
                    votes: function ($q, VotesService) {
                        console.log('Load all votes');
                        let deferred = $q.defer();
                        VotesService.loadAllVotes().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            })
            .state('restaurants', {
                url: '/restaurants',
                templateUrl: 'partials/restaurants',
                controller:'RestaurantsController',
                controllerAs:'restaurantsCtrl',
                resolve: { /* @ngInject */
                    restaurants: function ($q, RestaurantsService) {
                        console.log('Load all restaurants page');
                        let deferred = $q.defer();
                        RestaurantsService.loadAllRestaurants().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            })
            .state('users', {
                url: '/users',
                templateUrl: 'partials/users',
                controller:'UsersController',
                controllerAs:'usersCtrl'
            })
            .state('winners', {
            	url: '/winners',
                templateUrl: 'partials/winners',
                controller:'WinnersController',
                controllerAs:'winnersCtrl',
                resolve: { /* @ngInject */
                    winners: function ($q, WinnersService) {
                        console.log('Load all winners');
                        let deferred = $q.defer();
                        WinnersService.loadAllWinners().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]
);

//State return to login page
app.run(['$rootScope', '$state', '$localStorage',
	function ($rootScope, $state, $localStorage) {
		$rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
			if ((toState.name === 'users' || toState.name === 'login') && !!$localStorage.auth) {
				// user is logged
				event.preventDefault();
				$state.go('winners'); // default for logged users
			}
			if (toState.name !== 'login' && !$localStorage.auth) {
				// looking to access without authorization
				if (toState.name !== 'users') {
					event.preventDefault();
					$state.go('login');
				} // got to users and make a new account
			} // other states and has authorization
		});
		$rootScope.$on('$stateChangeSuccess',
			function(event, toState, toParams, fromState, fromParams){
				if (!!toState && !!toState.name)
					console.log('Ended in: ' + toState.name);
				if (!!$localStorage) {
					console.log('$localStorage ' + $localStorage);
					if (!!$localStorage.auth) {
						console.log('$localStorage.auth ' + $localStorage.auth);
						if (!!$localStorage.auth.id) {
							console.log('$localStorage.auth.id ' + $localStorage.auth.id);
						}
					}
				}
		});
	}]
);