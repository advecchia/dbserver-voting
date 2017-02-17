describe('Unit: VotesController', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Votes', function () {
    	let VotesController = null;
    	let VotesService = null;
    	let RestaurantsService = null;
    	let LoginService = null;
        let $scope = null;
        let $state = null;
        let $q = null;
        let $timeout = null;
        let errResponse = {data: {errorMessage: 'Error message'}};
        let mockVotes = [
    		{id: 'id1', user: {id: 'idu1', name: 'uname1'}, restaurant: {id: 'idr1', name: 'rname1'}, votingDate: '2017-02-05'},
    		{id: 'id2', user: {id: 'idu2', name: 'uname2'}, restaurant: {id: 'idr2', name: 'rname2'}, votingDate: '2017-02-04'},
    		{id: 'id3', user: {id: 'idu3', name: 'uname3'}, restaurant: {id: 'idr3', name: 'rname3'}, votingDate: '2017-02-03'},
    		{id: 'id4', user: {id: 'idu4', name: 'uname4'}, restaurant: {id: 'idr4', name: 'rname4'}, votingDate: '2017-02-02'},
    	];
        let mockUser = {id: 'id', name: 'name', username: 'username', pasword: 'password', votes: []};
        let mockRestaurants = [
        	{id: 'id1', name: 'name1', winners: [], votes: []},
        	{id: 'id2', name: 'name2', winners: [], votes: []}
    	];

        beforeEach(inject(function (_$controller_, _$localStorage_, _$rootScope_, _$state_, _$timeout_, _$q_, $httpBackend) {
        	$localStorage = _$localStorage_;
        	$scope = _$rootScope_.$new();
        	$state = _$state_;
        	$timeout = _$timeout_;
        	$q = _$q_;

        	RestaurantsService = jasmine.createSpyObj('RestaurantsService', 
        			['getAllRestaurants']);
        	RestaurantsService.getAllRestaurants.and.returnValue(mockRestaurants);
        	
        	LoginService = jasmine.createSpyObj('LoginService', ['isAuth']);
        	LoginService.isAuth.and.returnValue(mockUser);

        	VotesService = jasmine.createSpyObj('VotesService', 
        			['loadAllVotes', 'getAllVotes', 'createVote']);
        	VotesService.loadAllVotes.and.returnValue($q.when(mockVotes));
        	VotesService.getAllVotes.and.returnValue(mockVotes);
        	VotesService.createVote.and.returnValue($q.when({}));

        	VotesController = _$controller_('VotesController', {
        		VotesService: VotesService,
        		RestaurantsService: RestaurantsService, 
        		LoginService: LoginService, 
        		$scope: $scope,
        		$localStorage: $localStorage
        	});
        }));

        it('verifies injection', function () {
        	expect(VotesService).toBeDefined();
        	expect(RestaurantsService).toBeDefined();
        	expect(LoginService).toBeDefined();
        	expect($scope).toBeDefined();
        	expect($localStorage).toBeDefined();
        });

        it('validates controller initialization', function () {
            expect(VotesController).toBeDefined();
        });

        it('should submit to create a vote', function () {
        	// Mock values
        	VotesController.restaurant = '{"id": "idr1", "name": "rname1"}';
        	$scope.votesForm = {};
        	$scope.votesForm.$pristine = false;
        	$scope.votesForm.$setPristine = function() {
        		$scope.votesForm.$pristine = true;
        	};

        	// Make the call
        	VotesController.submit();
        	
        	//Assert
        	expect(LoginService.isAuth).toHaveBeenCalled();
        	expect(VotesService.createVote).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should create a vote', function () {
        	// Mock values
        	let vote = {user: {id: 'idu1', name: 'uname1'}, restaurant: {id: 'idr1', name: 'rname1'}, votingDate: new Date(2017, 2, 5)};
        	VotesController.vote = vote;
        	$scope.votesForm = {};
        	$scope.votesForm.$pristine = false;
        	$scope.votesForm.$setPristine = function() {
        		$scope.votesForm.$pristine = true;
        	};

        	// Make the call
        	VotesController.createVote(vote);
        	
        	//Assert
        	expect(VotesService.createVote).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should get all existing votes', function () {
        	// Mock values

        	// Make the call
        	let votes = VotesController.getAllVotes();

        	//Assert
        	expect(votes).toEqual(mockVotes);
        	expect(VotesService.getAllVotes).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should reset votes form', function () {
        	// Mock values
        	VotesController.vote = {id: 'id1', user: {id: 'idu1', name: 'uname1'}, 
        			restaurant: {id: 'idr1', name: 'rname1'}, votingDate: '2017-02-05'};
        	VotesController.successMessage = 'Success message';
        	VotesController.errorMessage = '';
        	$scope.votesForm = {};
        	$scope.votesForm.$pristine = false;
        	$scope.votesForm.$setPristine = function() {
        		$scope.votesForm.$pristine = true;
        	};

        	// Make the call
        	VotesController.reset();

        	//Assert
        	expect(VotesController.successMessage).toEqual('');
        	expect(VotesController.errorMessage).toEqual('');
        	expect(VotesController.vote).toEqual({});
        	$timeout.flush();
        });
    });
});