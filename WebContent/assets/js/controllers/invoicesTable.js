'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial) {
controllers.controller("invoicesTableCtrl", ['$mdEditDialog', '$q', '$scope', '$timeout', 'InvoiceService','$rootScope','$mdDialog',
  function($mdEditDialog, $q, $scope, $timeout, InvoiceService, $rootScope, $mdDialog) {

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

    $scope.$watch(function() {
      return $rootScope.selectedTab;
    }, function() {
      if($rootScope.selectedTab == 'invoice') {
          $scope.refreshArticles();
      };
    });

    $scope.refreshArticles = function() {
      //_getArticles();
    };

    /*var _getArticles = function() {
      var _filterBy;
      if($scope.userType === 'author' || $scope.userType === 'submitter') {
        _filterBy = {
          "email" : $rootScope.userInfo.email
        }
      }
      ArticlesService.getArticles(_filterBy).then(function (data) {
        if (data.statusCode == 200) { // Success
          $scope.desserts = data;
        } else { 					// Error
          console.log("Unable to fetch articles list. please contact support.");
        }
      });
    };
    _getArticles();*/

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

    /*$scope.delete1 = function(article) {
      ArticlesService.deleteArticle(article['id']).then(function(data) {
          if (data.statusCode == 200) { // Success
              $scope.refreshArticles();
          } else { 					// Error
              console.log("Unable to delete Article. please contact support.");
          }
      });
    };*/
    $scope.countries = [{"name":"US Dollar","abbrev":"USD"},
                        {"name":"Euros","abbrev":"EUR"},
                        {"name":"Pounds","abbrev":"GBP"}];

    $scope.addInvoiceDialog = function(ev) {
        $mdDialog.show({
            controller: addInvoiceController,
            locals: {parentScope: $scope},
            templateUrl: 'assets/views/addInvoiceDialog.html',
            parent: angular.element(document.body),
            targetEvent: ev,
            clickOutsideToClose:true,
            fullscreen: $scope.customFullscreen // Only for -xs, -sm breakpoints.
        }).then(function() {
        }, function() {
        });
    }

    function addInvoiceController($scope, $mdDialog, parentScope) {

        $scope.invoice = {
            'userId': '',
            'articleName': '',
            'amount': '',
            'journalName': '',
            'authorName': '',
            'authorEmailId': '',
            'currencyCode': ''
        };
        $scope.file = '';
        $scope.hide = function() {
            $mdDialog.hide();
        };
        
        $scope.generatedUrl = '';

        $scope.cancel = function() {
            $mdDialog.cancel();
        };
        $scope.currencyCodes = [{"name":"US Dollar","abbrev":"USD"},
                            {"name":"Euros","abbrev":"EUR"},
                            {"name":"Pounds","abbrev":"GBP"},
                            {"name":"Indian Rupees","abbrev":"INR"}];

        $scope.submitInvoice = function() {
        	var data;
            angular.copy($scope.invoice, data);
            /*$scope.invoice = {
                    'userId': '',
                    'articleNumber': '',
                    'articleName': '',
                    'amount': '',
                    'journalName': '',
                    'authorName': '',
                    'authorEmailId': '',
                    'currencyCode': ''
                };*/
            InvoiceService.generateInvoice($scope.invoice).then(function (data) {
                if (data.statusCode == 200) { // Success
                	$scope.generatedUrl = data.generatedUrl;
                } else { 					// Error
                    console.log("Unable to generate Url. please contact support.");
                }
                //$mdDialog.cancel();
            });
        };
    }
  
}]);
});