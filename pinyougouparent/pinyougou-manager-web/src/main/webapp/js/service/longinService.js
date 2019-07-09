app.service('longService',function ($http) {
      this.loginName=function () {
            return $http.get('../userName/name.do');
      }
})