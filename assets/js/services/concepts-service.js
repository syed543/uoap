/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('ConceptsService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			getConcetps: function() {
        var deferred = $q.defer();
        Http.getData('concepts/concepts.json').then(function(data){
          //Http.getData('assets/data/getAttributes.json').then(function(data){
          deferred.resolve(data);
        }).catch(function(err){});
        return deferred.promise;
			}
		};
	}]);
	return services;
});