/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('ReviewersService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			getReviewers: function() {
                var deferred = $q.defer();
                Http.getData('assets/data/reviewers.json').then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            addReviewer: function(data) {
				var deferred = $q.defer();
				/*HTTP.postData('assets/data/journals.json', data).then(function(data){*/
                Http.getData('assets/data/reviewers.json', data).then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            updateReviewer: function(data) {
                var deferred = $q.defer();
                /*HTTP.postData('assets/data/journals.json', data).then(function(data){*/
                Http.getData('assets/data/reviewers.json', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            deleteReviewer: function(reviewerId) {
                var deferred = $q.defer();
                /*HTTP.postData('assets/data/journals.json', reviewerId).then(function(data){*/
                Http.getData('assets/data/reviewers.json', reviewerId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});