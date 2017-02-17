describe('Unit: VotesService', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Votes', function () {
    	let VotesService = null;
    	let $localStorage = null;
    	let $q = null;
    	let httpBackend = null;
    	let errResponse = {data: {errorMessage: 'Error message'}};
    	let mockVotes = [
    		{id: 'id1', user: {id: 'idu1', name: 'uname1'}, restaurant: {id: 'idr1', name: 'rname1'}, votingDate: '2017-02-05'},
    		{id: 'id2', user: {id: 'idu2', name: 'uname2'}, restaurant: {id: 'idr2', name: 'rname2'}, votingDate: '2017-02-04'},
    		{id: 'id3', user: {id: 'idu3', name: 'uname3'}, restaurant: {id: 'idr3', name: 'rname3'}, votingDate: '2017-02-03'},
    		{id: 'id4', user: {id: 'idu4', name: 'uname4'}, restaurant: {id: 'idr4', name: 'rname4'}, votingDate: '2017-02-02'},
    	];
    	let mockRestaurants = [
        	{id: 'id1', name: 'name1', winners: [], votes: []},
        	{id: 'id2', name: 'name2', winners: [], votes: []}
    	];

        beforeEach(inject(function (_VotesService_, _$localStorage_, _$q_, $httpBackend) {
        	VotesService = _VotesService_;
        	$localStorage = _$localStorage_;
        	$q = _$q_;
        	httpBackend = $httpBackend;

        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/votes/').respond(mockVotes);
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/restaurants/').respond(mockRestaurants);

        }));

        it('verifies injection', function () {
        	expect(VotesService).toBeDefined();
        	expect($localStorage).toBeDefined();
        	expect($q).toBeDefined();
        	expect(httpBackend).toBeDefined();
        });

        it('should load all votes', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/votes/').respond(mockVotes);

        	// Make the call
        	VotesService.loadAllVotes().then(function(response) {
            	//Assert
        		expect(response.data).toEqual(mockVotes);
      	    });
        	httpBackend.flush();
        });
        
        it('should get all votes from $localStorage', function () {
        	// Mock values
        	$localStorage.votes = mockVotes;

        	// Make the call
        	let votes = VotesService.getAllVotes();

        	//Assert
    		expect(votes).toEqual(mockVotes);
        });
        
        it('should create one vote', function () {
        	// Mock values
        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/votes/', mockVotes[0]).respond(200, {});

        	// Make the call
        	VotesService.createVote(mockVotes[0]).then(function(response) {
            	//Assert
        		expect(response).toEqual({});
        	});

        	httpBackend.flush();
        });
        
        it('should fail to create one vote', function () {
        	// Mock values
        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/votes/', mockVotes[0]).respond(404, {});

        	// Make the call
        	VotesService.createVote(mockVotes[0]).then(function(response) {
            	//Assert
        		expect(response.data).toEqual({});
        	}, function (response) {
      	    	//Assert
        		expect(response.data).toEqual({});
      	    });
        	httpBackend.flush();
        });
    });
});