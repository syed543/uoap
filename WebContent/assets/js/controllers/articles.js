'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("articlesCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'ArticlesService', '$mdDialog', '$rootScope',
  function($mdEditDialog, $q, $scope, $timeout, ArticlesService, $mdDialog, $rootScope) {

    var _journalId = window.location.hash.split('=')[1];

    $scope.refreshArticles = function() {
      _getArticles();
    };

    var _getArticles = function() {
      if(_journalId) {
        ArticlesService.getArticlesOnDetailsPageById(_journalId, "1").then(function (data) {
          if (data.statusCode == 200) { // Success
            $scope.articles = data.data;
          } else {          // Error
            console.log("Unable to fetch articles list. please contact support.");
          }
        });
      } else {
        ArticlesService.getArticles().then(function (data) {
          if (data.statusCode == 200) { // Success
            $scope.articles = data.data;
          } else {          // Error
            console.log("Unable to fetch articles list. please contact support.");
          }
        });
      }
      
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

    $scope.states = [{
      "stateName": "In Press",
      "stateValue": "1"
    },{
      "stateName": "Current Issue",
      "stateValue": "2"
    },{
      "stateName": "Archive",
      "stateValue": "3"
    }];
    $scope.stateValue = "1";
    $scope.handleStateChange = function(state) {
      console.log(state);
      ArticlesService.getArticlesOnDetailsPageById(_journalId, state).then(function (data) {
          if (data.statusCode == 200) { // Success
            $scope.articles = data.data;
          } else {          // Error
            console.log("Unable to fetch articles list. please contact support.");
          }
        });
    };
  
}]);
});