'use strict';
 
angular.module('votingApp').factory('LoginService',
    ['$localStorage', '$http', '$q', 'urls', function ($localStorage, $http, $q, urls) {

    let factory = {
        authorize: authorize,
        logout: logout,
        isAuth: isAuth
    };

    $localStorage.auth = null;

    return factory;
 
    function authorize(user) {
        let deferred = $q.defer();
        $http.post(urls.USERS_SERVICE_API + 'authorize', user)
            .then(
                function (response) {
                    console.log('User exists ');
                    $localStorage.auth = response.data;
                    deferred.resolve(response);
                },
                function (errResponse) {
                    console.error('Error while autorizing user');
                    $localStorage.auth = null;
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
    
    function logout() {
    	$localStorage.auth = null;
    }

    function isAuth() {
    	return $localStorage.auth;
    }
}]);