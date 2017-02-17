describe('Unit: LoginService', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('Login', function () {
    	let LoginService = null;
    	let $localStorage = null;
    	let httpBackend = null;
        let mockUser = {id: 'id', name: 'name', username: 'username', pasword: 'password', votes: []};
    	let errResponse = {data: {errorMessage: 'Error message'}};

        beforeEach(inject(function (_LoginService_, _$localStorage_, $httpBackend) {
        	LoginService = _LoginService_;
        	$localStorage = _$localStorage_;
        	httpBackend = $httpBackend;

        	httpBackend.when('GET', 'partials/login').respond({});
        }));

        it('verifies injection', function () {
        	expect(LoginService).toBeDefined();
        	expect($localStorage).toBeDefined();
        	expect(httpBackend).toBeDefined();
        });

        it('user should login', function () {
        	// Mock values
        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/users/authorize', 
        			mockUser).respond(mockUser);

        	// Make the call
        	LoginService.authorize(mockUser).then(function(response) {
            	//Assert
        		expect(response.data).toEqual(mockUser);
      	    });
        	httpBackend.flush();
        });
        
        it('user should fail to login', function () {
        	// Mock values
        	httpBackend.whenPOST('http://localhost:8080/voting/api/v1/users/authorize', 
        			mockUser).respond(401, errResponse);

        	// Make the call
        	LoginService.authorize(mockUser).then(function(response) {
            	//Assert
        		expect(response.data).toEqual(errResponse);
      	    }, function (response) {
      	    	//Assert
        		expect(response.data).toEqual(errResponse);
      	    });
        	httpBackend.flush();
        });
        
        it('user should logout', function () {
        	// Mock values

        	// Make the call
        	LoginService.logout();

        	//Assert
    		expect($localStorage.auth).toEqual(null);
        });
        
        it('should get user authorization', function () {
        	// Mock values
        	$localStorage.auth = mockUser;

        	// Make the call
        	let auth = LoginService.isAuth();

        	//Assert
    		expect(auth).toEqual(mockUser);
        });
    });
});