/* global requirejs, define */

'use strict';
/**
 * This file sets up the basic module libraries you'll need
 * for your application.
 */
requirejs.onError = function(err) {
    //console.log(err.requireType);
    if (err.requireType === 'timeout') {
        //console.error('modules: ' + err.requireModules);
    }
    throw err;
};
/**
 * RequireJS Config
 * This is configuration for the entire application.
 */
require.config({
    enforceDefine : false,
    xhtml : false,    
	//Cache buster
    //urlArgs : '_=' + Date.now(),
    waitSeconds : 15,
    config : {
        text : {
            env : 'xhr'
        }
    },
    paths : {
        
       directives : './directives',
        // Named References
        config: './config',
        app: './app',

        //Angular App Modules
        'controllers-module': 'controllers/module',
        'directives-module': 'directives/module',
        'services-module': 'services/module',

        // angularjs + modules
         angular: 'vendors/angular/angular.min',
        'angular-resource': 'vendors/angular-resource/angular-resource',
        'angular-ui-router': 'vendors/angular-ui-router/angular-ui-router.min',
        'ngStorage': 'vendors/ngstorage/ngStorage.min',
        underscore: 'vendors/underscore/underscore-min',
        jquery: 'vendors/jquery/jquery-1.11.3',
        bootstrap: 'vendors/bootstrap/js/bootstrap',
        'angular-animate': 'vendors/angular-animate/angular-animate.min',
        'angular-aria': 'vendors/angular-aria/angular-aria.min',
        'angular-messages': 'vendors/angular-messages/angular-messages.min',
        'angular-material': 'vendors/angular-material/angular-material.min',
        'jkAngularCarousel': 'vendors/jk-carousel/jk-carousel',
        'angular-material-data-table': 'vendors/angular-material-data-table/dist/md-data-table'
    },
   
    
    priority: [
        'jquery',
        'angular',
        'angular-resource',
        'angular-ui-router',
        'ngStorage',
        'bootstrap'
    ],    
    shim : {
		'angular' : {
			deps: ['jquery'],
			exports : 'angular'
		},
		'angular-route': ['angular'],
		'angular-resource': ['angular'],
		'angular-ui-router': ['angular'],
    'ngStorage': ['angular'],
      underscore : {
        exports : '_'
      },
			'bootstrap': ['jquery'],
      'angular-animate': ['angular'],
      'angular-aria': ['angular'],
      'angular-messages': ['angular'],
      'angular-material': ['angular'],
      'jkAngularCarousel': ['angular'],
      'angular-material-data-table': ['angular'],
		bootstrapper: {
			deps: [
				'app',
				'directives-module',
				'services-module',
				'controllers-module',
				'routes'
			]
		}
    }
});
