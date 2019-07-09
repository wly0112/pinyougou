//品牌服务
app.service('brandService', function ($http) {
    // 查询
    this.findall = function () {
        return $http.get("../Brand/findAll.do")
    }
    //分页查询
    this.findPage = function (pageNum, pageSize) {
        return $http.get('../Brand/findAllPage.do?pageNum=' + pageNum + '&pageSize=' + pageSize + ' ')
    }
    // 添加
    this.add = function (brand) {
        return $http.post('../Brand/add.do', brand)
    }
    //修改
    this.updata = function (brand) {
        return $http.post('../Brand/updata.do', brand)
    }
    //根据id查询
    this.findOne = function (id) {
        return    $http.post("../Brand/findOne.do?id=" + id)
    }
    //批量删除
    this.delete = function (ids) {
        return   $http.get('../Brand/delete.do?ids=' + ids)
    }
    //模糊查询
    this.search = function (pageNum,pageSize,serachBrand) {
        return  $http.post('../Brand/serach.do?pageNum=' + pageNum + '&pageSize=' + pageSize, serachBrand)
    }
     //品牌下拉列表
    this.selectOptionList  =function () {
         return $http.get('../Brand/selectOptionList.do');
    }
});