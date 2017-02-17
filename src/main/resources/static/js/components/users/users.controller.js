'use strict';
 
angular.module('votingApp').controller('UsersController',
    ['UsersService', '$scope', '$state', function( UsersService, $scope, $state) {

    let vm = this;
    vm.user = {};
    vm.password = '';
    vm.users = [];
 
    vm.submit = submit;
    vm.getAllUsers = getAllUsers;
    vm.createUser = createUser;
    vm.updateUser = updateUser;
    vm.removeUser = removeUser;
    vm.editUser = editUser;
    vm.reset = reset;
    vm.backToLogin = backToLogin;
 
    vm.successMessage = '';
    vm.errorMessage = '';
    vm.done = false;
 
    function submit() {
        console.log('Submitting');
	    if (vm.user.id === undefined || vm.user.id === null) {
	        console.log('Saving New User', vm.user);
	        createUser(vm.user);
	    } else {
	        updateUser(vm.user, vm.user.id);
	        console.log('User updated with id ', vm.user.id);
        }
    }
 
    function createUser(user) {
        console.log('About to create user');
        vm.user.password = btoa(vm.password || '')
        UsersService.createUser(user)
	        .then(
	            function (response) {
	                console.log('User created successfully');
	                vm.successMessage = 'User created successfully';
	                vm.errorMessage='';
	                vm.done = true;
	                vm.user = {};
	                vm.password = '';
	                $scope.usersForm.$setPristine();
	            },
	            function (errResponse) {
	                console.error('Error while creating User');
	                vm.errorMessage = 'Error while creating User: ' + errResponse.data.errorMessage;
	                vm.successMessage='';
                }
            );
    }

    function updateUser(user, id){
        console.log('About to update user');
		UsersService.updateUser(user, id)
		    .then(
		        function (response){
		            console.log('User updated successfully');
		            vm.successMessage='User updated successfully';
		            vm.errorMessage='';
		            vm.done = true;
		            vm.user = {}
		            $scope.usersForm.$setPristine();
		        },
		        function(errResponse){
		            console.error('Error while updating User');
		            vm.errorMessage='Error while updating User '+errResponse.data.errorMessage;
		            vm.successMessage='';
	            }
	        );
    }

    function removeUser(id){
        console.log('About to remove User with id '+id);
        UsersService.removeUser(id)
            .then(
                function(){
                    console.log('User '+id + ' removed successfully');
                },
                function(errResponse){
                	console.error('Error while removing user '+id +', Error :'+errResponse.data.errorMessage);
                }
            );
    }

    function getAllUsers(){
        return UsersService.getAllUsers();
    }
 
    function editUser(id) {
        vm.successMessage='';
        vm.errorMessage='';
        UsersService.getUser(id).then(
            function (user) {
                vm.user = user;
            },
            function (errResponse) {
                console.error('Error while removing user ' + id + ', Error :' + errResponse.data.errorMessage);
            }
        );
    }

    function reset(){
        vm.successMessage = '';
        vm.errorMessage = '';
        vm.user = {};
        $scope.usersForm.$setPristine(); //reset Form
    }

    function backToLogin() {
        console.log('Change state to login');
        $state.go('login');
    }
}]);