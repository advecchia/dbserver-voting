'use strict';
 
angular.module('votingApp').controller('LoginController',
    ['LoginService', '$scope', '$state', function( LoginService, $scope, $state) {

    let vm = this;
    vm.user = {};
    vm.password = '';
 
    vm.login = login;
    vm.logout = logout;
    vm.newUser = newUser;
    vm.isAuth = isAuth;
 
    vm.successMessage = '';
    vm.errorMessage = '';
    vm.done = false;
 
    function login() {
        console.log('Login');
        vm.user.password = btoa(vm.password || '');
        LoginService.authorize(vm.user).then(
            function (response) {
                console.log('User authorized successfully');
                vm.done = true;
                vm.user = {};
                vm.password = '';
                $scope.loginForm.$setPristine();
                console.log('Change state to winners');
                $state.go('winners');
            },
            function (errResponse) {
                console.error('Error while authorizing User');
                vm.errorMessage = 'Error while authorizing User: ' + errResponse.data.errorMessage;
                vm.successMessage='';
            }
        );
    }
    
    function logout() {
    	LoginService.logout();
    	console.log('User logout');
    	$state.go('login');
    }

    function newUser() {
        console.log('Change state to new user');
        $state.go('users');
    }

    function isAuth() {
    	return LoginService.isAuth();
    }
}]);