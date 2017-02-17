describe('Unit: UsersService', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Users', function () {
    	let UsersService = null;
    	let $localStorage = null;
    	let $q = null;
    	let httpBackend = null;
    	let errResponse = {data: {errorMessage: 'Error message'}};
    	let mockUsers = [
    		{id: 'id1', name: 'name1', username: 'username1', pasword: 'password1', votes: []},
    		{id: 'id2', name: 'name2', username: 'username2', pasword: 'password2', votes: []}
    	];

        beforeEach(inject(function (_UsersService_, _$localStorage_, _$q_, $httpBackend) {
        	UsersService = _UsersService_;
        	$localStorage = _$localStorage_;
        	$q = _$q_;
        	httpBackend = $httpBackend;

        	httpBackend.whenGET('partials/login').respond({});
        	httpBackend.whenGET('partials/winners').respond({});
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/winners/').respond([]);
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/users/').respond(mockUsers);
        	httpBackend.flush();

        }));

        it('verifies injection', function () {
        	expect(UsersService).toBeDefined();
        	expect($localStorage).toBeDefined();
        	expect($q).toBeDefined();
        	expect(httpBackend).toBeDefined();
        });

        it('should load all users', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/users/').respond(mockUsers);

        	// Make the call
        	UsersService.loadAllUsers().then(function(response) {
            	//Assert
        		expect(response.data).toEqual(mockUsers);
      	    });
        	httpBackend.flush();
        });
        
        it('should get all users from $localStorage', function () {
        	// Mock values
        	$localStorage.users = mockUsers;

        	// Make the call
        	let users = UsersService.getAllUsers();

        	//Assert
    		expect(users).toEqual(mockUsers);
        });
        
        it('should get one user', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/users/' + mockUsers[0].id).respond(mockUsers[0]);

        	// Make the call
        	UsersService.getUser(mockUsers[0].id).then(function(response) {
            	//Assert
        		expect(response).toEqual(mockUsers[0]);
      	    });
        	httpBackend.flush();
        });
        
        it('should fail to get one user', function () {
        	// Mock values
        	httpBackend.whenGET('http://localhost:8080/voting/api/v1/users/' + mockUsers[0].id).respond(404, {});

        	// Make the call
        	UsersService.getUser(mockUsers[0].id).then(function(response) {
            	//Assert
        		expect(response.data).toEqual({});
      	    }, function (response) {
      	    	//Assert
        		expect(response.data).toEqual({});
      	    });
        	httpBackend.flush();
        });
        
        it('should create one user', function () {
        	// Mock values
        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/users/', mockUsers[0]).respond(200, {});

        	// Make the call
        	UsersService.createUser(mockUsers[0]).then(function(response) {
            	//Assert
        		expect(response).toEqual({});
        	});
        	httpBackend.flush();
        });
        
        it('should fail to create one user', function () {
        	// Mock values
        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/users/', mockUsers[0]).respond(404, {});

        	// Make the call
        	UsersService.createUser(mockUsers[0]).then(function(response) {
            	//Assert
        		expect(response.data).toEqual({});
        	}, function (response) {
      	    	//Assert
        		expect(response.data).toEqual({});
      	    });
        	httpBackend.flush();
        });

        it('should update one user', function () {
        	// Mock values
        	httpBackend.whenPUT('http://localhost:8080/voting/api/v1/users/' + mockUsers[0].id, mockUsers[0]).respond(200, mockUsers[0]);

        	// Make the call
        	UsersService.updateUser(mockUsers[0], mockUsers[0].id).then(function(response) {
            	//Assert
        		expect(response).toEqual(mockUsers[0]);

        	});
        	httpBackend.flush();
        });
        
        it('should fail to update one user', function () {
        	// Mock values
        	httpBackend.whenPUT('http://localhost:8080/voting/api/v1/users/' + mockUsers[0].id, mockUsers[0]).respond(404, {});

        	// Make the call
        	UsersService.updateUser(mockUsers[0], mockUsers[0].id).then(function(response) {
            	//Assert
        		expect(response.data).toEqual({});
        	}, function (response) {
      	    	//Assert
        		expect(response.data).toEqual({});
      	    });
        	httpBackend.flush();
        });

        it('should remove one user', function () {
        	// Mock values
        	httpBackend.whenDELETE('http://localhost:8080/voting/api/v1/users/' + mockUsers[0].id).respond(204, {});

        	// Make the call
        	UsersService.removeUser(mockUsers[0].id).then(function(response) {
            	//Assert
        		expect(response).toEqual({});
        	});
        	httpBackend.flush();
        });
        
        it('should fail to remove one user', function () {
        	// Mock values

        	httpBackend.whenDELETE('http://localhost:8080/voting/api/v1/users/' + mockUsers[0].id).respond(404, {});

        	// Make the call
        	UsersService.removeUser(mockUsers[0].id).then(function(response) {
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