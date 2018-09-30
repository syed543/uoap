/*global define */
define(['angular', 'services-module'], function(angular, services) {
	'use strict';

	/* Services Global Ajax calls setup*/
services.factory('Http', ["$http", "$q", "$state", "$log", "ApiEndpoint", function ($http, $q, $state, $log, ApiEndpoint) {
		return {
			// for all GET ajax calls
			getData : function(url) {
				var d = $q.defer(),
				url = ApiEndpoint.url+url;
				$http.get(url).success(function (data, status) {
						d.resolve(data);
				}).error(function (data, status) {
					d.reject(data);
					console.log(status);
				});
				return d.promise;
			},
      // for all POST ajax calls
      postData : function(url, data) {
        var url = ApiEndpoint.url + url,
          d = $q.defer();
        if (typeof data === "undefined" || data.length <= 0) {
          $log.error("Query: missing data.");
          d.reject("Data must be provided");
        } else {
          $http.post(url, data).success(function (data, status, headers, config) {
            if(data.statusCode == "500") {
              /*displayMsg.error("Internal Server Error...");*/
            } else {
              d.resolve(data);
            }
          }).error(function (data, status, headers, config) {
            d.reject(data);
            console.log(status);
            if(status==401) $state.go('login');
          });
        }
        return d.promise;
      },
      // for all POST Multipart ajax calls
      postMultipartData : function(url, data) {
        var url = ApiEndpoint.url + url,
          d = $q.defer();
        if (typeof data === "undefined" || data.length <= 0) {
          $log.error("Query: missing data.");
          d.reject("Data must be provided");
        } else {
          $http.post(url, data, {
            headers : {
              'Content-Type' : undefined
            },
            transformRequest : angular.identity
          }).success(function (data, status, headers, config) {
            if(data.statusCode == "500") {
              /*displayMsg.error("Internal Server Error...");*/
            } else {
              d.resolve(data);
            }
          }).error(function (data, status, headers, config) {
            d.reject(data);
            console.log(status);
            if(status==401) $state.go('login');
          });
        }
        return d.promise;
      },
      downloadFile : function(url) {
        var url = ApiEndpoint.url + url;
        //window.location = url;
          window.open(url);
      }
		};
	}]);
	return services;
});