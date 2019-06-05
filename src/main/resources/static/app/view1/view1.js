'use strict';

angular.module('myApp.view1', ['ngRoute','myApp.listing'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', ['Listings', '$scope', function View1Ctrl(Listings, $scope) {
  $scope.makeQuery = "";
  $scope.listings = Listings.query();
}]);