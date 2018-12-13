/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('MenuScriptsService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
		    getMenuScripts: function(filterBy) {
                var deferred = $q.defer(),
                    // serviceUrl = 'assets/data/menuScriptList.json';
                    serviceUrl = '/menuScriptList';
                if(filterBy) {
                  for(var key in filterBy) {
                    serviceUrl += "?"+key+"="+filterBy[key];
                  }
                }
                Http.getData(serviceUrl).then(function(data){
                /*Http.getData('assets/data/menuScriptList.json').then(function(data){*/
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
            submitMenuScript: function(menuScriptItem) {
                var deferred = $q.defer();
                Http.postMultipartData('/addMenuScript', menuScriptItem).then(function(data){
                /*Http.getData('assets/data/menuScriptItem.json', menuScriptItem).then(function(data){*/
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            rejectMenuScript: function(menuScriptItem) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/menuScriptItem', menuScriptItem).then(function(data){*/
                Http.getData('assets/data/menuScriptItem.json', menuScriptItem).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            approveMenuScript: function(menuScriptItem) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/menuScriptItem', menuScriptItem).then(function(data){*/
                Http.getData('assets/data/menuScriptItem.json', menuScriptItem).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            acceptReview: function(menuScriptId) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/menuScriptItem', menuScriptId).then(function(data){*/
                Http.getData('assets/data/menuScriptItem.json', menuScriptId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            rejectReview: function(menuScriptId) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/menuScriptItem', menuScriptId).then(function(data){*/
                Http.getData('assets/data/menuScriptItem.json', menuScriptId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            updateMenuScript: function(menuScriptItem, id) {
                var deferred = $q.defer();
                Http.postData('/updateMenuScript/'+id, menuScriptItem).then(function(data){
                /*Http.getData('assets/data/menuScriptItem.json', menuScriptItem).then(function(data){*/
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            updateMenuScriptMultipart: function(menuScriptItem, id) {
                var deferred = $q.defer();
                Http.postMultipartData('/updateMenuScript/'+id, menuScriptItem).then(function(data){
                /*Http.getData('assets/data/menuScriptItem.json', menuScriptItem).then(function(data){*/
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            deleteMenuScript: function(menuScriptId) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/menuScriptItem', menuScriptId).then(function(data){*/
                /*Http.getData('assets/data/menuScriptItem.json', menuScriptId).then(function(data){*/
                Http.getData('/deleteMenuScript/'+menuScriptId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});