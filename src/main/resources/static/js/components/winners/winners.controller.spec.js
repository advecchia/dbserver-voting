describe('Unit: WinnersController', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Winners', function () {
    	let WinnersController = null;
    	let WinnersService = null;
        let $scope = null;
        let $q = null;
        let $timeout = null;
        let errResponse = {data: {errorMessage: 'Error message'}};
        let mockWinners = [
    		{id: 'id1', restaurantId: 'idr1', lunchDate: '2017-02-05'},
    		{id: 'id2', restaurantId: 'idr2', lunchDate: '2017-02-04'},
    		{id: 'id3', restaurantId: 'idr3', lunchDate: '2017-02-03'},
    		{id: 'id4', restaurantId: 'idr4', lunchDate: '2017-02-02'}
    	];

        beforeEach(inject(function (_$controller_, _$rootScope_, _$timeout_, _$q_, $httpBackend) {
        	$scope = _$rootScope_.$new();
        	$timeout = _$timeout_;
        	$q = _$q_;

        	WinnersService = jasmine.createSpyObj('WinnersService', [
        		'loadAllWinners', 'getAllWinners']);
        	WinnersService.loadAllWinners.and.returnValue($q.when(mockWinners));
        	WinnersService.getAllWinners.and.returnValue(mockWinners);

        	$httpBackend.whenGET('partials/login').respond({});
        	$httpBackend.whenGET('partials/winners').respond({});
        	$httpBackend.whenGET('http://localhost:8080/voting/api/v1/winners/').respond([]);

        	WinnersController = _$controller_('WinnersController', {
        		WinnersService: WinnersService, 
        		$scope: $scope
        	});
        }));

        it('verifies injection', function () {
        	expect(WinnersService).toBeDefined();
        	expect($scope).toBeDefined();
        });

        it('validates controller initialization', function () {
            expect(WinnersController).toBeDefined();
        });
        
        it('should get all existing winners', function () {
        	// Mock values

        	// Make the call
        	let winners = WinnersController.getAllWinners();

        	//Assert
        	expect(winners).toEqual(mockWinners);
        	expect(WinnersService.getAllWinners).toHaveBeenCalled();
        	$timeout.flush();
        });
    });
});