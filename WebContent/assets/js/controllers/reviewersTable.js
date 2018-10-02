'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("reviewersTableCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'ReviewersService', '$mdDialog', '$rootScope', 'ArticlesService',
  function($mdEditDialog, $q, $scope, $timeout, ReviewersService, $mdDialog, $rootScope, ArticlesService) {

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

    $scope.refreshReviewers = function() {
      _getReviewers();
    };

    ArticlesService.getCountries().then(function (data) {
        if (data.statusCode == 200) { // Success
            $scope.countries = data.body.countryList;
        } else { 					// Error
            console.log("Unable to fetch articles list. please contact support.");
        }
    });

    $scope.addReviewerDialog = function(ev) {
      $mdDialog.show({
        controller: addReviewerController,
        locals: {countries: $scope.countries},
        templateUrl: 'assets/views/addReviewerDialog.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
      }).then(function() {
      }, function() {
      });
    };

    function addReviewerController($scope, $mdDialog, countries) {
      $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

      $scope.reviewer = {
        'first_Name': '',
        'last_Name': '',
        'email': '',
        'country': ''
      };
      $scope.countries = countries;

      $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };

      $scope.addReviewer = function() {
        ReviewersService.addReviewer($scope.reviewer).then(function (data) {
          if (data.statusCode == 200) { // Success
            _getReviewers();
          } else { 					// Error
            console.log("Unable to add Journal. please contact support.");
          }
          $mdDialog.cancel();
        });
      };
    }

    var _getReviewers = function() {
      ReviewersService.getReviewers().then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.desserts = data;
        } else { 					// Error
          console.log("Unable to fetch journals list. please contact support.");
        }
      });
    };
    _getReviewers();

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

    $scope.reviewerObj = {};
    $scope.view = function(item) {
        $scope.reviewerObj = item;
        $scope.toggleEdit();
    };
    $scope.cancel = function() {
        $scope.toggleEdit();
    };
    $scope.updateReviewer = function() {
        ReviewersService.updateReviewer($scope.reviewerObj).then(function (data) {
            if (data.statusCode == 200) { // Success
                $scope.refreshReviewers();
                $scope.toggleEdit();
            } else { 					// Error
                console.log("Unable to update reviewer. please contact support.");
            }
        });
    }
    $scope.deleteReviewer = function(reviewer) {
        ReviewersService.deleteReviewer(reviewer.email).then(function (data) {
            if (data.statusCode == 200) { // Success
                $scope.refreshReviewers();
            } else { 					// Error
                console.log("Unable to update reviewer. please contact support.");
            }
            $mdDialog.cancel();
        });
    }
}]);
});