describe('Unit: RestaurantsController', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Restaurants', function () {
    	let RestaurantsController = null;
    	let RestaurantsService = null;
        let $scope = null;
        let $q = null;
        let $timeout = null;
        let errResponse = {data: {errorMessage: 'Error message'}};
        let mockRestaurants = [
        	{id: 'id1', name: 'name1', winners: [], votes: []},
        	{id: 'id2', name: 'name2', winners: [], votes: []}
    	];

        beforeEach(inject(function (_$controller_, _$rootScope_, _$timeout_, _$q_, $httpBackend) {
        	$scope = _$rootScope_.$new();
        	$timeout = _$timeout_;
        	$q = _$q_;

        	RestaurantsService = jasmine.createSpyObj('RestaurantsService', [
        		'loadAllRestaurants', 'getAllRestaurants', 'getRestaurant', 
        		'createRestaurant', 'updateRestaurant', 'removeRestaurant']);
        	RestaurantsService.loadAllRestaurants.and.returnValue($q.when(mockRestaurants));
        	RestaurantsService.getAllRestaurants.and.returnValue(mockRestaurants);
        	RestaurantsService.getRestaurant.and.returnValue($q.when(mockRestaurants[0]));
        	RestaurantsService.createRestaurant.and.returnValue($q.when({}));
        	RestaurantsService.updateRestaurant.and.returnValue($q.when(mockRestaurants[0]));
        	RestaurantsService.removeRestaurant.and.returnValue($q.when({}));

        	$httpBackend.whenGET('partials/login').respond({});
        	$httpBackend.whenGET('partials/winners').respond({});
        	$httpBackend.whenGET('http://localhost:8080/voting/api/v1/winners/').respond([]);

        	RestaurantsController = _$controller_('RestaurantsController', {
        		RestaurantsService: RestaurantsService, 
        		$scope: $scope
        	});
        }));

        it('verifies injection', function () {
        	expect(RestaurantsService).toBeDefined();
        	expect($scope).toBeDefined();
        });

        it('validates controller initialization', function () {
            expect(RestaurantsController).toBeDefined();
        });

        it('should submit to create a restaurant with null id', function () {
        	// Mock values
        	let restaurant = {id: null, name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;
        	$scope.restaurantsForm = {};
        	$scope.restaurantsForm.$pristine = false;
        	$scope.restaurantsForm.$setPristine = function() {
        		$scope.restaurantsForm.$pristine = true;
        	};

        	// Make the call
        	RestaurantsController.submit();
        	
        	//Assert
        	expect(RestaurantsService.createRestaurant).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should submit to create a restaurant with undefined id', function () {
        	// Mock values
        	let restaurant = {id: undefined, name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;
        	$scope.restaurantsForm = {};
        	$scope.restaurantsForm.$pristine = false;
        	$scope.restaurantsForm.$setPristine = function() {
        		$scope.restaurantsForm.$pristine = true;
        	};

        	// Make the call
        	RestaurantsController.submit();
        	
        	//Assert
        	expect(RestaurantsService.createRestaurant).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should submit to update an existing restaurant', function () {
        	// Mock values
        	let restaurant = {id: 'id1', name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;
        	$scope.restaurantsForm = {};
        	$scope.restaurantsForm.$pristine = false;
        	$scope.restaurantsForm.$setPristine = function() {
        		$scope.restaurantsForm.$pristine = true;
        	};

        	// Make the call
        	RestaurantsController.submit();

        	//Assert
        	expect(RestaurantsService.updateRestaurant).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should create a restaurant', function () {
        	// Mock values
        	let restaurant = {id: null, name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;
        	$scope.restaurantsForm = {};
        	$scope.restaurantsForm.$pristine = false;
        	$scope.restaurantsForm.$setPristine = function() {
        		$scope.restaurantsForm.$pristine = true;
        	};

        	// Make the call
        	RestaurantsController.createRestaurant(restaurant);
        	
        	//Assert
        	expect(RestaurantsService.createRestaurant).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should update an existing restaurant', function () {
        	// Mock values
        	let restaurant = {id: 'id1', name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;
        	$scope.restaurantsForm = {};
        	$scope.restaurantsForm.$pristine = false;
        	$scope.restaurantsForm.$setPristine = function() {
        		$scope.restaurantsForm.$pristine = true;
        	};

        	// Make the call
        	RestaurantsController.updateRestaurant(restaurant, restaurant.id);

        	//Assert
        	expect(RestaurantsService.updateRestaurant).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should remove an existing restaurant', function () {
        	// Mock values
        	let restaurant = {id: 'id1', name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;

        	// Make the call
        	RestaurantsController.removeRestaurant(restaurant.id);

        	//Assert
        	expect(RestaurantsService.removeRestaurant).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should get all existing restaurant', function () {
        	// Mock values
        	let restaurant = {id: 'id1', name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;

        	// Make the call
        	let restaurants = RestaurantsController.getAllRestaurants();

        	//Assert
        	expect(restaurants).toEqual(mockRestaurants);
        	expect(RestaurantsService.getAllRestaurants).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should get an existing restaurant', function () {
        	// Mock values
        	let restaurant = {id: 'id1', name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;

        	// Make the call
        	RestaurantsController.editRestaurant(restaurant.id);

        	//Assert
        	expect(RestaurantsController.restaurant).toEqual(restaurant);
        	expect(RestaurantsService.getRestaurant).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should reset restaurant form', function () {
        	// Mock values
        	let restaurant = {id: 'id1', name: 'name1', winners: [], votes: []};
        	RestaurantsController.restaurant = restaurant;
        	RestaurantsController.successMessage = 'Success message';
        	RestaurantsController.errorMessage = '';
        	$scope.restaurantsForm = {};
        	$scope.restaurantsForm.$pristine = false;
        	$scope.restaurantsForm.$setPristine = function() {
        		$scope.restaurantsForm.$pristine = true;
        	};

        	// Make the call
        	RestaurantsController.reset();

        	//Assert
        	expect(RestaurantsController.successMessage).toEqual('');
        	expect(RestaurantsController.errorMessage).toEqual('');
        	expect(RestaurantsController.restaurant).toEqual({});
        	$timeout.flush();
        });
    });
});