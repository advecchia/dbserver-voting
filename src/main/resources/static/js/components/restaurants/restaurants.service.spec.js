describe('Unit: RestaurantsService', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Restaurants', function () {
    	let RestaurantsService = null;
    	let $localStorage = null;
    	let $q = null;
    	let httpBackend = null;
    	let errResponse = {data: {errorMessage: 'Error message'}};
    	let mockRestaurants = [
        	{id: 'id1', name: 'name1', winners: [], votes: []},
        	{id: 'id2', name: 'name2', winners: [], votes: []}
    	];

        beforeEach(inject(function (_RestaurantsService_, _$localStorage_, _$q_, $httpBackend) {
        	RestaurantsService = _RestaurantsService_;
        	$localStorage = _$localStorage_;
        	$q = _$q_;
        	httpBackend = $httpBackend;

        	httpBackend.whenGET('partials/login').respond({});
        	httpBackend.whenGET('partials/winners').respond({});
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/winners/').respond([]);
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/restaurants/').respond(mockRestaurants);
        	httpBackend.flush();

        }));

        it('verifies injection', function () {
        	expect(RestaurantsService).toBeDefined();
        	expect($localStorage).toBeDefined();
        	expect($q).toBeDefined();
        	expect(httpBackend).toBeDefined();
        });

        it('should load all restaurants', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/restaurants/').respond(mockRestaurants);

        	// Make the call
        	RestaurantsService.loadAllRestaurants().then(function(response) {
            	//Assert
        		expect(response.data).toEqual(mockRestaurants);
      	    });
        	httpBackend.flush();
        });
        
        it('should get all restaurants from $localStorage', function () {
        	// Mock values
        	$localStorage.restaurants = mockRestaurants;

        	// Make the call
        	let restaurants = RestaurantsService.getAllRestaurants();

        	//Assert
    		expect(restaurants).toEqual(mockRestaurants);
        });
        
        it('should get one restaurant', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/restaurants/' + mockRestaurants[0].id).respond(mockRestaurants[0]);

        	// Make the call
        	RestaurantsService.getRestaurant(mockRestaurants[0].id).then(function(response) {
            	//Assert
        		expect(response).toEqual(mockRestaurants[0]);
      	    });
        	httpBackend.flush();
        });
        
        it('should fail to get one restaurant', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/restaurants/' + mockRestaurants[0].id).respond(404, {});

        	// Make the call
        	RestaurantsService.getRestaurant(mockRestaurants[0].id).then(function(response) {
            	//Assert
        		expect(response.data).toEqual({});
      	    }, function (response) {
      	    	//Assert
        		expect(response.data).toEqual({});
      	    });
        	httpBackend.flush();
        });
        
        it('should create one restaurant', function () {
        	// Mock values

        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/restaurants/', mockRestaurants[0]).respond(200, {});

        	// Make the call
        	RestaurantsService.createRestaurant(mockRestaurants[0]).then(function(response) {
            	//Assert
        		expect(response).toEqual({});
        	});
        	httpBackend.flush();
        });
        
        it('should fail to create one restaurant', function () {
        	// Mock values

        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/restaurants/', mockRestaurants[0]).respond(404, {});

        	// Make the call
        	RestaurantsService.createRestaurant(mockRestaurants[0]).then(function(response) {
            	//Assert
        		expect(response.data).toEqual({});
        	}, function (response) {
      	    	//Assert
        		expect(response.data).toEqual({});
      	    });
        	httpBackend.flush();
        });

        it('should update one restaurant', function () {
        	// Mock values

        	httpBackend.whenPUT('http://localhost:8080/voting/api/v1/restaurants/' + mockRestaurants[0].id, mockRestaurants[0]).respond(200, mockRestaurants[0]);

        	// Make the call
        	RestaurantsService.updateRestaurant(mockRestaurants[0], mockRestaurants[0].id).then(function(response) {
            	//Assert
        		expect(response).toEqual(mockRestaurants[0]);

        	});
        	httpBackend.flush();
        });
        
        it('should fail to update one restaurant', function () {
        	// Mock values

        	httpBackend.whenPUT('http://localhost:8080/voting/api/v1/restaurants/' + mockRestaurants[0].id, mockRestaurants[0]).respond(404, {});

        	// Make the call
        	RestaurantsService.updateRestaurant(mockRestaurants[0], mockRestaurants[0].id).then(function(response) {
            	//Assert
        		expect(response.data).toEqual({});
        	}, function (response) {
      	    	//Assert
        		expect(response.data).toEqual({});
      	    });
        	httpBackend.flush();
        });

        it('should remove one restaurant', function () {
        	// Mock values

        	httpBackend.whenDELETE('http://localhost:8080/voting/api/v1/restaurants/' + mockRestaurants[0].id).respond(204, {});

        	// Make the call
        	RestaurantsService.removeRestaurant(mockRestaurants[0].id).then(function(response) {
            	//Assert
        		expect(response).toEqual({});
        	});
        	httpBackend.flush();
        });
        
        it('should fail to remove one restaurant', function () {
        	// Mock values

        	httpBackend.whenDELETE('http://localhost:8080/voting/api/v1/restaurants/' + mockRestaurants[0].id).respond(404, {});

        	// Make the call
        	RestaurantsService.removeRestaurant(mockRestaurants[0].id).then(function(response) {
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