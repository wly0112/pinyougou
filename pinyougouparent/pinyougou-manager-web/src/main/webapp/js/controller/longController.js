app.controller('longController',function ($scope, $controller,  longService) {
    $controller('baseController',{$scope:$scope});//继承
  $scope.loginUsername= function(){
      longService.loginName().success(
          function (respones) {

                 $scope.userName = respones.userName;
          }
      )




  }

})