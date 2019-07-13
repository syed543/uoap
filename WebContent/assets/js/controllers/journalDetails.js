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

  var _journalId = window.location.search.split('=')[1];
  //console.log(_journalId);
  	$scope.states = [{
  		"stateName": "In Press",
  		"stateValue": "1"
  	},{
  		"stateName": "Current Issue",
  		"stateValue": "2"
  	},{
  		"stateName": "Archive",
  		"stateValue": "3"
  	}];
  	$scope.stateValue = "1";
  	$scope.handleStateChange = function(state) {
  		console.log(state);
  		ArticlesService.getArticlesOnDetailsPageById(_journalId, state).then(function (data) {
  	      if (data.statusCode == 200) { // Success
  	        $scope.articles = data.data;
  	      } else { 					// Error
  	        console.log("Unable to fetch articles list. please contact support.");
  	      }
  	    });
  	};
  	
    JournalsService.getJournalById(_journalId).then(function (data) {
      if (data.statusCode == 200) { // Success
        $scope.journal = data.data;
        $scope.setJournalBanner();
      } else { 					// Error
        console.log("Unable to fetch journal. please contact support.");
      }
    });
    $scope.setJournalBanner = function() {
    	var bannerImage = $scope.journal.journalBannerImageFileName.split("\\").join("/"),
    		iconImage = $scope.journal.journalIconFileName.split("\\").join("/");
    		
    	$('.journal-details-banner').css('background-image', 'url("'+bannerImage+'")');
    	$('.submit-menuScript-container').css('background-image', 'url("'+iconImage+'")');
    };
    
    /*ArticlesService.getArticlesByJournalId(_journalId).then(function (data) {
      if (data.statusCode == 200) { // Success
        $scope.articles = data.data;
      } else { 					// Error
        console.log("Unable to fetch articles list. please contact support.");
      }
    });*/
		ArticlesService.getArticlesOnDetailsPageById(_journalId, "1").then(function (data) {
	  	      if (data.statusCode == 200) { // Success
	  	        $scope.articles = data.data;
	  	      } else { 					// Error
	  	        console.log("Unable to fetch articles list. please contact support.");
	  	      }
	  	    });

    EditorsService.getEditorsByJournalId(_journalId).then(function (data) {
        if (data.statusCode == 200) { // Success
            $scope.editors = data.data;
        } else { 					// Error
            console.log("Unable to fetch editors list. please contact support.");
        }
    });
    
    $scope.downloadArticle = function(article) {
    	ArticlesService.downloadArticle(article['id']);
    };

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