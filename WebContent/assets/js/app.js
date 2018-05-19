define([
    'require',
    'jquery',
    'angular',
    'angular-resource',
  'ngStorage',
    'directives/main',
    'services/main',
    'controllers/main',
    'routes',
    'angular-animate',
    'angular-aria',
    'angular-messages',
    'angular-material',
  'jkAngularCarousel'
    
], function (require, $, angular, ngResource, ngStorage, directives,services, controllers, routes, ngMaterial, jkAngularCarousel) {
    'use strict';    
    /**
     * Application definition
     * This is where the AngularJS application is defined and all application dependencies declared.
     * @type {module}
     */
    var uoap = angular.module('custApp', [
            'ngResource',
            'app.controllers', 
            'app.directives', 
            'app.services', 
            'app.routes',
            'ngMaterial',
            'ngStorage',
            'ngMessages',
            'jkAngularCarousel'
        ]);

  uoap.config(['$mdThemingProvider', function($mdThemingProvider) {

    $mdThemingProvider.theme('input')
      .primaryPalette('blue')
      .accentPalette('pink')
      .dark();
    //
    // .warnPalette('red')
    // .backgroundPalette('gray');
  }
  ]);
	uoap.run(['$rootScope', '$state', '$stateParams', '$location',
		function($rootScope, $state, $stateParams, $location) {
		  // it'll be done when the state it resolved.
		  $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams) {
			  /*$rootScope.dataLoading = true;*/
			  // track the state the user wants to go to; authorization service needs this
				$rootScope.toState = toState;
				$rootScope.toStateParams = toStateParams;
				if(toState.name === "login") {
          $rootScope.showHeader = false;
        } else {
          $rootScope.showHeader = true;
        }
		  });
		  /*$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
			  //stop loading bar on stateChangeSuccess
        event.targetScope.$watch("$viewContentLoaded", function() {
          $rootScope.dataLoading = false;
        });
		  });*/
		}
	  ]);
    
    window.uoap = uoap;
	
    //Return the application  object
    return uoap;
});