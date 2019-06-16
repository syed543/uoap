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

    $scope.$watch(function() {
      return $rootScope.selectedTab;
    }, function() {
      if($rootScope.selectedTab == 'article') {
          $scope.refreshArticles();
      };
    });

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
    $scope.updateArticleState = function(article) {
      ArticlesService.updateArticleState(article['id']).then(function(data) {
          if (data.statusCode == 200) { // Success
              $scope.refreshArticles();
          } else { 					// Error
              console.log("Unable to delete Article. please contact support.");
          }
      });
    };
    
    $scope.articleTypes = [
                           {"id": 1, "name": "Research Article"},
                           {"id": 2, "name": "Review Article"},
                           {"id": 3, "name": "Case Report"},
                           {"id": 4, "name": "Mini Reivew Article"},
                           {"id": 5, "name": "Commentary"},
                           {"id": 6, "name": "Letter to Editor"},
                           {"id": 7, "name": "Perspective"},
                           {"id": 8, "name": "Rapid Communication"},
                           {"id": 9, "name": "Short Communication"},
                           {"id": 10, "name": "Editorial"},
                           {"id": 11, "name": "Proceedings"},
                           {"id": 12, "name": "Expert Review"},
                           {"id": 13, "name": "Opinion"},
                           {"id": 14, "name": "Special Issue Article"},
                           {"id": 15, "name": "Case Series"},
                           {"id": 16, "name": "Scientific Letter"},
                           {"id": 17, "name": "Thesis"},
                           {"id": 18, "name": "Surgical Technique"},
                           {"id": 19, "name": "Image Article"},
                           {"id": 20, "name": "Book Review"}
                           ];

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
            'authors': '',
            'articleType': '',
            'showOnDetailsPage': false
        };
        $scope.file = '';
        $scope.hide = function() {
            $mdDialog.hide();
        };

        $scope.cancel = function() {
            $mdDialog.cancel();
        };
        $scope.journals = parentScope.journals;
        $scope.articleTypes = parentScope.articleTypes;
        $scope.versions = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,
                            32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50];
        $scope.issues = [1,2,3,4,5,6,7,8,9,10,11,12];

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
            _data['issueNo'] = $scope.article['issueNo'];
            _data['version'] = $scope.article['version'];
            _data['articleType'] = $scope.article['articleType'];
            _data['showOnDetailsPage'] = $scope.article['showOnDetailsPage'];

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