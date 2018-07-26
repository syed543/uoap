/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('MenuScriptService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			submitMenuScript: function(data) {
                var deferred = $q.defer();
                Http.postMultipartData('/addMenuScript', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            listMenuScript: function() {
				var deferred = $q.defer();
				HTTP.getData('/listMenuScript', data).then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            updateMenuScript: function(data) {
                var deferred = $q.defer();
                Http.postMultipartData('/updateMenuScript', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});