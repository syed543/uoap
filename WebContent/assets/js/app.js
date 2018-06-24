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
  'jkAngularCarousel',
  'angular-material-data-table'
    
], function (require, $, angular, ngResource, ngStorage, directives,services, controllers, routes, ngMaterial, jkAngularCarousel, mdDataTable) {
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
            'jkAngularCarousel',
            'md.data.table'
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

  // api services url constant variable
  uoap.constant('ApiEndpoint', {
    url: '' /*'/journal/router'*/
  });

	uoap.run(['$rootScope', '$state', '$stateParams', 'authenticationSvc', '$location', '$timeout',
		function($rootScope, $state, $stateParams, authenticationSvc, $location, $timeout) {
		  // it'll be done when the state it resolved.
		  $rootScope.$on('$stateChangeStart', function(event, toState, toStateParams) {
			  /*$rootScope.dataLoading = true;*/
			  // track the state the user wants to go to; authorization service needs this
				$rootScope.toState = toState;
				$rootScope.toStateParams = toStateParams;

        $rootScope.userlogged = false;
        $rootScope.userInfo = {};
        // do an authorization check immediately
        // if Authentication flag has been set true in routing. otherwise load controller without checking,
        $rootScope.userInfo = authenticationSvc.getUserInfo();
        if(typeof toState.Authentication !== "undefined" && toState.Authentication === true) {
          // If user Authentication failed then redirect to login page
          if ($rootScope.userInfo !== false) {console.log("Authorized");$rootScope.userlogged = true;}
          else {event.preventDefault();console.log("Not Authorized");$state.go("login");}
        } else if(typeof toState.Authentication !== "undefined" && toState.Authentication === false) {
          if ($rootScope.userInfo === false) {console.log("Not Authorized");$rootScope.userlogged = false;}
          else {event.preventDefault();console.log("Authorized");$state.go("home");}
        }
        if($rootScope.userInfo !== false) {
          if($rootScope.userInfo.usertype.toLowerCase() == "admin") {
            $rootScope.userView = "adminHome";
          } else if($rootScope.userInfo.usertype.toLowerCase() == "reviewer") {
            $rootScope.userView = "reviewerHome";
          } else if($rootScope.userInfo.usertype.toLowerCase() == "editor") {
            $rootScope.userView = "editorHome";
          } else if($rootScope.userInfo.usertype.toLowerCase() == "editor") {
            $rootScope.userView = "userHome";
          } else {
            $rootScope.userView = "home";
          }
        }
        if($rootScope.userInfo !== false) {
          $rootScope.userlogged = true;
        }
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