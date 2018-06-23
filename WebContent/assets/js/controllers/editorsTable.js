'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("journalsTableCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'JournalsService', '$mdDialog',
  function($mdEditDialog, $q, $scope, $timeout, JournalsService, $mdDialog) {

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

    $scope.refreshJournal = function() {
      _getJournals();
    };

    $scope.addJournalDialog = function(ev) {
      $mdDialog.show({
        controller: addJournalController,
        templateUrl: 'assets/views/addJournalDialog.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
      }).then(function() {
      }, function() {
      });
    };

    function addJournalController($scope, $mdDialog) {
      $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

      $scope.journal = {
        'journal_name': null,
        'journal_icon': null,
        'journal_description': '',
        'journal_long_description': '',
        'journal_banner_image': ''
      };

      $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };

      $scope.addJournal = function() {
        JournalsService.addJournal().then(function (data) {
          if (data.statusCode == 200) { // Success
            _getJournals();
          } else { 					// Error
            console.log("Unable to add Journal. please contact support.");
          }
          $mdDialog.cancel();
        });
      };
    }

    var _getJournals = function() {
      JournalsService.getJournals().then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.desserts = data;
        } else { 					// Error
          console.log("Unable to fetch journals list. please contact support.");
        }
      });
    };
    _getJournals();

    $scope.editComment = function (event, dessert) {
      event.stopPropagation(); // in case autoselect is enabled

      var editDialog = {
        modelValue: dessert.comment,
        placeholder: 'Add a comment',
        save: function (input) {
          if(input.$modelValue === 'Donald Trump') {
            input.$invalid = true;
            return $q.reject();
          }
          if(input.$modelValue === 'Bernie Sanders') {
            return dessert.comment = 'FEEL THE BERN!'
          }
          dessert.comment = input.$modelValue;
        },
        targetEvent: event,
        title: 'Add a comment',
        validators: {
          'md-maxlength': 30
        }
      };

      var promise;

      if($scope.options.largeEditDialog) {
        promise = $mdEditDialog.large(editDialog);
      } else {
        promise = $mdEditDialog.small(editDialog);
      }

      promise.then(function (ctrl) {
        var input = ctrl.getInput();

        input.$viewChangeListeners.push(function () {
          input.$setValidity('test', input.$modelValue !== 'test');
        });
      });
    };

    $scope.toggleLimitOptions = function () {
      $scope.limitOptions = $scope.limitOptions ? undefined : [5, 10, 15];
    };

    $scope.getTypes = function () {
      return ['Candy', 'Ice cream', 'Other', 'Pastry'];
    };

    $scope.loadStuff = function () {
      $scope.promise = $timeout(function () {
        // loading
      }, 2000);
    }

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