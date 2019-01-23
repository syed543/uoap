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
                Http.getData('/getArticles').then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            getArticlesByJournalId: function(Id) {
                var deferred = $q.defer();
                Http.getData('/getArticlesByJournalId', Id).then(function(data){
                /*Http.getData('assets/data/articles.json').then(function(data){*/
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
            },
            addArticle: function(data) {
                var deferred = $q.defer();
                //Http.getData('assets/data/articles.json', data).then(function(data){
                Http.postMultipartData('/addArticle', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            deleteArticle: function(articleId) {
                var deferred = $q.defer();
                /*Http.getData('assets/data/articles.json', articleId).then(function(data){*/
                Http.getData('/deleteArticle/'+articleId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            updateArticleState: function(articleId) {
                var deferred = $q.defer();
                /*Http.getData('assets/data/articles.json', articleId).then(function(data){*/
                Http.getData('/updateArticleState/'+articleId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});