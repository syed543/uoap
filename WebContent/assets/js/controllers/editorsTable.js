'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("editorsTableCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'EditorsService', '$mdDialog', 'JournalsService',
  function($mdEditDialog, $q, $scope, $timeout, EditorsService, $mdDialog, JournalsService) {

    $scope.selected = [];
    $scope.limitOptions = [5, 10, 15];

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

    $scope.refreshEditors = function() {
      _getEditors();
    };

    $scope.addEditorDialog = function(ev) {
      $mdDialog.show({
        controller: addEditorController,
        templateUrl: 'assets/views/addEditorDialog.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
      }).then(function() {
      }, function() {
      });
    };

    function addEditorController($scope, $mdDialog) {
      $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

      $scope.editor = {
        'first_Name': '',
        'last_Name': '',
        'email': '',
        'avatar': '',
        'description': '',
        'affiliation': '',
        'journal': ''
      };

      $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };

      $scope.addEditor = function() {
        EditorsService.addEditor().then(function (data) {
          if (data.statusCode == 200) { // Success
            _getEditors();
          } else { 					// Error
            console.log("Unable to add Journal. please contact support.");
          }
          $mdDialog.cancel();
        });
      };

      JournalsService.getJournals().then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.journals = data.data;
        } else { 					// Error
          console.log("Unable to fetch journals list. please contact support.");
        }
      });
    }

    var _getEditors = function() {
      EditorsService.getEditors().then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.desserts = data;
        } else { 					// Error
          console.log("Unable to fetch journals list. please contact support.");
        }
      });
    };
    _getEditors();

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
  
}]);
});