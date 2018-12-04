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
        'journal_banner_image': '',
        'journal_abbrev':''
      };

      $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };
      
      $scope.uploadedFile = function(element) {
		$scope.$apply(function($scope) {
		   $scope.icon = element.files[0];
		});			
	  };
	  
	  $scope.uploadedBanner = function(element) {
			$scope.$apply(function($scope) {
			   $scope.banner = element.files[0];
			});			
		};
           

      $scope.addJournal = function() {
          var data = {},
              fd = new FormData();
          	
          	fd.append("icon", $scope.icon);
          	fd.append("banner", $scope.banner);
          
          data['journal_name'] = $scope.journal['journal_name'];
          data['journal_description'] = $scope.journal['journal_description'];
          data['journal_long_description'] = $scope.journal['journal_long_description'];
          data['id'] = $scope.journal['id'];

          fd.append("data", JSON.stringify(data));
          
        JournalsService.addJournal(fd).then(function (data) {
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

    $scope.canEdit = false;
    $scope.inEditMode = false;
    $scope.toggleEdit = function() {
        $scope.inEditMode = !$scope.inEditMode;
    };
    $scope.journal = {};
    $scope.view = function(item) {
        $scope.journal = item;
        $scope.toggleEdit();
    };
    $scope.cancel = function() {
        $scope.toggleEdit();
    };
    $scope.icon = '';
    $scope.banner = '';

    $scope.uploadedFile = function(element) {
        $scope.$apply(function($scope) {
            $scope.icon = element.files[0];
        });
    };

    $scope.uploadedBanner = function(element) {
        $scope.$apply(function($scope) {
            $scope.banner = element.files[0];
        });
    };
    $scope.updateJournal = function() {
        var data = {},
            fd = new FormData(),
            journalId = $scope.journal['journalId'];

        fd.append("icon", $scope.icon);
        fd.append("banner", $scope.banner);

        data['journal_name'] = $scope.journal['journal_name'];
        data['journal_description'] = $scope.journal['journal_description'];
        data['journal_long_description'] = $scope.journal['journal_long_description'];

        fd.append("data", JSON.stringify(data));

        JournalsService.updateJournal(fd).then(function (data, journalId) {
            if (data.statusCode == 200) { // Success
                $scope.refreshJournal();
                $scope.toggleEdit();
            } else { 					// Error
                console.log("Unable to update Journal. please contact support.");
            }
        });
    };
    $scope.deleteJournal = function(journal) {
        JournalsService.deleteJournal(journal.id).then(function (data) {
            if (data.statusCode == 200) { // Success
                $scope.refreshJournal();
            } else { 					// Error
                console.log("Unable to delete Journal. please contact support.");
            }
        });
    };
  
}]);
});