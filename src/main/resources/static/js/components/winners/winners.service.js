'use strict';
 
angular.module('votingApp').factory('WinnersService',
    ['$localStorage', '$http', '$q', 'urls', function ($localStorage, $http, $q, urls) {

    let factory = {
        loadAllWinners: loadAllWinners,
        getAllWinners: getAllWinners
    };
 
    return factory;
 
    function loadAllWinners() {
        console.log('Fetching all winners');
        let deferred = $q.defer();
        $http.get(urls.WINNERS_SERVICE_API)
            .then(
                function (response) {
                    console.log('Fetched successfully all winners');
                    $localStorage.winners = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Error while loading winners');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
 
    function getAllWinners(){
        return $localStorage.winners;
    }
}]);