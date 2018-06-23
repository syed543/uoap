/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('ArticlesService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			getArticles: function() {
        var deferred = $q.defer();
        Http.getData('assets/data/articles.json').then(function(data){
          deferred.resolve(data);
        }).catch(function(err){});
        return deferred.promise;
			}
		};
	}]);
	return services;
});