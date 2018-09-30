/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('ArticlesService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			getArticles: function(filterBy) {
                var deferred = $q.defer(),
                  serviceUrl = 'assets/data/menuScriptList.json';
                if(filterBy) {
                  for(var key in filterBy) {
                    serviceUrl += "?"+key+"="+filterBy[key];
                  }
                }
                /*Http.getData(serviceUrl).then(function(data){*/
                Http.getData('assets/data/articles.json').then(function(data){
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
            getCountries: function() {
                var deferred = $q.defer();
                /*Http.getData(serviceUrl).then(function(data){*/
                Http.getData('assets/data/countrysList.json').then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            downloadArticle: function(articleId) {
                Http.downloadFile('/downloadArticle?id='+articleId);
            }
		};
	}]);
	return services;
});