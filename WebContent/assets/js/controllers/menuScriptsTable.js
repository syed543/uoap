'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("menuScriptsTableCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'MenuScriptsService', '$rootScope',
  function($mdEditDialog, $q, $scope, $timeout, MenuScriptsService, $rootScope) {

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
    $scope.canEdit = false;
    $scope.query = {
      order: 'name',
      limit: 5,
      page: 1
    };
    $scope.$watch(function() {
      return $rootScope.selectedTab;
    }, function() {
      if($rootScope.selectedTab == 'menuscript') {
          $scope.refreshMenuScripts();
      };
    });
    $scope.refreshMenuScripts = function() {
      _getMenuScripts();
    };

    var _getMenuScripts = function() {
      var _filterBy;
      /*if($scope.userType === 'reviewer' || $scope.userType === 'author') {*/
        _filterBy = {
          "email" : $rootScope.userInfo.email
        }
      /*}*/
      MenuScriptsService.getMenuScripts(_filterBy).then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.desserts = data;

          for(var index=0; index < data.data.length; index++) {

              if (data['data'][index].status == 1) {
                  $scope.desserts['data'][index].statusText = 'Open';
              } else if (data['data'][index].status == 2) {
                  $scope.desserts['data'][index].statusText = 'Assigned';
              } else if (data['data'][index].status == 3) {
                  $scope.desserts['data'][index].statusText = 'In-review';
              } else if (data['data'][index].status == 4) {
                  $scope.desserts['data'][index].statusText = 'Approved';
              } else {
                  $scope.desserts['data'][index].statusText = 'Rejected';
              }
          }
        } else { 					// Error
          console.log("Unable to fetch articles list. please contact support.");
        }
      });
    };
    _getMenuScripts();

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
    }

    $scope.menuScriptItem = {};
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
    $scope.view = function(item) {
        $scope.menuScriptItem = item;

        MenuScriptsService.getReviewersByJournalId($scope.menuScriptItem.journal).then(function (data) {
            if (data.statusCode == 200) { // Success
                $scope.reviewers = data.data;

                $scope.toggleEdit();
                if($scope.menuScriptItem.status && $scope.menuScriptItem.status == 1) {
                    $scope.canEdit = true;
                } else {
                    $scope.canEdit = false;
                }
            } else { 					// Error
                console.log("Unable to fetch articles list. please contact support.");
            }
        });
        
        $scope.articleType = _.findWhere($scope.articleTypes, {id: $scope.menuScriptItem.articleType});
        
    };

      $scope.inEditMode = false;
      $scope.toggleEdit = function() {
          $scope.inEditMode = !$scope.inEditMode;
      };
      $scope.uploadedFile = function(element) {
          $scope.$apply(function($scope) {
              $scope.file = element.files[0];
          });
      };
      $scope.submitScript = function() {
          /*if($scope.userType === 'author' || $scope.userType === 'submitter' || $scope.userType === 'admin') {*/
              var data = {},
                  fd = new FormData();

              var _id = $scope.menuScriptItem['id'];
              if($scope.userType === 'admin' || $scope.userType === 'editor') {
                  data['feedback'] = $scope.menuScriptItem['feedback'];
                  data['reviewers'] = $scope.menuScriptItem['reviewerId'];
              } else if($scope.userType === 'reviewer') {
                  data['feedback'] = $scope.menuScriptItem['feedback'];
              } else {
                  fd.append("file", $scope.file);
                  data['menuTitle'] = $scope.menuScriptItem['menuTitle'];
                  data['abstractTitle'] = $scope.menuScriptItem['abstractTitle'];
              }

              fd.append("data", JSON.stringify(data));
              MenuScriptsService.updateMenuScriptMultipart(fd, _id).then(function (data) {
                  $scope.refreshMenuScripts();
                  $scope.toggleEdit();
                  angular.element("input[type='file']").val(null);
                  $scope.file = '';
              });
          /*} else {
              var data = {};

              data['feedback'] = $scope.menuScriptItem['feedback'];
              data['reviewerId'] = $scope.menuScriptItem['reviewerId'];

              var _id = $scope.menuScriptItem['id'];

              MenuScriptsService.updateMenuScript(data, _id).then(function (data) {
                  $scope.refreshMenuScripts();
                  $scope.toggleEdit();
                  angular.element("input[type='file']").val(null);
                  $scope.file = '';
              });
          }*/
      };

      $scope.rejectMenuScript = function() {
          $scope.menuScriptItem['status'] = 'Reject'
          MenuScriptsService.rejectMenuScript($scope.menuScriptItem).then(function (data) {
              $scope.refreshMenuScripts();
              $scope.toggleEdit();
          });
      };

      $scope.approveMenuScript = function() {
          $scope.menuScriptItem['status'] = 'Approve'
          MenuScriptsService.approveMenuScript($scope.menuScriptItem).then(function (data) {
              $scope.refreshMenuScripts();
              $scope.toggleEdit();
          });
      };

      $scope.acceptReview = function(menuScript) {
          MenuScriptsService.acceptReview(menuScript.id).then(function (data) {
              $scope.refreshMenuScripts();
          });
      };

      $scope.rejectReview = function(menuScript) {
          MenuScriptsService.rejectReview(menuScript.id).then(function (data) {
              $scope.refreshMenuScripts();
          });
      };

      $scope.delete = function(menuScript) {
          MenuScriptsService.deleteMenuScript(menuScript.id).then(function (data) {
              $scope.refreshMenuScripts();
          });
      };

      $scope.cancel = function() {
          $scope.toggleEdit();
      };
  
}]);
});