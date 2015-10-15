angular.module('ionicApp', ['ionic','ngCordova'])

.config(function($stateProvider, $urlRouterProvider) {

  $stateProvider
    .state('signin', {
      url: '/sign-in',
      templateUrl: 'templates/sign-in.html',
      controller: 'SignInCtrl'
    })
    .state('Registration', {
      url: '/Registration',
      templateUrl: 'templates/Registration.html',
      controller: 'RegCtrl'
    })
    .state('forgotpassword', {
      url: '/forgot-password',
      templateUrl: 'templates/forgot-password.html'
    })
    .state('changepassword', {
      url: '/changepassword',
      templateUrl: 'templates/changepassword.html',
      controller :'ChngpwdCtrl'
    })
    .state('deleteAccount', {
      url: '/deleteAccount',
      templateUrl: 'templates/deleteAccount.html',
      controller :'delAccCtrl'
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
    .state("secure", {
            url: "/secure",
            templateUrl: "templates/secure.html",
            controller: "SecureController"
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

.controller('SignInCtrl', function($scope, $ionicPlatform, $ionicLoading, $compile, $http, $window, $state) {
  $scope.signIn = function(username,pwd) {
        $http({
            method: 'GET',
            url : 'https://api.mongolab.com/api/1/databases/facecomm/collections/Users?q={"name":"'+username+'"}&f={"password":1}&fo=true&apiKey=5cj9ozUjYZWJR5JPG_ItUIKcJrIjHEli'
        })
        .success(function(data) {
            if (data.password == pwd) {
                $state.go('tabs.home');
            } else {
                alert("Invalid combination of Username and password");
            }
        })
        .error(function() {
            alert('Failed to find user '+username);
        });
        
    };
})
.controller('delAccCtrl', function($scope, $ionicPlatform, $ionicLoading, $compile, $http, $window) {
   
    $scope.removeAcc = function(username,pwd) {
        $http({
            method: 'GET',
            url : 'https://api.mongolab.com/api/1/databases/facecomm/collections/Users?q={"name":"'+username+'"}&f={"password":1,"_id":1}&fo=true&apiKey=5cj9ozUjYZWJR5JPG_ItUIKcJrIjHEli'
        })
        .success(function(data) {
            if (data.password == pwd) {
                $http({
                    method: 'DELETE',
                    url: 'https://api.mongolab.com/api/1/databases/facecomm/collections/Users/'+data._id.$oid+'?apiKey=5cj9ozUjYZWJR5JPG_ItUIKcJrIjHEli',
                    async: true
                })
                .success(function() {
                    $scope.Msg = "User "+username+" has been removed";
                })
                .error(function() {
                    alert("Failed to remove user");
                });
            } else {
                alert("Invalid password");
            }
        })
        .error(function() {
            alert('Failed to find user '+username);
        });
    }
})
.controller('RegCtrl', function($scope, $http, $httpParamSerializerJQLike) {
 $scope.pageClass = 'register';
 $scope.register = function(username, password, email, phone) {
$http({
    method: 'POST',
    url : 'https://api.mongolab.com/api/1/databases/facecomm/collections/Users?apiKey=5cj9ozUjYZWJR5JPG_ItUIKcJrIjHEli',
    data: JSON.stringify({
                name: username,
                password: password,
                email: email,
                phone: phone
            }),
    contentType: "application/json"
}).success(function() {
    $scope.username ="";
    $scope.password ="";
    $scope.email ="";
    $scope.phone ="";
    
    $scope.msg ="Registration successfull";
        })
}
 
})
.controller('ChngpwdCtrl', function($scope, $ionicPlatform, $ionicLoading, $compile, $http, $window) {
 $scope.changePassword = function(email, pwdold, pwdnew) {
        console.log("RegCtrl: changePassword: Entered with: " + email + ", " + pwdold + ", " + pwdnew );
        $http({
            method: 'GET',
            url: 'https://api.mongolab.com/api/1/databases/facecomm/collections/Users?q={"email":"'+email+'"}&f={"password":1}&fo=true&apiKey=5cj9ozUjYZWJR5JPG_ItUIKcJrIjHEli'
        })
        .success(function(data) {
            if (data.password == pwdold) {
                $http({
                    method: 'PUT',
                    url: 'https://api.mongolab.com/api/1/databases/facecomm/collections/Users?q={"email":"'+email+'"}&apiKey=5cj9ozUjYZWJR5JPG_ItUIKcJrIjHEli',
                    data: JSON.stringify({ "$set" : { "password": pwdnew } }),
                    contentType: 'Application/json'
                }) .success(function() {
                    $scope.displayMsg = "Password changed";
                }) .error(function() {
                    alert('Failed to update password');
                })
                        
            } else {
                alert('Old password is invalid');
            }
        })
        .error(function() {
            alert('Failed to find existing details for ' + email);
        })
        
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
.controller("PictureCtrl", function ($scope, $cordovaCamera) {
 
                $scope.takePhoto = function () {
                  var options = {
                    quality: 75,
                    destinationType: Camera.DestinationType.DATA_URL,
                    sourceType: Camera.PictureSourceType.CAMERA,
                    allowEdit: true,
                    encodingType: Camera.EncodingType.JPEG,
                    targetWidth: 300,
                    targetHeight: 300,
                    popoverOptions: CameraPopoverOptions,
                    saveToPhotoAlbum: false
                };
   
                    $cordovaCamera.getPicture(options).then(function (imageData) {
                        $scope.imgURI = "data:image/jpeg;base64," + imageData;
                    }, function (err) {
                        // An error occured. Show a message to the user
                    });
                }
                
                $scope.choosePhoto = function () {
                  var options = {
                    quality: 75,
                    destinationType: Camera.DestinationType.DATA_URL,
                    sourceType: Camera.PictureSourceType.PHOTOLIBRARY,
                    allowEdit: true,
                    encodingType: Camera.EncodingType.JPEG,
                    targetWidth: 300,
                    targetHeight: 300,
                    popoverOptions: CameraPopoverOptions,
                    saveToPhotoAlbum: false
                };
   
                    $cordovaCamera.getPicture(options).then(function (imageData) {
                        $scope.imgURI = "data:image/jpeg;base64," + imageData;
                    }, function (err) {
                        // An error occured. Show a message to the user
                    });
                }
            });
