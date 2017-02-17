describe('Unit: votingApp', function () {
    beforeEach(module('votingApp'));
    beforeEach(module('ui.router'));
    beforeEach(module('ngStorage'));

    describe('App default route', function () {
        let $state = null;
        let $location = null;
        let $rootScope = null;
        let $localStorage = null;
        let initialState = 'login';
        let mockUser = {id: 'id', name: 'name', username: 'username', pasword: 'password', votes: []};

        beforeEach(inject(function (_$state_, _$location_, _$rootScope_, _$localStorage_) {
        	$state = _$state_;
        	$location = _$location_;
        	$rootScope = _$rootScope_;
        	$localStorage = _$localStorage_;
        }));

        it('verifies injection', function () {
        	expect($state).not.toBeNull();
        	expect($location).not.toBeNull();
        	expect($rootScope).not.toBeNull();
        	expect($localStorage).not.toBeNull();
        });
        
        it('verifies login state configuration', function () {
            let config = $state.get(initialState);
            expect(config.url).toEqual('/');
            expect(config.templateUrl).toEqual('partials/login');
            expect(config.controllerAs).toEqual('loginCtrl');
        });
        
        it('verifies change state configuration', function () {
        	$location.path('/anyPath');
        	$rootScope.$emit('$locationChangeSuccess');
        	expect($location.path()).toBe('/');
        });
        
        it('verifies change state configuration', function () {
        	$location.path('/login');
        	$localStorage.auth = mockUser;
        	let toState = {name: 'winners'};
        	$rootScope.$emit('$stateChangeStart', toState);
        	spyOn($state, 'go').and.callThrough();
        	$location.path('/winners');
        	expect($location.path()).toBe('/winners');
        });
    });
});