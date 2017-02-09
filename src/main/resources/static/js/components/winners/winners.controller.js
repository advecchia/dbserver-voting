'use strict';
 
angular.module('votingApp').controller('WinnersController',
    ['WinnersService', '$scope', function( WinnersService, $scope) {

    let vm = this;
    vm.winner = {};
    vm.winners = [];
 
    vm.getAllWinners = getAllWinners;

    function getAllWinners(){
        return WinnersService.getAllWinners();
    }
}]);