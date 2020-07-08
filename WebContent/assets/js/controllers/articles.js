'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("articlesCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'ArticlesService', '$mdDialog',
  function($mdEditDialog, $q, $scope, $timeout, ArticlesService, $mdDialog) {


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

    $scope.downloadArticle = function(article) {
        ArticlesService.downloadArticle(article.id)
    };

    $scope.showAbstract = function(ev, article) {
    // Appending dialog to document.body to cover sidenav in docs app
    // Modal dialogs should fully cover application
    // to prevent interaction outside of dialog
    $mdDialog.show(
      $mdDialog.alert()
        .parent(angular.element(document.querySelector('#articlesList')))
        .clickOutsideToClose(true)
        .title(article.title)
        .textContent(article.abstractDesc)
        .ariaLabel('Abstract')
        .ok('Ok')
        .targetEvent(ev)
    );
  };
  
}]);
});