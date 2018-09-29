'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("articlesTableCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'ArticlesService', '$rootScope',
  function($mdEditDialog, $q, $scope, $timeout, ArticlesService, $rootScope) {

    $scope.selected = [];
    $scope.limitOptions = [5, 10, 15];
    $scope.userType = $rootScope.userInfo.usertype.toLowerCase();
    $scope.options = {
      rowSelection: false,
      multiSelect: false,
      autoSelect: true,
      decapitate: false,
      largeEditDialog: false,
      boundaryLinks: false,
      limitSelect: true,
      pageSelect: true
    };

    $scope.query = {
      order: 'name',
      limit: 5,
      page: 1
    };

    $scope.refreshArticles = function() {
      _getArticles();
    };

    var _getArticles = function() {
      var _filterBy;
      if($scope.userType === 'author') {
        _filterBy = {
          "email" : $rootScope.userInfo.email
        }
      }
      ArticlesService.getArticles(_filterBy).then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.desserts = data;
        } else { 					// Error
          console.log("Unable to fetch articles list. please contact support.");
        }
      });
    };
    _getArticles();

    $scope.toggleLimitOptions = function () {
      $scope.limitOptions = $scope.limitOptions ? undefined : [5, 10, 15];
    };

    $scope.logItem = function (item) {
      console.log(item.name, 'was selected');
    };

    $scope.logOrder = function (order) {
      console.log('order: ', order);
    };

    $scope.logPagination = function (page, limit) {
      console.log('page: ', page);
      console.log('limit: ', limit);
    };

    $scope.view = function(article) {
      ArticlesService.downloadArticle(article['article_id']);
    };
  
}]);
});