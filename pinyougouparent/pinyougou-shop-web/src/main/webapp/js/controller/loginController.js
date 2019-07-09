app.controller("loginController",function ($scope,loginService) {

     $scope.showName=function () {

          loginService.sellerName().success(function (respones) {
                $scope.name=respones.sellerName;
          })

     }

})