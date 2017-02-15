'use strict';
 
angular.module('votingApp').controller('VotesController',
    ['VotesService', 'RestaurantsService', 'LoginService', '$scope', '$localStorage', 
    	function(VotesService, RestaurantsService, LoginService, $scope, $localStorage) {

    let vm = this;
    vm.vote = {};
    vm.votes = [];
    vm.restaurant = {};
    vm.restaurants = [];
 
    vm.submit = submit;
    vm.createVote = createVote;
    vm.getAllVotes = getAllVotes;
    vm.reset = reset;
 
    vm.successMessage = '';
    vm.errorMessage = '';
    vm.done = false;

    // Looking for restaurants
    vm.restaurants = RestaurantsService.getAllRestaurants();

    function submit() {
        console.log('Submitting');
        
        // Make a correct date format for voting
        let currentTime = new Date();
        let year, month, day;
        year = currentTime.getFullYear();
        month = currentTime.getMonth();
        day = currentTime.getDate();
        vm.vote.votingDate = new Date(year, month, day);
        vm.vote.user = LoginService.isAuth();
        vm.vote.restaurant = JSON.parse(vm.restaurant);
        console.log('Saving New Vote', vm.vote);

        createVote(vm.vote);
    }
 
    function createVote(vote) {
        console.log('About to create vote');
        VotesService.createVote(vote)
	        .then(
	            function (response) {
	                console.log('Vote created successfully');
	                vm.successMessage = 'Vote created successfully';
	                vm.errorMessage='';
	                vm.done = true;
	                vm.vote = {};
	                $scope.votesForm.$setPristine();
	            },
	            function (errResponse) {
	                console.error('Error while creating Vote');
	                vm.errorMessage = 'Error while creating Vote: ' + errResponse.data.errorMessage;
	                vm.successMessage='';
                }
            );
    }

    function getAllVotes(){
        return VotesService.getAllVotes();
    }

    function reset(){
        vm.successMessage='';
        vm.errorMessage='';
        vm.vote = {};
        $scope.votesForm.$setPristine(); //reset Form
    }
}]);