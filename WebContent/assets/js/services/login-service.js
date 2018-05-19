/*global define */
define(['angular', 'services-module','underscore'], function(angular, services) {
  'use strict';

  /* Services */
  // Authentication service it will call when authentication required
  services.factory("authenticationSvc",["Http","$q","$sessionStorage","$state", function(Http, $q, $sessionStorage, $state) {
    return	{
      // Login to check authentication from service
      login: function(data) {
        var deferred = $q.defer();
        Http.postData('userLogin', data).then(function(result){
          //Http.postData('assets/data/login.json', data).then(function(result){
          deferred.resolve(result);
        }).catch(function(err){});
        return deferred.promise;
      },
      // Forgot Password recovery from service
      forgot: function(data) {
        var deferred = $q.defer();
        Http.postData('forgotPassword', data).then(function(result){
          deferred.resolve(result);
        }).catch(function(err){});
        return deferred.promise;
      },
      // Logout to kill the session and redirect to login page
      logout: function() {
        if(typeof $sessionStorage["userInfo"] != "undefined" && typeof $sessionStorage["userInfo"].emailid != "undefined") {
          $sessionStorage.$reset(); // Remove localstorage data
          $state.go('login'); // Redirect to login page
        }
      },
      // get userinfo from local variable
      getUserInfo: function() {
        return (typeof $sessionStorage["userInfo"] != "undefined" && typeof $sessionStorage["userInfo"].emailid != "undefined") ? $sessionStorage["userInfo"] : false;
      }
    };
  }]);
  return services;
});