'use strict';
 
angular.module('votingApp').factory('VotesService',
    ['$localStorage', '$http', '$q', 'urls', function ($localStorage, $http, $q, urls) {

    let factory = {
        loadAllVotes: loadAllVotes,
        getAllVotes: getAllVotes,
        createVote: createVote
    };
 
    return factory;
 
    function loadAllVotes() {
        console.log('Fetching all votes');
        let deferred = $q.defer();
        $http.get(urls.VOTES_SERVICE_API)
            .then(
                function (response) {
                    console.log('Fetched successfully all votes');
                    $localStorage.votes = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Error while loading votes');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
 
    function getAllVotes(){
        return $localStorage.votes;
    }
 
    function createVote(vote) {
        console.log('Creating Vote');
        let deferred = $q.defer();
        $http.post(urls.VOTES_SERVICE_API, vote)
            .then(
                function (response) {
                    loadAllVotes();
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                   console.error('Error while creating Vote : ' + errResponse.data.errorMessage);
                   deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);