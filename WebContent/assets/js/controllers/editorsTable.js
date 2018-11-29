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

    JournalsService.getJournals().then(function (data) {
        if (data.statusCode == 200) { // Success
            $scope.journals = data.data;
        } else { 					// Error
            console.log("Unable to fetch journals list. please contact support.");
        }
    });

    $scope.addEditorDialog = function(ev) {
      $mdDialog.show({
        controller: addEditorController,
        locals: {parentScope: $scope},
        templateUrl: 'assets/views/addEditorDialog.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
      }).then(function() {
      }, function() {
      });
    };

    function addEditorController($scope, $mdDialog, parentScope) {
      $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

      $scope.editor = {
        'firstName': '',
        'lastName': '',
        'email': '',
        'avatar': '',
        'description': '',
        'affiliation': '',
        'journalId': ''
      };
      $scope.journals = parentScope.journals;

      $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };
      $scope.file = '';
      $scope.uploadedFile = function(element) {
          $scope.$apply(function($scope) {
              $scope.file = element.files[0];
          });
      };

      $scope.addEditor = function() {
        var data = {},
            fd = new FormData(),
            editorObj = {};
        fd.append("file", $scope.file);

         data['firstName'] = $scope.editor['firstName'];
         data['lastName'] = $scope.editor['lastName'];
         data['email'] = $scope.editor['email'];
         data['description'] = $scope.editor['description'];
         data['affiliation'] = $scope.editor['affiliation'];
         data['journalId'] = $scope.editor['journal'];

        fd.append("data", JSON.stringify(data));
        EditorsService.addEditor(fd).then(function (data) {
          if (data.statusCode == 200) { // Success
              parentScope.refreshEditors();
          } else { 					// Error
            console.log("Unable to add Journal. please contact support.");
          }
          $mdDialog.cancel();
        });
      };
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
    };

    $scope.canEdit = false;
    $scope.inEditMode = false;
    $scope.toggleEdit = function() {
        $scope.inEditMode = !$scope.inEditMode;
    };
    $scope.editor = {};
    $scope.view = function(item) {
        $scope.editor = item;
        $scope.toggleEdit();
    };
    $scope.cancel = function() {
        $scope.toggleEdit();
    };
    $scope.file = '';
    $scope.uploadedFile = function(element) {
        $scope.$apply(function($scope) {
            $scope.file = element.files[0];
        });
    };
    $scope.updateEditor = function() {
        var data = {},
            fd = new FormData(),
            editorObj = {};
        fd.append("file", $scope.file);

        data['firstName'] = $scope.editor['firstName'];
        data['lastName'] = $scope.editor['lastName'];
        data['email'] = $scope.editor['email'];
        data['description'] = $scope.editor['description'];
        data['affiliation'] = $scope.editor['affiliation'];
        data['journalId'] = $scope.editor['journalId'];

        fd.append("data", JSON.stringify(data));
        EditorsService.updateEditor(fd).then(function (data) {
            if (data.statusCode == 200) { // Success
                $scope.refreshEditors();
                $scope.toggleEdit();
            } else { 					// Error
                console.log("Unable to update Editor. please contact support.");
            }
        });
    }
    $scope.deleteEditor = function(editor) {
        EditorsService.deleteEditor(editor.email).then(function (data) {
            if (data.statusCode == 200) { // Success
                $scope.refreshEditors();
            } else { 					// Error
                console.log("Unable to delete Editor. please contact support.");
            }
            $mdDialog.cancel();
        });
    }
  
}]);
});