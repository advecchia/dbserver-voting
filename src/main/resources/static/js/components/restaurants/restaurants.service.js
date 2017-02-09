'use strict';
 
angular.module('votingApp').factory('RestaurantsService',
    ['$localStorage', '$http', '$q', 'urls', function ($localStorage, $http, $q, urls) {

    let factory = {
        loadAllRestaurants: loadAllRestaurants,
        getAllRestaurants: getAllRestaurants,
        getRestaurant: getRestaurant,
        createRestaurant: createRestaurant,
        updateRestaurant: updateRestaurant,
        removeRestaurant: removeRestaurant
    };
 
    return factory;
 
    function loadAllRestaurants() {
        console.log('Fetching all restaurants');
        let deferred = $q.defer();
        $http.get(urls.RESTAURANTS_SERVICE_API)
            .then(
                function (response) {
                    console.log('Fetched successfully all restaurants');
                    $localStorage.restaurants = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Error while loading restaurants');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
 
    function getAllRestaurants(){
        return $localStorage.restaurants;
    }
 
    function getRestaurant(id) {
        console.log('Fetching Restaurant with id :' + id);
        let deferred = $q.defer();
        $http.get(urls.RESTAURANTS_SERVICE_API + id)
            .then(
                function (response) {
                    console.log('Fetched successfully Restaurant with id :' + id);
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while loading Restaurant with id :' + id);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
 
    function createRestaurant(restaurant) {
        console.log('Creating Restaurant');
        let deferred = $q.defer();
        $http.post(urls.RESTAURANTS_SERVICE_API, restaurant)
            .then(
                function (response) {
                    loadAllRestaurants();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                   console.error('Error while creating Restaurant : ' + errResponse.data.errorMessage);
                   deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
 
    function updateRestaurant(restaurant, id) {
        console.log('Updating Restaurant with id ' + id);
        let deferred = $q.defer();
        $http.put(urls.RESTAURANTS_SERVICE_API + id, restaurant)
            .then(
                function (response) {
                    loadAllRestaurants();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while updating Restaurant with id :' + id);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
 
    function removeRestaurant(id) {
        console.log('Removing Restaurant with id ' + id);
        let deferred = $q.defer();
        $http.delete(urls.RESTAURANTS_SERVICE_API + id)
            .then(
                function (response) {
                    loadAllRestaurants();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while removing Restaurant with id :' + id);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);