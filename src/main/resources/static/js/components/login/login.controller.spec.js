describe('Unit: LoginController', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Login', function () {
    	let LoginController = null;
    	let LoginService = null;
    	let $q = null;
    	let $state = null;
        let $scope = null;
        let $timeout = null;
        let mockUser = {id: 'id', name: 'name', username: 'username', pasword: 'password', votes: []};

        beforeEach(inject(function (_$controller_, _$q_, _$rootScope_, _$state_, _$timeout_, $httpBackend) {
        	$scope = _$rootScope_.$new();
        	$state = _$state_;
        	$q = _$q_;
        	$timeout = _$timeout_;

        	let errResponse = {data: {errorMessage: 'Error message'}};
        	LoginService = jasmine.createSpyObj('LoginService', ['authorize', 'logout', 'isAuth']);
        	LoginService.authorize.and.returnValue($q.when(mockUser));
        	LoginService.logout.and.returnValue(null);
        	LoginService.isAuth.and.returnValue(mockUser);

        	$httpBackend.when('GET', 'partials/login').respond({});

        	LoginController = _$controller_('LoginController', {
        		LoginService: LoginService, 
        		$scope: $scope, 
        		$state: $state
        	});
        }));

        it('verifies injection', function () {
        	expect(LoginService).toBeDefined();
        	expect($scope).toBeDefined();
        	expect($state).toBeDefined();
        });

        it('validates controller initialization', function () {
            expect(LoginController).toBeDefined();
        });

        it('user should login', function () {
        	// Mock values
        	LoginController.user = mockUser;
        	$scope.loginForm = {};
        	$scope.loginForm.$pristine = false;
        	$scope.loginForm.$setPristine = function() {
        		$scope.loginForm.$pristine = true;
        	};
        	spyOn($state, 'go').and.callFake(function() {
        		//$state.$current.name = 'winners';
        	});
        	
        	// Make the call
        	LoginController.login();
        	
        	//Assert
        	expect(LoginService.authorize).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('user should logout', function () {
        	// Mock values
        	LoginController.user = mockUser;
        	spyOn($state, 'go').and.callFake(function() {
        		//$state.$current.name = 'login';
        	});
        	
        	// Make the call
        	LoginController.logout();
        	
        	//Assert
        	expect(LoginService.logout).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should go to create a new user', function () {
        	// Mock values
        	spyOn($state, 'go').and.callFake(function() {
        		//$state.$current.name = 'users';
        	});
        	
        	// Make the call
        	LoginController.newUser();
        	
        	//Assert
        	$timeout.flush();
        });
        
        it('should get user authorization', function () {
        	// Mock values
        	
        	// Make the call
        	LoginController.isAuth();
        	
        	//Assert
        	expect(LoginService.isAuth).toHaveBeenCalled();
        });
    });
});