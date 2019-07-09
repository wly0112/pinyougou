app.controller('baseController',function ($scope) {


    //刷新
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);

    }

    $scope.paginationConf = {
        currentPage: 1,     //当前页
        totalItems: 10,       //总记录数
        itemsPerPage: 5,     // 每页显示的条数
        perPageOptions: [10, 15, 20],   // 下拉选条数
        onChange: function () {
            $scope.reloadList();  // 刷新


        }


    };



    $scope.selectIds = [];  // 选中的id数组
    // 更新复选框
    $scope.updateSelection = function ($event, id) {

        if ($event.target.checked) {
            //为 true 选中
            $scope.selectIds.push(id);  // 添加

        } else {
            //为 fales  没有选中
            var indexOf = $scope.selectIds.indexOf(id);  // 元素所在的位置

            $scope.selectIds.splice(indexOf, 1);   // 删除

        }


    }

    //字符串转为json格式

      $scope.jsonToString = function (jsonString ,key) {
   var json  = JSON.parse(jsonString);
     value = "";

          for (var i = 0; i <json.length ; i++) {
                if (i>0){
                    value+=",";
                }
             value+=json[i][key]
          }
          return value;

      }

    //从集合中按照key查询对象
    $scope.searchObjectByKey=function(list,key,keyValue){
        for(var i=0;i<list.length;i++){
            if(list[i][key]==keyValue){
                return list[i];
            }
        }
        return null;
    }


});