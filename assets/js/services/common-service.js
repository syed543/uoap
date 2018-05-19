/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

	/* Services Global Ajax calls setup*/
services.factory('Http', ["$http", "$q", "$state", "$log", function ($http, $q, $state, $log) {
		return {
			// for all GET ajax calls
			getData : function(url) {
				var d = $q.defer();
				$http.get(url).success(function (data, status) {
						d.resolve(data);
				}).error(function (data, status) {
					d.reject(data);
					console.log(status);
				});
				return d.promise;
			}
		};
	}]);
	return services;
});