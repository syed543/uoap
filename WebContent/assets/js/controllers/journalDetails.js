'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("journalDetailsCtrl", ["$scope", "$rootScope", "$state", "$stateParams", "$localStorage", "$sessionStorage", "JournalsService", "ArticlesService", "authenticationSvc", "$mdDialog","EditorsService",
  function($scope, $rootScope, $state, $stateParams, $localStorage, $sessionStorage, JournalsService, ArticlesService, authenticationSvc, $mdDialog, EditorsService) {

  var urlSplit = window.location.pathname.split('/'),
      _journalId = urlSplit[urlSplit.length-1].split('.')[0];
  console.log(_journalId);

    JournalsService.getJournalById(_journalId).then(function (data) {
      if (data.statusCode == 200) { // Success
        $scope.journal = data.body;
      } else { 					// Error
        console.log("Unable to fetch journal. please contact support.");
      }
    });

    ArticlesService.getArticlesByJournalId(_journalId).then(function (data) {
      if (data.statusCode == 200) { // Success
        $scope.articles = data.data;
      } else { 					// Error
        console.log("Unable to fetch articles list. please contact support.");
      }
    });

    EditorsService.getEditorsByJournalId(_journalId).then(function (data) {
        if (data.statusCode == 200) { // Success
            $scope.editors = data.data;

            $scope.editors = [
                {
                    "avatar": "https://images.pexels.com/photos/617278/pexels-photo-617278.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                    "isChiefEditor": "true",
                    "firstName": "First",
                    "lastName": "Last",
                    "affiliation": "Affiliation",
                    "designation": "Designation",
                    "department": "Department",
                    "country": "India",
                    "contactNo": "040-45678985 Ext No: 3456"
                }
            ];
        } else { 					// Error
            console.log("Unable to fetch editors list. please contact support.");
        }
    });

      /*$scope.editors = [
          {
              "avatar": "https://images.pexels.com/photos/617278/pexels-photo-617278.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
              "isChiefEditor": "true",
              "firstName": "First",
              "lastName": "Last",
              "affiliation": "Affiliation",
              "designation": "Designation",
              "department": "Department",
              "country": "India",
              "contactNo": "040-45678985 Ext No: 3456"
          },
          {
              "avatar": "https://images.pexels.com/photos/617278/pexels-photo-617278.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
              "isChiefEditor": "true",
              "firstName": "First",
              "lastName": "Last",
              "affiliation": "Affiliation",
              "designation": "Designation",
              "department": "Department",
              "country": "India",
              "contactNo": "040-45678985 Ext No: 3456"
          }
      ];*/

    $scope.submitMenuScript = function(ev) {
      $mdDialog.show({
        locals:{journals: $scope.journal.journal_name, articles: $scope.articles, journalId: _journalId},
        controller: DialogController,
        templateUrl: 'assets/views/submit-menuscript.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose:true,
        fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
      })
        .then(function(answer) {
        }, function() {
        });
    };

    function DialogController($scope, $mdDialog, journals, articles, journalId) {
      $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

      $scope.user = {
        name: 'Developer',
        fname: 'test',
        email: 'ipsum@lorem.com',
        postalCode: '94043',
        journal: 'ca'
      };
      $scope.journalId = journals;
      $scope.articles = articles;
      $scope.user.journal = journalId;

        $scope.hide = function() {
        $mdDialog.hide();
      };

      $scope.cancel = function() {
        $mdDialog.cancel();
      };

      $scope.submitScript = function() {
        console.log("submit script...");
      };
    }
}]);
});