'use strict';
 
angular.module('votingApp').controller('VotesController',
    ['VotesService','UsersService','RestaurantsService', '$scope', function(VotesService, UsersService, RestaurantsService, $scope) {

    let vm = this;
    vm.vote = {};
    vm.votes = [];
    vm.userId = {};
    vm.users = null;
    vm.restaurantId = null;
    vm.restaurants = [];
 
    vm.submit = submit;
    vm.getAllVotes = getAllVotes;
    vm.createVote = createVote;
    vm.reset = reset;
 
    vm.successMessage = '';
    vm.errorMessage = '';
    vm.done = false;

    // Looking for users 
    vm.users = UsersService.getAllUsers();

    // Looking for restaurants
    vm.restaurants = RestaurantsService.getAllRestaurants();

    function submit() {
        console.log('Submitting');
        console.log('Saving New Vote', vm.vote);
        let currentTime = new Date();
        let year, month, day;
        year = String(currentTime.getFullYear());
        month = String(currentTime.getMonth() + 1);
        month = (month < 10) ? '0'+month: month; 
        day = String(currentTime.getDate());
        day = (day < 10) ? '0'+day: day;
        vm.vote.votingDate = year + '-' + month + '-' + day; 
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