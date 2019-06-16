'use strict';
/** 
  * controller for user profile
*/
define(['angular',
        'controllers-module',
		'angular-material'
        ], function(angular, controllers, ngMaterial, ngMessages) {
controllers.controller("homeCtrl", ["$scope", "$rootScope", "$state", "JournalsService", "ArticlesService", "$mdDialog", "$mdSidenav", "$timeout", "MenuScriptsService","$mdToast", 
  function($scope, $rootScope, $state, JournalsService, ArticlesService, $mdDialog, $mdSidenav, $timeout, MenuScriptsService, $mdToast) {

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
    $scope.countries = [
          {"name":"Afghanistan","abbrev":"AF"},
          {"name":"Åland Islands","abbrev":"AX"},
          {"name":"Albania","abbrev":"AL"},
          {"name":"Algeria","abbrev":"DZ"},
          {"name":"American Samoa","abbrev":"AS"},
          {"name":"Andorra","abbrev":"AD"},
          {"name":"Angola","abbrev":"AO"},
          {"name":"Anguilla","abbrev":"AI"},
          {"name":"Antarctica","abbrev":"AQ"},
          {"name":"Antigua and Barbuda","abbrev":"AG"},
          {"name":"Argentina","abbrev":"AR"},
          {"name":"Armenia","abbrev":"AM"},
          {"name":"Aruba","abbrev":"AW"},
          {"name":"Australia","abbrev":"AU"},
          {"name":"Austria","abbrev":"AT"},
          {"name":"Azerbaijan","abbrev":"AZ"},
          {"name":"Bahamas","abbrev":"BS"},
          {"name":"Bahrain","abbrev":"BH"},
          {"name":"Bangladesh","abbrev":"BD"},
          {"name":"Barbados","abbrev":"BB"},
          {"name":"Belarus","abbrev":"BY"},
          {"name":"Belgium","abbrev":"BE"},
          {"name":"Belize","abbrev":"BZ"},
          {"name":"Benin","abbrev":"BJ"},
          {"name":"Bermuda","abbrev":"BM"},
          {"name":"Bhutan","abbrev":"BT"},
          {"name":"Bolivia, Plurinational State of","abbrev":"BO"},
          {"name":"Bonaire, Sint Eustatius and Saba","abbrev":"BQ"},
          {"name":"Bosnia and Herzegovina","abbrev":"BA"},
          {"name":"Botswana","abbrev":"BW"},
          {"name":"Bouvet Island","abbrev":"BV"},
          {"name":"Brazil","abbrev":"BR"},
          {"name":"British Indian Ocean Territory","abbrev":"IO"},
          {"name":"Brunei Darussalam","abbrev":"BN"},
          {"name":"Bulgaria","abbrev":"BG"},
          {"name":"Burkina Faso","abbrev":"BF"},
          {"name":"Burundi","abbrev":"BI"},
          {"name":"Cambodia","abbrev":"KH"},
          {"name":"Cameroon","abbrev":"CM"},
          {"name":"Canada","abbrev":"CA"},
          {"name":"Cape Verde","abbrev":"CV"},
          {"name":"Cayman Islands","abbrev":"KY"},
          {"name":"Central African Republic","abbrev":"CF"},
          {"name":"Chad","abbrev":"TD"},
          {"name":"Chile","abbrev":"CL"},
          {"name":"China","abbrev":"CN"},
          {"name":"Christmas Island","abbrev":"CX"},
          {"name":"Cocos (Keeling) Islands","abbrev":"CC"},
          {"name":"Colombia","abbrev":"CO"},
          {"name":"Comoros","abbrev":"KM"},
          {"name":"Congo","abbrev":"CG"},
          {"name":"Congo, the Democratic Republic of the","abbrev":"CD"},
          {"name":"Cook Islands","abbrev":"CK"},
          {"name":"Costa Rica","abbrev":"CR"},
          {"name":"Côte d'Ivoire","abbrev":"CI"},
          {"name":"Croatia","abbrev":"HR"},
          {"name":"Cuba","abbrev":"CU"},
          {"name":"Curaçao","abbrev":"CW"},
          {"name":"Cyprus","abbrev":"CY"},
          {"name":"Czech Republic","abbrev":"CZ"},
          {"name":"Denmark","abbrev":"DK"},
          {"name":"Djibouti","abbrev":"DJ"},
          {"name":"Dominica","abbrev":"DM"},
          {"name":"Dominican Republic","abbrev":"DO"},
          {"name":"Ecuador","abbrev":"EC"},
          {"name":"Egypt","abbrev":"EG"},
          {"name":"El Salvador","abbrev":"SV"},
          {"name":"Equatorial Guinea","abbrev":"GQ"},
          {"name":"Eritrea","abbrev":"ER"},
          {"name":"Estonia","abbrev":"EE"},
          {"name":"Ethiopia","abbrev":"ET"},
          {"name":"Falkland Islands (Malvinas)","abbrev":"FK"},
          {"name":"Faroe Islands","abbrev":"FO"},
          {"name":"Fiji","abbrev":"FJ"},
          {"name":"Finland","abbrev":"FI"},
          {"name":"France","abbrev":"FR"},
          {"name":"French Guiana","abbrev":"GF"},
          {"name":"French Polynesia","abbrev":"PF"},
          {"name":"French Southern Territories","abbrev":"TF"},
          {"name":"Gabon","abbrev":"GA"},
          {"name":"Gambia","abbrev":"GM"},
          {"name":"Georgia","abbrev":"GE"},
          {"name":"Germany","abbrev":"DE"},
          {"name":"Ghana","abbrev":"GH"},
          {"name":"Gibraltar","abbrev":"GI"},
          {"name":"Greece","abbrev":"GR"},
          {"name":"Greenland","abbrev":"GL"},
          {"name":"Grenada","abbrev":"GD"},
          {"name":"Guadeloupe","abbrev":"GP"},
          {"name":"Guam","abbrev":"GU"},
          {"name":"Guatemala","abbrev":"GT"},
          {"name":"Guernsey","abbrev":"GG"},
          {"name":"Guinea","abbrev":"GN"},
          {"name":"Guinea-Bissau","abbrev":"GW"},
          {"name":"Guyana","abbrev":"GY"},
          {"name":"Haiti","abbrev":"HT"},
          {"name":"Heard Island and McDonald Islands","abbrev":"HM"},
          {"name":"Holy See (Vatican City State)","abbrev":"VA"},
          {"name":"Honduras","abbrev":"HN"},
          {"name":"Hong Kong","abbrev":"HK"},
          {"name":"Hungary","abbrev":"HU"},
          {"name":"Iceland","abbrev":"IS"},
          {"name":"India","abbrev":"IN"},
          {"name":"Indonesia","abbrev":"ID"},
          {"name":"Iran, Islamic Republic of","abbrev":"IR"},
          {"name":"Iraq","abbrev":"IQ"},
          {"name":"Ireland","abbrev":"IE"},
          {"name":"Isle of Man","abbrev":"IM"},
          {"name":"Israel","abbrev":"IL"},
          {"name":"Italy","abbrev":"IT"},
          {"name":"Jamaica","abbrev":"JM"},
          {"name":"Japan","abbrev":"JP"},
          {"name":"Jersey","abbrev":"JE"},
          {"name":"Jordan","abbrev":"JO"},
          {"name":"Kazakhstan","abbrev":"KZ"},
          {"name":"Kenya","abbrev":"KE"},
          {"name":"Kiribati","abbrev":"KI"},
          {"name":"Korea, Democratic People's Republic of","abbrev":"KP"},
          {"name":"Korea, Republic of","abbrev":"KR"},
          {"name":"Kuwait","abbrev":"KW"},
          {"name":"Kyrgyzstan","abbrev":"KG"},
          {"name":"Lao People's Democratic Republic","abbrev":"LA"},
          {"name":"Latvia","abbrev":"LV"},
          {"name":"Lebanon","abbrev":"LB"},
          {"name":"Lesotho","abbrev":"LS"},
          {"name":"Liberia","abbrev":"LR"},
          {"name":"Libya","abbrev":"LY"},
          {"name":"Liechtenstein","abbrev":"LI"},
          {"name":"Lithuania","abbrev":"LT"},
          {"name":"Luxembourg","abbrev":"LU"},
          {"name":"Macao","abbrev":"MO"},
          {"name":"Macedonia, the former Yugoslav Republic of","abbrev":"MK"},
          {"name":"Madagascar","abbrev":"MG"},
          {"name":"Malawi","abbrev":"MW"},
          {"name":"Malaysia","abbrev":"MY"},
          {"name":"Maldives","abbrev":"MV"},
          {"name":"Mali","abbrev":"ML"},
          {"name":"Malta","abbrev":"MT"},
          {"name":"Marshall Islands","abbrev":"MH"},
          {"name":"Martinique","abbrev":"MQ"},
          {"name":"Mauritania","abbrev":"MR"},
          {"name":"Mauritius","abbrev":"MU"},
          {"name":"Mayotte","abbrev":"YT"},
          {"name":"Mexico","abbrev":"MX"},
          {"name":"Micronesia, Federated States of","abbrev":"FM"},
          {"name":"Moldova, Republic of","abbrev":"MD"},
          {"name":"Monaco","abbrev":"MC"},
          {"name":"Mongolia","abbrev":"MN"},
          {"name":"Montenegro","abbrev":"ME"},
          {"name":"Montserrat","abbrev":"MS"},
          {"name":"Morocco","abbrev":"MA"},
          {"name":"Mozambique","abbrev":"MZ"},
          {"name":"Myanmar","abbrev":"MM"},
          {"name":"Namibia","abbrev":"NA"},
          {"name":"Nauru","abbrev":"NR"},
          {"name":"Nepal","abbrev":"NP"},
          {"name":"Netherlands","abbrev":"NL"},
          {"name":"New Caledonia","abbrev":"NC"},
          {"name":"New Zealand","abbrev":"NZ"},
          {"name":"Nicaragua","abbrev":"NI"},
          {"name":"Niger","abbrev":"NE"},
          {"name":"Nigeria","abbrev":"NG"},
          {"name":"Niue","abbrev":"NU"},
          {"name":"Norfolk Island","abbrev":"NF"},
          {"name":"Northern Mariana Islands","abbrev":"MP"},
          {"name":"Norway","abbrev":"NO"},
          {"name":"Oman","abbrev":"OM"},
          {"name":"Pakistan","abbrev":"PK"},
          {"name":"Palau","abbrev":"PW"},
          {"name":"Palestinian Territory, Occupied","abbrev":"PS"},
          {"name":"Panama","abbrev":"PA"},
          {"name":"Papua New Guinea","abbrev":"PG"},
          {"name":"Paraguay","abbrev":"PY"},
          {"name":"Peru","abbrev":"PE"},
          {"name":"Philippines","abbrev":"PH"},
          {"name":"Pitcairn","abbrev":"PN"},
          {"name":"Poland","abbrev":"PL"},
          {"name":"Portugal","abbrev":"PT"},
          {"name":"Puerto Rico","abbrev":"PR"},
          {"name":"Qatar","abbrev":"QA"},
          {"name":"Réunion","abbrev":"RE"},
          {"name":"Romania","abbrev":"RO"},
          {"name":"Russian Federation","abbrev":"RU"},
          {"name":"Rwanda","abbrev":"RW"},
          {"name":"Saint Barthélemy","abbrev":"BL"},
          {"name":"Saint Helena, Ascension and Tristan da Cunha","abbrev":"SH"},
          {"name":"Saint Kitts and Nevis","abbrev":"KN"},
          {"name":"Saint Lucia","abbrev":"LC"},
          {"name":"Saint Martin (French part)","abbrev":"MF"},
          {"name":"Saint Pierre and Miquelon","abbrev":"PM"},
          {"name":"Saint Vincent and the Grenadines","abbrev":"VC"},
          {"name":"Samoa","abbrev":"WS"},
          {"name":"San Marino","abbrev":"SM"},
          {"name":"Sao Tome and Principe","abbrev":"ST"},
          {"name":"Saudi Arabia","abbrev":"SA"},
          {"name":"Senegal","abbrev":"SN"},
          {"name":"Serbia","abbrev":"RS"},
          {"name":"Seychelles","abbrev":"SC"},
          {"name":"Sierra Leone","abbrev":"SL"},
          {"name":"Singapore","abbrev":"SG"},
          {"name":"Sint Maarten (Dutch part)","abbrev":"SX"},
          {"name":"Slovakia","abbrev":"SK"},
          {"name":"Slovenia","abbrev":"SI"},
          {"name":"Solomon Islands","abbrev":"SB"},
          {"name":"Somalia","abbrev":"SO"},
          {"name":"South Africa","abbrev":"ZA"},
          {"name":"South Georgia and the South Sandwich Islands","abbrev":"GS"},
          {"name":"South Sudan","abbrev":"SS"},
          {"name":"Spain","abbrev":"ES"},
          {"name":"Sri Lanka","abbrev":"LK"},
          {"name":"Sudan","abbrev":"SD"},
          {"name":"Suriname","abbrev":"SR"},
          {"name":"Svalbard and Jan Mayen","abbrev":"SJ"},
          {"name":"Swaziland","abbrev":"SZ"},
          {"name":"Sweden","abbrev":"SE"},
          {"name":"Switzerland","abbrev":"CH"},
          {"name":"Syrian Arab Republic","abbrev":"SY"},
          {"name":"Taiwan, Province of China","abbrev":"TW"},
          {"name":"Tajikistan","abbrev":"TJ"},
          {"name":"Tanzania, United Republic of","abbrev":"TZ"},
          {"name":"Thailand","abbrev":"TH"},
          {"name":"Timor-Leste","abbrev":"TL"},
          {"name":"Togo","abbrev":"TG"},
          {"name":"Tokelau","abbrev":"TK"},
          {"name":"Tonga","abbrev":"TO"},
          {"name":"Trinidad and Tobago","abbrev":"TT"},
          {"name":"Tunisia","abbrev":"TN"},
          {"name":"Turkey","abbrev":"TR"},
          {"name":"Turkmenistan","abbrev":"TM"},
          {"name":"Turks and Caicos Islands","abbrev":"TC"},
          {"name":"Tuvalu","abbrev":"TV"},
          {"name":"Uganda","abbrev":"UG"},
          {"name":"Ukraine","abbrev":"UA"},
          {"name":"United Arab Emirates","abbrev":"AE"},
          {"name":"United Kingdom","abbrev":"GB"},
          {"name":"United States","abbrev":"US"},
          {"name":"United States Minor Outlying Islands","abbrev":"UM"},
          {"name":"Uruguay","abbrev":"UY"},
          {"name":"Uzbekistan","abbrev":"UZ"},
          {"name":"Vanuatu","abbrev":"VU"},
          {"name":"Venezuela, Bolivarian Republic of","abbrev":"VE"},
          {"name":"Viet Nam","abbrev":"VN"},
          {"name":"Virgin Islands, British","abbrev":"VG"},
          {"name":"Virgin Islands, U.S.","abbrev":"VI"},
          {"name":"Wallis and Futuna","abbrev":"WF"},
          {"name":"Western Sahara","abbrev":"EH"},
          {"name":"Yemen","abbrev":"YE"},
          {"name":"Zambia","abbrev":"ZM"},
          {"name":"Zimbabwe","abbrev":"ZW"}
      ];
    
    $scope.articleTypes = [
                           {"id": 1, "name": "Research Article"},
                           {"id": 2, "name": "Review Article"},
                           {"id": 3, "name": "Case Report"},
                           {"id": 4, "name": "Mini Reivew Article"},
                           {"id": 5, "name": "Commentary"},
                           {"id": 6, "name": "Letter to Editor"},
                           {"id": 7, "name": "Perspective"},
                           {"id": 8, "name": "Rapid Communication"},
                           {"id": 9, "name": "Short Communication"},
                           {"id": 10, "name": "Editorial"},
                           {"id": 11, "name": "Proceedings"},
                           {"id": 12, "name": "Expert Review"},
                           {"id": 13, "name": "Opinion"},
                           {"id": 14, "name": "Special Issue Article"},
                           {"id": 15, "name": "Case Series"},
                           {"id": 16, "name": "Scientific Letter"},
                           {"id": 17, "name": "Thesis"},
                           {"id": 18, "name": "Surgical Technique"},
                           {"id": 19, "name": "Image Article"},
                           {"id": 20, "name": "Book Review"}
                           ];
    /*ArticlesService.getCountries().then(function (data) {
      if (data.statusCode == 200) { // Success
        $scope.countries = data.body.countryList;
      } else { 					// Error
        console.log("Unable to fetch articles list. please contact support.");
      }
    });*/

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
      locals:{journals: $scope.journalList, articles: $scope.articles, countries: $scope.countries, articleTypes: $scope.articleTypes},
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

  function DialogController($scope, $rootScope, $mdDialog, journals, articles, countries, articleTypes) {
    $scope.emailFormat = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;

    $scope.user = {
        country: "",
        email : "",
        fName : "",
        lName : "",
        postalAddress : "",
        title : "",
        articleType: ""
    };
    if($rootScope.userInfo.email) {
      $scope.user.country = $rootScope.userInfo.country;
      $scope.user.email = $rootScope.userInfo.email;
      $scope.user.fName = $rootScope.userInfo.fName;
      $scope.user.lName = $rootScope.userInfo.lName;
      $scope.user.postalAddress = $rootScope.userInfo.postalAddress;
      $scope.user.title = $rootScope.userInfo.title;
      $scope.user.journalId = $rootScope.userInfo.journal;
    }
    /*$scope.user.country = 'IN';*/
    $scope.uploadedFile = function(element) {
        $scope.$apply(function($scope) {
            $scope.file = element.files[0];
        });
    };

    $scope.journals = journals;
    $scope.articles = articles;
    $scope.countries = countries;
    $scope.articleTypes = articleTypes;

    $scope.hide = function() {
      $mdDialog.hide();
    };

    $scope.cancel = function() {
      $mdDialog.cancel();
    };

    $scope.submitScript = function() {
        var data = {},
            fd = new FormData();
        fd.append("attachment", $scope.file);

        fd.append("data", JSON.stringify($scope.user));
        MenuScriptsService.submitMenuScript(fd).then(function(data) {
          console.log('submitted...');
          $scope.cancel();
          if(data.statusCode == 200) {
        	  $mdToast.show($mdToast.simple().textContent(data.message));
          } else if(data.message) {
        	  $mdToast.show($mdToast.simple().textContent(data.message));  
          }
          
        });
    };
  }

  $scope.openJournal = function(journal) {
	var _name = journal.journal_name.split(' ').join('_');
    window.open(_name+'.html?id='+journal.id);
  }

  }]);
});