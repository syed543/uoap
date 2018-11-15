/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('JournalsService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			getJournals: function() {
                var deferred = $q.defer();
               // Http.getData('assets/data/journals.json').then(function(data){
                Http.getData('/journals').then(function (data) {
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
			},
            addJournal: function(data) {
                var deferred = $q.defer();
                //Http.getData('assets/data/journals.json', data).then(function(data){
                Http.postMultipartData('/addJournal', data).then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            getJournalById: function(id) {
                var deferred = $q.defer();
                Http.getData('assets/data/getJournalById.json?Id='+id).then(function(data){
                //Http.getData('assets/data/getJournalById.json').then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            updateJournal: function(data) {
                var deferred = $q.defer();
                //Http.getData('assets/data/journals.json', data).then(function(data){
                Http.postMultipartData('/updateJournal', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            deleteJournal: function(journalId) {
                var deferred = $q.defer();
                /*HTTP.postData('assets/data/journals.json', journalId).then(function(data){*/
                Http.getData('assets/data/journals.json', journalId).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});