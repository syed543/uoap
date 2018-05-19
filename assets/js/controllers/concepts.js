'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("conceptsCtrl", ["$scope", "$rootScope", "$state", "ConceptsService","$stateParams", "$filter", function($scope, $rootScope, $state, ConceptsService,$stateParams, $filter) {

  $scope.selectedConcept = $state.params.concept;

  ConceptsService.getConcetps().then(function (data) {
    $scope.concepts = data;
    updateView();
  });

  $scope.closeAPI = function() {
    $state.go('home');
  };
  $scope.expandView = false;
  $scope.expandAPI = function() {
    $scope.expandView = true;
  };
  $scope.collapseAPI = function() {
    $scope.expandView = false;
  };

  $scope.updateConcept = function(concept) {
    $scope.selectedConcept.selected = false;
    $scope.selectedConcept = concept;
    $scope.selectedConcept.selected = true;
    /*$state.go('concepts', {concept : concept.title});*/
  };

  function updateView() {
    $filter('filter')($scope.concepts, {'title': $state.params.concept}, true)[0].selected = true;
    $scope.selectedConcept = $filter('filter')($scope.concepts, {'title': $state.params.concept}, true)[0];
  }

}]);
});