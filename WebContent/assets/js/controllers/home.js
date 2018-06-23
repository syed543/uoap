'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial, ngMessages) {
controllers.controller("homeCtrl", ["$scope", "$rootScope", "$state", "FeatureService", "ConceptsService", "$mdDialog", "$mdSidenav", "$timeout",
  function($scope, $rootScope, $state, FeatureService, ConceptsService, $mdDialog, $mdSidenav, $timeout) {

  function checkUserLogin() {
    if ($rootScope.userInfo) {
      if ($rootScope.userInfo.usertype.toLowerCase() == "admin") {
        $timeout($state.go("adminHome"), 0);
      } else if ($rootScope.userInfo.usertype.toLowerCase() == "reviewer") {
        $state.go("reviewerHome");
      } else if ($rootScope.userInfo.usertype.toLowerCase() == "editor") {
        $state.go("editorHome");
      } else if ($rootScope.userInfo.usertype.toLowerCase() == "editor") {
        $state.go("userHome");
      } else {
        $state.go("home");
      }
    }
  }

    //checkUserLogin();

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

  $scope.journalList = [{
    imagePath: 'https://www.travelexcellence.com/images/movil/La_Paz_Waterfall.jpg',
    name: 'Biochemistry and Modern Applications',
    description: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. ' +
    'Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took ' +
    'a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ' +
    'but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the ' +
    '1960s with the release of Letraset sheets containing Lorem Ipsum passages, ' +
    'and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'
  },
    {
      imagePath: 'https://www.travelexcellence.com/images/movil/La_Paz_Waterfall.jpg',
      name: 'Clinical Cardiology and Cardiovascular Medicine',
      description: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. ' +
      'Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took ' +
      'a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ' +
      'but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the ' +
      '1960s with the release of Letraset sheets containing Lorem Ipsum passages, ' +
      'and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'
    },
    {
      imagePath: 'https://www.travelexcellence.com/images/movil/La_Paz_Waterfall.jpg',
      name: 'Dental research and Management',
      description: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. ' +
      'Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took ' +
      'a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ' +
      'but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the ' +
      '1960s with the release of Letraset sheets containing Lorem Ipsum passages, ' +
      'and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'
    },
    {
      imagePath: 'https://www.travelexcellence.com/images/movil/La_Paz_Waterfall.jpg',
      name: 'Dental research and Management ...',
      description: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. ' +
      'Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took ' +
      'a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, ' +
      'but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the ' +
      '1960s with the release of Letraset sheets containing Lorem Ipsum passages, ' +
      'and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.'
    }];

    /**
     * Supplies a function that will continue to operate until the
     * time is up.
     */
    function debounce(func, wait, context) {
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
    }

    /**
     * Build handler to open/close a SideNav; when animation finishes
     * report completion in console
     */
    function buildDelayedToggler(navID) {
      return debounce(function() {
        // Component lookup should always be available since we are not using `ng-if`
        $mdSidenav(navID)
          .toggle()
          .then(function () {

          });
      }, 200);
    }

  $scope.openSidePanel = buildDelayedToggler('left');
    $scope.closeSideNav = function() {
      $mdSidenav('left').close()
        .then(function () {
        });
    };

    $scope.submitMenuScript = function(ev) {
      $mdDialog.show({
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
    }

    function DialogController($scope, $mdDialog) {
      $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

      $scope.user = {
        name: 'Developer',
        fname: 'test',
        email: 'ipsum@lorem.com',
        postalCode: '94043',
        journal: 'ca'
      };

      $scope.journals = [{
        name: 'journal1',
        abbrev: 'JA1'
      },{
        name: 'journal2',
        abbrev: 'JA2'
      },{
        name: 'journal3',
        abbrev: 'JA3'
      }];

      $scope.articles = [{
        name: 'article1',
        abbrev: 'AR1'
      },{
        name: 'article2',
        abbrev: 'AR2'
      },{
        name: 'article3',
        abbrev: 'AR3'
      }];

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