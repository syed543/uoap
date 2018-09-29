/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('MenuScriptsService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
		    getMenuScripts: function(filterBy) {
                var deferred = $q.defer(),
                    serviceUrl = 'assets/data/menuScriptList.json';
                if(filterBy) {
                  for(var key in filterBy) {
                    serviceUrl += "?"+key+"="+filterBy[key];
                  }
                }
                /*Http.getData(serviceUrl).then(function(data){*/
                Http.getData('assets/data/menuScriptList.json').then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            getArticlesByJournalId: function(Id) {
                var deferred = $q.defer();
                /*Http.getData('assets/data/getArticlesByJournalId.json?Id='+id).then(function(data){*/
                Http.getData('assets/data/articles.json').then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            updateMenuScript: function(menuScriptItem) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/menuScriptItem', data).then(function(data){*/
                Http.getData('assets/data/menuScriptItem.json', menuScriptItem).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});