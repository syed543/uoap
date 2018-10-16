'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("headerController", ["$scope", "$rootScope", "$state", "$stateParams", "$localStorage", "$sessionStorage", "authenticationSvc",
  function($scope, $rootScope, $state, $stateParams, $localStorage, $sessionStorage, authenticationSvc) {

    $scope.logout = function() {
        authenticationSvc.logout();
        $state.go('login');
    }

}]);
});