angular.module('ionicApp', ['ionic'])

.config(function($stateProvider, $urlRouterProvider) {

  $stateProvider
    .state('signin', {
      url: '/sign-in',
      templateUrl: 'templates/sign-in.html',
      controller: 'SignInCtrl'
    })
    .state('forgotpassword', {
      url: '/forgot-password',
      templateUrl: 'templates/forgot-password.html'
    })
    .state('tabs', {
      url: '/tab',
      abstract: true,
      templateUrl: 'templates/tabs.html'
    })
    .state('tabs.home', {
      url: '/home',
      views: {
        'home-tab': {
          templateUrl: 'templates/home.html',
          controller: 'HomeTabCtrl'
        }
      }
    })
    .state('tabs.Gallery', {
      url: '/Gallery',
      views: {
        'Gallery-tab': {
          templateUrl: 'templates/Gallery.html',
          controller: 'ExampleController'
        }
      }
    })
    .state('tabs.contact', {
      url: '/contact',
      views: {
        'contact-tab': {
          templateUrl: 'templates/contact.html'
        }
      }
    });


   $urlRouterProvider.otherwise('/sign-in');

})

.controller('SignInCtrl', function($scope, LoginService, $ionicPopup, $state) {
  $scope.user= {};
  $scope.signIn = function(user) {
    LoginService.loginUser($scope.user.username,$scope.user.password).success(function(user){
      $state.go('tabs.home');
    }).error(function(user){
      var alertPopup = $ionicPopup.alert({
        title: 'Login Failed!',
        template: 'Please check your Credentials!'
      });
    });
 
  }
  
})

.controller('HomeTabCtrl', function($scope) {
  console.log('HomeTabCtrl');
})
.controller('ExampleController',function($scope)
 {
$scope.images = [];
$scope.loadImages = function() {
for(var i = 0; i < 100; i++) {
$scope.images.push({id : i, src : "http://placehold.it/50x50"});
    }
  }
})
.service('LoginService',function($q){
  return {
    loginUser: function(name,pw) {
      var deferred = $q.defer();
      var promise = deferred.promise;
      if(name ==  'Sowmya' && pw == '123') {
        deferred.resolve('welcome' + name + '!');
      } else {
        deferred.reject('Please enter correct username/password');
      }
      promise.success=function(fn){
        promise.then(fn);
        return promise;
      }
      promise.error=function(fn) {
        promise.then(null, fn);
        return promise;
      }
      return promise;
    }
  }
});