/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('FeatureService', ["$http", "$q", "$state", "$log", function ($http, $q, $state, $log) {
		return {
			setFeature: function (feature) {
				this.feature = feature;
			},
			getFeature: function() {
				return this.feature;
			}
		};
	}]);
	return services;
});