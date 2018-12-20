'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("userHomeCtrl", ["$scope", "$rootScope", "$state", "$stateParams", "$localStorage", "$sessionStorage", "authenticationSvc",
  function($scope, $rootScope, $state, $stateParams, $localStorage, $sessionStorage, authenticationSvc) {

      $scope.userType = $rootScope.userInfo ?  $rootScope.userInfo.usertype : '';
}]);
});