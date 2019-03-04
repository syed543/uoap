'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("headerController", ["$scope", "$rootScope", "$state", "$stateParams", "$localStorage", "$sessionStorage", "authenticationSvc", "JournalsService",
  function($scope, $rootScope, $state, $stateParams, $localStorage, $sessionStorage, authenticationSvc,  JournalsService) {

    $scope.logout = function() {
        authenticationSvc.logout();
        $state.go('login');
    }

    var _getJournals = function() {
      JournalsService.getJournals().then(function (data) {
          if (data.statusCode == 200) { // Success
              $scope.journals = data.data;
          } else { 					// Error
              console.log("Unable to fetch journals list. please contact support.");
          }
      });
    };
    _getJournals();
    
    $scope.openJournal = function(journal) {
    	var _name = journal.journal_name.split(' ').join('_');
        window.open(_name+'.html?id='+journal.id);
      }

}]);
});