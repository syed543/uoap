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

    $scope.refreshMenuScripts = function() {
      _getMenuScripts();
    };

    var _getMenuScripts = function() {
      var _filterBy;
      if($scope.userType === 'reviewer') {
        _filterBy = {
          "email" : $rootScope.userInfo.email
        }
      }
      MenuScriptsService.getMenuScripts(_filterBy).then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.desserts = data;
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
    $scope.view = function(item) {
        $scope.menuScriptItem = item;
        $scope.toggleEdit();
        if($scope.menuScriptItem.status && $scope.menuScriptItem.status.toLowerCase() == 'open') {
            $scope.canEdit = true;
        }
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
          var data = {},
              fd = new FormData();

          fd.append("file", $scope.file);

          data['menu_title'] = $scope.menuScriptItem['menu_title'];
          data['abstract'] = $scope.menuScriptItem['abstract'];
          data['menuScript_id'] = $scope.menuScriptItem['menuScript_id'];

          fd.append("data", JSON.stringify(data));
          MenuScriptsService.updateMenuScript(fd).then(function(data) {
              $scope.refreshMenuScripts();
              $scope.toggleEdit();
              angular.element("input[type='file']").val(null);
              $scope.file = '';
          });
          console.log("submit script...");
      };

      $scope.cancel = function() {
          $scope.toggleEdit();
      };
  
}]);
});