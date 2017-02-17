describe('Unit: UsersController', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Users', function () {
    	let UsersController = null;
    	let UsersService = null;
        let $scope = null;
        let $state = null;
        let $q = null;
        let $timeout = null;
        let errResponse = {data: {errorMessage: 'Error message'}};
        let mockUsers = [
    		{id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []},
    		{id: 'id2', name: 'name2', username: 'username2', pasword: 'password2', votes: []}
    	];

        beforeEach(inject(function (_$controller_, _$rootScope_, _$state_, _$timeout_, _$q_, $httpBackend) {
        	$scope = _$rootScope_.$new();
        	$state = _$state_;
        	$timeout = _$timeout_;
        	$q = _$q_;

        	UsersService = jasmine.createSpyObj('UsersService', [
        		'loadAllUsers', 'getAllUsers', 'getUser', 
        		'createUser', 'updateUser', 'removeUser']);
        	UsersService.loadAllUsers.and.returnValue($q.when(mockUsers));
        	UsersService.getAllUsers.and.returnValue(mockUsers);
        	UsersService.getUser.and.returnValue($q.when(mockUsers[0]));
        	UsersService.createUser.and.returnValue($q.when({}));
        	UsersService.updateUser.and.returnValue($q.when(mockUsers[0]));
        	UsersService.removeUser.and.returnValue($q.when({}));

        	$httpBackend.whenGET('partials/login').respond({});
        	$httpBackend.whenGET('partials/winners').respond({});
        	$httpBackend.whenGET('http://localhost:8080/voting/api/v1/winners/').respond([]);

        	UsersController = _$controller_('UsersController', {
        		UsersService: UsersService, 
        		$scope: $scope
        	});
        }));

        it('verifies injection', function () {
        	expect(UsersService).toBeDefined();
        	expect($scope).toBeDefined();
        });

        it('validates controller initialization', function () {
            expect(UsersController).toBeDefined();
        });

        it('should submit to create a user with null id', function () {
        	// Mock values
        	let user = {id: null, name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;
        	$scope.usersForm = {};
        	$scope.usersForm.$pristine = false;
        	$scope.usersForm.$setPristine = function() {
        		$scope.usersForm.$pristine = true;
        	};

        	// Make the call
        	UsersController.submit();
        	
        	//Assert
        	expect(UsersService.createUser).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should submit to create a user with undefined id', function () {
        	// Mock values
        	let user = {id: undefined, name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;
        	$scope.usersForm = {};
        	$scope.usersForm.$pristine = false;
        	$scope.usersForm.$setPristine = function() {
        		$scope.usersForm.$pristine = true;
        	};

        	// Make the call
        	UsersController.submit();
        	
        	//Assert
        	expect(UsersService.createUser).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should submit to update an existing user', function () {
        	// Mock values
        	let user = {id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;
        	$scope.usersForm = {};
        	$scope.usersForm.$pristine = false;
        	$scope.usersForm.$setPristine = function() {
        		$scope.usersForm.$pristine = true;
        	};

        	// Make the call
        	UsersController.submit();

        	//Assert
        	expect(UsersService.updateUser).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should create a user', function () {
        	// Mock values
        	let user = {id: null, name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;
        	$scope.usersForm = {};
        	$scope.usersForm.$pristine = false;
        	$scope.usersForm.$setPristine = function() {
        		$scope.usersForm.$pristine = true;
        	};

        	// Make the call
        	UsersController.createUser(user);
        	
        	//Assert
        	expect(UsersService.createUser).toHaveBeenCalled();
        	$timeout.flush();
        });

        it('should update an existing user', function () {
        	// Mock values
        	let user = {id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;
        	$scope.usersForm = {};
        	$scope.usersForm.$pristine = false;
        	$scope.usersForm.$setPristine = function() {
        		$scope.usersForm.$pristine = true;
        	};

        	// Make the call
        	UsersController.updateUser(user, user.id);

        	//Assert
        	expect(UsersService.updateUser).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should remove an existing user', function () {
        	// Mock values
        	let user = {id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;

        	// Make the call
        	UsersController.removeUser(user.id);

        	//Assert
        	expect(UsersService.removeUser).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should get all existing users', function () {
        	// Mock values
        	let user = {id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;

        	// Make the call
        	let users = UsersController.getAllUsers();

        	//Assert
        	expect(users).toEqual(mockUsers);
        	expect(UsersService.getAllUsers).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should edit an existing user', function () {
        	// Mock values
        	let user = {id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;

        	// Make the call
        	UsersController.editUser(user.id);

        	//Assert
        	expect(UsersController.user).toEqual(user);
        	expect(UsersService.getUser).toHaveBeenCalled();
        	$timeout.flush();
        });
        
        it('should reset user form', function () {
        	// Mock values
        	let user = {id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []};
        	UsersController.user = user;
        	UsersController.successMessage = 'Success message';
        	UsersController.errorMessage = '';
        	$scope.usersForm = {};
        	$scope.usersForm.$pristine = false;
        	$scope.usersForm.$setPristine = function() {
        		$scope.usersForm.$pristine = true;
        	};

        	// Make the call
        	UsersController.reset();

        	//Assert
        	expect(UsersController.successMessage).toEqual('');
        	expect(UsersController.errorMessage).toEqual('');
        	expect(UsersController.user).toEqual({});
        	$timeout.flush();
        });
        
        it('should back to login form', function () {
        	// Mock values
        	spyOn($state, 'go').and.callFake(function() {
        		//$state.$current.name = 'login';
        	});

        	// Make the call
        	UsersController.backToLogin();

        	//Assert
        	expect($state.go).toHaveBeenCalled();
        });
    });
});