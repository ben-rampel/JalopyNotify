'use strict';

angular.
module('myApp.listing').
factory('Listing', ['$resource',
    function($resource) {
        return $resource('http://localhost:8080/listings/:id', {}, {
            query: {
                method: 'GET',
                params: {id: 'listing'},
                isArray: false
            }
        });
    }
]).
factory('Listings', ['$resource',
    function($resource) {
        return $resource('http://localhost:8080/listings/', {}, {
            query: {
                method: 'GET',
                isArray:true
            }
        });
    }
]);