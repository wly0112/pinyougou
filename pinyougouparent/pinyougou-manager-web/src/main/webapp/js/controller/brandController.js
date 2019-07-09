//品牌控制层
app.controller("brandcontroller", function ($scope, $controller, brandService) {

    $controller('baseController',{$scope:$scope});//继承
    // 查询品牌列表
    $scope.findall = function () {
        brandService.findall().success(
            function (respones) {
                $scope.list = respones;
            }
        )
    };


    // 分页
    $scope.findPage = function (pageNum, pageSize) {

        brandService.findPage(pageNum, pageSize).success(function (respones) {

            $scope.list = respones.rows; // 每页显示的内容

            $scope.paginationConf.totalItems = respones.total;  // 总记录数

        })
    };

    //添加
    $scope.add = function () {

        var Object = null;
        if ($scope.brand.id != null) {

            Object = brandService.updata($scope.brand)
        } else {

            Object = brandService.add($scope.brand)
        }
        Object.success(function (respones) {
            if (respones.success) {

                $scope.pagefindList();
            } else {

                alert(respones.message);
            }


        })

    };


    //根据id查询

    $scope.findOne = function (id) {

        brandService.findOne(id).success(function (respones) {

            $scope.brand = respones;
        })

    }


    //批量删除

    $scope.delete = function () {
        brandService.delete($scope.deleteId).success(function (respones) {

            if (respones.success) {

                $scope.pagefindList(); // 刷新列表
            } else {

                alert(respones.message);
            }

        })
    }

    //模糊查询
    $scope.serachBrand = {};
    $scope.search = function (pageNum, pageSize) {

        brandService.search(pageNum, pageSize, $scope.serachBrand).success(function (respones) {


            $scope.list = respones.rows ; // 每页显示的内容

            $scope.paginationConf.totalItems = respones.total;  // 总记录数

        })

    }

})
