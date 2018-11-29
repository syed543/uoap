/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('EditorsService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			getEditors: function() {
                var deferred = $q.defer();
                Http.getData('/editors').then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            addEditor: function(data) {
				var deferred = $q.defer();
                /*Http.postMultipartData('/addEditor', data).then(function(data){*/
                Http.postMultipartData('/addEditor', data).then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            updateEditor: function(data) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/addEditor', data).then(function(data){*/
                Http.getData('/updateEditor', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            deleteEditor: function(editorId) {
                var deferred = $q.defer();
                /*HTTP.postData('assets/data/journals.json', reviewerId).then(function(data){*/
                Http.getData('/deleteEditor?editorId='+editorId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});