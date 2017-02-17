describe('Unit: WinnersService', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Winners', function () {
    	let WinnersService = null;
    	let $localStorage = null;
    	let $q = null;
    	let httpBackend = null;
    	let errResponse = {data: {errorMessage: 'Error message'}};
    	let mockWinners = [
    		{id: 'id1', restaurantId: 'idr1', lunchDate: '2017-02-05'},
    		{id: 'id2', restaurantId: 'idr2', lunchDate: '2017-02-04'},
    		{id: 'id3', restaurantId: 'idr3', lunchDate: '2017-02-03'},
    		{id: 'id4', restaurantId: 'idr4', lunchDate: '2017-02-02'}
    	];

        beforeEach(inject(function (_WinnersService_, _$localStorage_, _$q_, $httpBackend) {
        	WinnersService = _WinnersService_;
        	$localStorage = _$localStorage_;
        	$q = _$q_;
        	httpBackend = $httpBackend;

        	httpBackend.whenGET('partials/login').respond({});
        	httpBackend.whenGET('partials/winners').respond({});
        	httpBackend.flush();

        }));

        it('verifies injection', function () {
        	expect(WinnersService).toBeDefined();
        	expect($localStorage).toBeDefined();
        	expect($q).toBeDefined();
        	expect(httpBackend).toBeDefined();
        });

        it('should load all winners', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/winners/').respond(200, mockWinners);

        	// Make the call
        	WinnersService.loadAllWinners().then(function(response) {
            	//Assert
        		expect(response.data).toEqual(mockWinners);
      	    });
        	httpBackend.flush();
        });
        
        it('should get all winners from $localStorage', function () {
        	// Mock values
        	$localStorage.winners = mockWinners;

        	// Make the call
        	let winners = WinnersService.getAllWinners();

        	//Assert
    		expect(winners).toEqual(mockWinners);
        });
    });
});