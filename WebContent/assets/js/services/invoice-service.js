/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

services.factory('InvoiceService', ["Http", "$q", "$state", "$log", function (Http, $q, $state, $log) {
		return {
			generateInvoice: function(data) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/addArticle', data).then(function(data){*/
                Http.postData('/generatesecureurl', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            verifytoken: function(token) {
                var deferred = $q.defer();
                Http.getData('/verifytoken', token).then(function(data){
                  deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            },
            updateInvoice: function(data) {
                var deferred = $q.defer();
                /*Http.postMultipartData('/addArticle', data).then(function(data){*/
                HTTP.postData('/updatePayment', data).then(function(data){
                    deferred.resolve(data);
                }).catch(function(err){});
                return deferred.promise;
            }
		};
	}]);
	return services;
});