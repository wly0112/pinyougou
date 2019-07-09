//控制层
app.controller('goodsController', function ($scope, $controller,$location, goodsService, uploadService, itemCatService,typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {

        var id = $location.search()['id'];//获取参数id
        if (id==null){
            return;
        }
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                //向富文本编辑器添加文本介绍
                editor.html($scope.entity.tbGoodsDesc.introduction);

                //显示图片列表
                $scope.entity.tbGoodsDesc.itemImages=JSON.parse(  $scope.entity.tbGoodsDesc.itemImages)

               if ($location.search()['id']==null) {
                    //如果没有id  就加载模板中的扩展属性

                   //显示扩展属性
                   $scope.entity.tbGoodsDesc.customAttributeItems = JSON.parse($scope.typeTemplate.customAttributeItems);
               }

        //规格属性
                $scope.entity.tbGoodsDesc.specificationItems = JSON.parse( $scope.entity.tbGoodsDesc.specificationItems)

                   //SKU列表转换json
                for (var i = 0; i <$.scope.entity.tbItems.length; i++) {
                    $scope.entity.tbItems[i].spec=JSON.parse($scope.entity.tbItems[i].spec);

                }
            }
        );
    }

    //保存
    $scope.save = function () {
        $scope.entity.tbGoodsDesc.introduction = editor.html();

        var serviceObject;//服务层对象
        if ($scope.entity.tbGoods.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    alert("保存成功");
                    location.href="goods.html";//跳转到商品列表页
                } else {
                    alert(response.message);
                }
            }
        );
    }

    //根据规格名称和选项名称返回是否被勾选
    $scope.checkAttributeValue=function(specName,optionName){

          var items = $scope.entity.tbGoodsDesc.specificationItems;
        var object= $scope.searchObjectByKey(items,'attributeName',specName);
        if (object != null&&object.attributeValue.indexOf(optionName)>=0){

            return true;
        }else {
            false;
        }
    }

    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //商品添加保存
    $scope.add = function () {

        $scope.entity.tbGoodsDesc.introduction = editor.html();
        goodsService.add($scope.entity).success(function (respones) {

            if (respones.success) {

                alert("保存成功");
                $scope.entity = {};
                editor.html("");  //清空富文本编辑
            } else {

                alert(respones.message);
            }
        })


    }


    //图片上传
    $scope.uploadFile = function () {

        uploadService.uploadFile().success(function (respones) {

            if (respones.success) {  // 图片上传成功

                $scope.image_entity.url = respones.message;//设置地址


            } else {
                alert(respones.message);
            }
        })
    }


    $scope.entity = {tbGoods: {}, tbGoodsDesc: {itemImages: [],specificationItems:[]}}
    // 图片上传保存列表
    $scope.add_image_entity = function () {

        $scope.entity.tbGoodsDesc.itemImages.push($scope.image_entity);

    }

    //列表中 移除图片

    $scope.remove_inage_entity = function (index) {
        $scope.entity.tbGoodsDesc.itemImages.splice(index, 1);


    };


    //商品分类 1 级 下拉框
    $scope.goodsList1 = function () {

        itemCatService.findbyId(0).success(function (respones) {

            $scope.entityList1 = respones;



        })
    }
    // 商品 2 级分类 下拉框
    $scope.$watch('entity.tbGoods.category1Id', function (newValue, oldValue) {


        itemCatService.findbyId(newValue).success(function (respones) {
            $scope.entityList2 = respones;

        })
        $scope.entityList3=undefined;  //当entity.tbGoods.category1Id的值发生变化  3 级分类清空



    })

    // 商品 3 级分类 下拉框
    $scope.$watch('entity.tbGoods.category2Id', function (newValue, oldValue) {


        itemCatService.findbyId(newValue).success(function (respones) {
            $scope.entityList3 = respones;

        })



    });

    //三级分类后读取模板ID

     $scope.$watch('entity.tbGoods.category3Id',function (newValue,oldValue) {

         itemCatService.findOne(newValue).success(function (respones) {
             $scope.entity.tbGoods.typeTemplateId = respones.typeId; // 更新模板id

         })

     })

    //品牌下拉列表

    $scope.$watch('entity.tbGoods.typeTemplateId',function (newValue,oldValue) {

        typeTemplateService.findOne(newValue).success(function (respones) {
            $scope.typeTemplate = respones; // 获取类型模板

            $scope.typeTemplate.brandIds = JSON.parse( $scope.typeTemplate.brandIds); // 品牌列表

              $scope.entity.tbGoodsDesc.customAttributeItems=JSON.parse( $scope.typeTemplate.customAttributeItems);//扩展属性

    })
        //查询规格列表
        typeTemplateService.findSpecList(newValue).success(function (respones) {
            $scope.specList=respones;  //规格列表
        })
    })
    
    
     // 规格选项
     $scope.updateSpecAttribute=function ($event,name ,value) {
          var object= $scope.searchObjectByKey($scope.entity.tbGoodsDesc.specificationItems,'attributeName',name);
          
          if (object!=null) {
              if ($event.target.checked){
                  object.attributeValue.push(value);//
              }else {

                   //取消勾选
                  object.attributeValue.splice(object.attributeValue.indexOf(value),1);//移除选项

                  //如果整个选项都取消了 将整条记录移除
                  if (  object.attributeValue.length==0) {
                      $scope.entity.tbGoodsDesc.specificationItems.splice(  $scope.entity.tbGoodsDesc.specificationItems.indexOf(object),1);
                  }
              }


          }else {
      $scope.entity.tbGoodsDesc.specificationItems.push({"attributeName":name,"attributeValue":[value]});
          }
     }




    //创建SKU列表
    $scope.createItemList=function(){
        $scope.entity.tbItems=[{spec:{},price:null,num:null,status:'0',isDefault:'0' } ];//初始
        var items=  $scope.entity.tbGoodsDesc.specificationItems;
        for(var i=0;i< items.length;i++){
            $scope.entity.tbItems = addColumn( $scope.entity.tbItems,items[i].attributeName,items[i].attributeValue );
        }
    }
//添加列值
    addColumn=function(list,columnName,conlumnValues) {
        var newList = [];//新的集合
        for (var i = 0; i < list.length; i++) {
            var oldRow = list[i];
            for (var j = 0; j < conlumnValues.length; j++) {
                var newRow = JSON.parse(JSON.stringify(oldRow));//深克隆
                newRow.spec[columnName] = conlumnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }



  $scope.status=['未审核','已审核','审核未通过','关闭']//商品审核状态

     $scope.itcatList=[]; //分类列表
    $scope.findItcatList=function () {
        itemCatService.findAll().success(function (response) {

            for (var i = 0; i <response.length ; i++) {
                $scope.itcatList[response[i].id] =response[i].name;
            }
        })
    }
        });
