'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("articlesCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'ArticlesService',
  function($mdEditDialog, $q, $scope, $timeout, ArticlesService) {


    $scope.refreshArticles = function() {
      _getArticles();
    };

    var _getArticles = function() {
      ArticlesService.getArticles().then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.articles = data.data;
        } else { 					// Error
          console.log("Unable to fetch articles list. please contact support.");
        }
      });
    };
    _getArticles();
  
}]);
});