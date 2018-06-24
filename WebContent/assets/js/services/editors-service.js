/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('EditorsService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			getEditors: function() {
        var deferred = $q.defer();
        Http.getData('assets/data/editors.json').then(function(data){
          deferred.resolve(data);
        }).catch(function(err){});
        return deferred.promise;
			},
      addEditor: function(data) {
				var deferred = $q.defer();
				/*HTTP.postData('assets/data/journals.json', data).then(function(data){*/
        Http.getData('assets/data/editors.json', data).then(function(data){
          deferred.resolve(data);
        }).catch(function(err){});
        return deferred.promise;
			}
		};
	}]);
	return services;
});