'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial, ngMessages) {
controllers.controller("homeCtrl", ["$scope", "$rootScope", "$state", "JournalsService", "ArticlesService", "$mdDialog", "$mdSidenav", "$timeout",
  function($scope, $rootScope, $state, JournalsService, ArticlesService, $mdDialog, $mdSidenav, $timeout) {

    $scope.search = "";

    $scope.searchArticle = function() {
      alert("search triggered...");
    };

  $scope.dataArray = [{
      src: 'https://www.travelexcellence.com/images/movil/La_Paz_Waterfall.jpg',
      text: 'This is first page'
    },{
      src: 'http://www.parasholidays.in/blog/wp-content/uploads/2014/05/holiday-tour-packages-for-usa.jpg',
      text: 'This is second page'
    },{
      src: 'http://clickker.in/wp-content/uploads/2016/03/new-zealand-fy-8-1-Copy.jpg',
      text: 'This is third page'
    },{
      src: 'http://images.kuoni.co.uk/73/indonesia-34834203-1451484722-ImageGalleryLightbox.jpg',
      text: 'This is fourth page'
    }];

    JournalsService.getJournals().then(function (data) {
      if (data.statusCode == 200) { // Success
        $scope.journalList = data.data;
      } else { 					// Error
        console.log("Unable to fetch journals list. please contact support.");
      }
    });

    ArticlesService.getArticles().then(function (data) {
      if (data.statusCode == 200) { // Success
        $scope.articles = data.data;
      } else { 					// Error
        console.log("Unable to fetch articles list. please contact support.");
      }
    });

    /**
     * Supplies a function that will continue to operate until the
     * time is up.
     */
    /*function debounce(func, wait, context) {
      var timer;

      return function debounced() {
        var context = $scope,
          args = Array.prototype.slice.call(arguments);
        $timeout.cancel(timer);
        timer = $timeout(function() {
          timer = undefined;
          func.apply(context, args);
        }, wait || 10);
      };
    }*/

    /**
     * Build handler to open/close a SideNav; when animation finishes
     * report completion in console
     */
    /*function buildDelayedToggler(navID) {
      return debounce(function() {
        // Component lookup should always be available since we are not using `ng-if`
        $mdSidenav(navID)
          .toggle()
          .then(function () {

          });
      }, 200);
    }*/

  /*$scope.openSidePanel = buildDelayedToggler('left');*/
  $scope.closeSideNav = function() {
    $mdSidenav('left').close()
      .then(function () {
      });
  };

  $scope.submitMenuScript = function(ev) {
    $mdDialog.show({
      locals:{journals: $scope.journalList, articles: $scope.articles},
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

  function DialogController($scope, $mdDialog, journals, articles) {
    $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

    $scope.user = {
      name: 'Developer',
      fname: 'test',
      email: 'ipsum@lorem.com',
      postalCode: '94043',
      journal: 'ca'
    };

    $scope.journals = journals;
    $scope.articles = articles;

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

  $scope.openJournal = function(journalPageId) {
    window.open(journalPageId+'.html')
  }

  }]);
});