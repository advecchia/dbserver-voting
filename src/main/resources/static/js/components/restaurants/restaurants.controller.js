'use strict';
 
angular.module('votingApp').controller('RestaurantsController',
    ['RestaurantsService', '$scope', function( RestaurantsService, $scope) {

    let vm = this;
    vm.restaurant = {};
    vm.restaurants = [];
 
    vm.submit = submit;
    vm.getAllRestaurants = getAllRestaurants;
    vm.createRestaurant = createRestaurant;
    vm.updateRestaurant = updateRestaurant;
    vm.removeRestaurant = removeRestaurant;
    vm.editRestaurant = editRestaurant;
    vm.reset = reset;
 
    vm.successMessage = '';
    vm.errorMessage = '';
    vm.done = false;
 
    function submit() {
        console.log('Submitting');
	    if (vm.restaurant.id === undefined || vm.restaurant.id === null) {
	        console.log('Saving New Restaurant', vm.restaurant);
	        createRestaurant(vm.restaurant);
	    } else {
	        updateRestaurant(vm.restaurant, vm.restaurant.id);
	        console.log('Restaurant updated with id ', vm.restaurant.id);
        }
    }
 
    function createRestaurant(restaurant) {
        console.log('About to create Restaurant');
        RestaurantsService.createRestaurant(restaurant)
	        .then(
	            function (response) {
	                console.log('Restaurant created successfully');
	                vm.successMessage = 'Restaurant created successfully';
	                vm.errorMessage='';
	                vm.done = true;
	                vm.restaurant = {};
	                $scope.restaurantsForm.$setPristine();
	            },
	            function (errResponse) {
	                console.error('Error while creating Restaurant');
	                vm.errorMessage = 'Error while creating Restaurant: ' + errResponse.data.errorMessage;
	                vm.successMessage='';
                }
            );
    }

    function updateRestaurant(restaurant, id){
        console.log('About to update Restaurant');
		RestaurantsService.updateRestaurant(restaurant, id)
		    .then(
		        function (response){
		            console.log('Restaurant updated successfully');
		            vm.successMessage='Restaurant updated successfully';
		            vm.errorMessage='';
		            vm.done = true;
		            vm.restaurant = {}
		            $scope.restaurantsForm.$setPristine();
		        },
		        function(errResponse){
		            console.error('Error while updating Restaurant');
		            vm.errorMessage='Error while updating Restaurant '+errResponse.data;
		            vm.successMessage='';
	            }
	        );
    }

    function removeRestaurant(id){
        console.log('About to remove Restaurant with id '+id);
        RestaurantsService.removeRestaurant(id).then(
            function(){
                console.log('Restaurant '+id + ' removed successfully');
            },
            function(errResponse){
            	console.error('Error while removing Restaurant '+id +', Error :'+errResponse.data);
            }
        );
    }

    function getAllRestaurants(){
        return RestaurantsService.getAllRestaurants();
    }
 
    function editRestaurant(id) {
        vm.successMessage='';
        vm.errorMessage='';
        RestaurantsService.getRestaurant(id).then(
            function (restaurant) {
                vm.restaurant = restaurant;
            },
            function (errResponse) {
                console.error('Error while removing Restaurant ' + id + ', Error :' + errResponse.data);
            }
        );
    }

    function reset(){
        vm.successMessage='';
        vm.errorMessage='';
        vm.restaurant = {};
        $scope.restaurantsForm.$setPristine(); //reset Form
    }
}]);