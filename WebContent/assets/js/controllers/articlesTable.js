'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("articlesTableCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'ArticlesService', '$rootScope', '$mdDialog','JournalsService',
  function($mdEditDialog, $q, $scope, $timeout, ArticlesService, $rootScope, $mdDialog, JournalsService) {

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
      if($scope.userType === 'author' || $scope.userType === 'submitter') {
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

    JournalsService.getJournals().then(function (data) {
      if (data.statusCode == 200) { // Success
          $scope.journals = data.data;
      } else { 					// Error
          console.log("Unable to fetch journals list. please contact support.");
      }
    });

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
      ArticlesService.downloadArticle(article['id']);
    };

    $scope.delete = function(article) {
      ArticlesService.deleteArticle(article['id']).then(function(data) {
          if (data.statusCode == 200) { // Success
              $scope.refreshArticles();
          } else { 					// Error
              console.log("Unable to delete Article. please contact support.");
          }
      });
    };

    $scope.addArticleDialog = function(ev) {
        $mdDialog.show({
            controller: addArticleController,
            locals: {parentScope: $scope},
            templateUrl: 'assets/views/addArticleDialog.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        }).then(function() {
        }, function() {
        });
    }

    function addArticleController($scope, $mdDialog, parentScope) {

        $scope.article = {
            'article_title': '',
            'article_abstract': '',
            'authors': ''
        };
        $scope.file = '';
        $scope.hide = function() {
            $mdDialog.hide();
        };

        $scope.cancel = function() {
            $mdDialog.cancel();
        };
        $scope.journals = parentScope.journals;

        $scope.uploadedFile = function(element) {
            $scope.$apply(function($scope) {
                $scope.file = element.files[0];
            });
        };

        $scope.submitArticle = function() {
            var fd = new FormData(),
                _data = {};
            _data['title'] = $scope.article['title'];
            _data['abstractDesc'] = $scope.article['abstractDesc'];
            _data['journalId'] = $scope.article['journalId'];
            _data['authors'] = $scope.article['authors'];

            fd.append("file", $scope.file);
            //angular.copy($scope.article, data);

            fd.append("data", JSON.stringify(_data));

            ArticlesService.addArticle(fd).then(function (data) {
                if (data.statusCode == 200) { // Success
                    parentScope.refreshArticles();
                } else { 					// Error
                    console.log("Unable to add Journal. please contact support.");
                }
                $mdDialog.cancel();
            });
        };
    }
  
}]);
});