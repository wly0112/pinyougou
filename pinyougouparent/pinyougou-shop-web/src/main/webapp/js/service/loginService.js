app.service("loginService",function ($http) {

      this.sellerName=function () {
           return $http.get('../sellerName/name.do')
      }
})