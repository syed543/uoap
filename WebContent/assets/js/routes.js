
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
          })
        .state('adminHome', {
          url: '/adminHome',
          name: 'adminHome',
          templateUrl: 'assets/views/adminHome.html',
          controller: 'adminHomeCtrl',
          Authentication: true
        })
        .state('editorHome', {
          url: '/editorHome',
          name: 'editorHome',
          templateUrl: 'assets/views/editorHome.html',
          controller: 'editorHomeCtrl',
          Authentication: true
        })
        .state('reviewerHome', {
          url: '/reviewerHome',
          name: 'reviewerHome',
          templateUrl: 'assets/views/reviewerHome.html',
          controller: 'reviewerHomeCtrl',
          Authentication: true
        })
        .state('userHome', {
          url: '/userHome',
          name: 'userHome',
          templateUrl: 'assets/views/userHome.html',
          controller: 'userHomeCtrl',
          Authentication: true
        });

        $urlRouterProvider.otherwise('home');
    } ]);
});
