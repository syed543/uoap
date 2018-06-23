'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("loginCtrl", ["$scope", "$rootScope", "$state", "$stateParams", "$localStorage", "$sessionStorage", "authenticationSvc",
  function($scope, $rootScope, $state, $stateParams, $localStorage, $sessionStorage, authenticationSvc) {

  // Login form array element
  $scope.loginInfo = {
    emailid: '',
    password: ''
  };
  // Forgot password array element
  $scope.forgotInfo = {
    emailid: ''
  };
  // error handling variable
  $scope.errorstatus = false;
  $scope.statusmsg = "";
  // page redirection variable
  $scope.loginPage = true;
  $scope.forgotPage = false;
  $scope.forgotmsgPage = false;
  $scope.submit = false;
  // page redirection methods for inner screens
  $scope.forgotPassword = function(){
    $scope.submit = false;
    $scope.loginPage = false;
    $scope.forgotmsgPage = false;
    $scope.forgotPage = true;
  }
  $scope.showloginPage = function(){
    $scope.submit = false;
    $scope.forgotPage = false;
    $scope.forgotmsgPage = false;
    $scope.loginPage = true;
  }
  $scope.showforgotmsgPage = function(){
    $scope.submit = false;
    $scope.forgotPage = false;
    $scope.loginPage = false;
    $scope.forgotmsgPage = true;
  }
  // Login form submission function
  $scope.login = function(form){
    $scope.submit = true;
    var firstError = null;
    if (form.$invalid) {
      // Form error handling
      var field = null, firstError = null;
      for (field in form) {
        if (field[0] != '$') {
          if (firstError === null && !form[field].$valid) {
            firstError = form[field].$name;
          }
          if (form[field].$pristine) {
            form[field].$dirty = true;
          }
        }
      }
      // auto focus error element
      angular.element('.ng-invalid[name=' + firstError + ']').focus();
      return;
    } else {
      // send data to login service
      authenticationSvc.login($scope.loginInfo, false).then(function(data){
        var userInfo = {}; // user variable
        if(data.statusCode == 200){ // Success
          userInfo = data.body;
          $sessionStorage.$reset();
          // set local session
          $sessionStorage["userInfo"] = userInfo;
          $scope.errorstatus = false;
          $rootScope.userlogged = true;
          $scope.statusmsg = data.statusMsg;
          if(userInfo.type.toLowerCase() === "admin") {
            $state.go('adminHome');
          } else {
            $state.go('home'); // Redirect to dashboard after login successful
          }
        } else { 					// Error
          userInfo = {};
          $sessionStorage.$reset();
          $scope.errorstatus = true;
          $scope.statusmsg = data.statusMsg;
        }
      },function(){});
      return;
    }
  }
  // Forgot Password function
  $scope.forgot = function(form){
    $scope.submit = true;
    var firstError = null;
    if (form.$invalid) {
      // Form error handling
      var field = null, firstError = null;
      for (field in form) {
        if (field[0] != '$') {
          if (firstError === null && !form[field].$valid) {
            firstError = form[field].$name;
          }

          if (form[field].$pristine) {
            form[field].$dirty = true;
          }
        }
      }
      angular.element('.ng-invalid[name=' + firstError + ']').focus();
      return;
    } else {
      // send data to forgot password service
      authenticationSvc.forgot($scope.forgotInfo, false).then(function(data){
        if(data.statusCode == 200){ // Success
          $sessionStorage.$reset();
          $scope.errorstatus = false;
          $scope.statusmsg = "";
          $scope.showforgotmsgPage();
        } else { 					// Error
          $sessionStorage.$reset();
          $scope.errorstatus = true;
          $scope.statusmsg = data.statusMsg;
        }
      },function(){});
      return;
    }
  }

  
}]);
});