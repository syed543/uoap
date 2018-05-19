
/**
 * Router Config
 * This is the router definition that defines all application routes.
 */
/*global define */
define([ 'angular', 'controllers/main', 'angular-ui-router' ], function (angular, controllers) {
    
    'use strict';
    
    return angular.module('app.routes', [ 'ui.router' ]).config([ "$stateProvider", "$urlRouterProvider", "$locationProvider", function ($stateProvider, $urlRouterProvider, $locationProvider) {

        //Turn on or off HTML5 mode which uses the # hash
        $locationProvider.html5Mode(false);
          /**
           * Router paths
           * This is where the name of the route is matched to the controller and view template.
		    current no abstarct route is set...
           */
      $stateProvider
           .state('home', {
            url:'/home',
             name: 'home',
            templateUrl: 'assets/views/home.html',
            controller: 'homeCtrl'
           })
           .state('login', {
            url: '/login',
             name: 'login',
            templateUrl: 'assets/views/login.html',
            controller: 'loginCtrl'
           })
          .state('concepts', {
            url: '/concepts?concept',
            name: 'concepts',
            templateUrl: 'assets/views/conceptsdetail.html',
            controller: 'conceptsCtrl'
          });

          
        $urlRouterProvider.otherwise('home');
    } ]);
});
