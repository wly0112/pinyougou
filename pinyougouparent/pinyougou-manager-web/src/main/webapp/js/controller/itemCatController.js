 //控制层 
app.controller('itemCatController' ,function($scope,$controller   ,itemCatService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemCatService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemCatService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemCatService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemCatService.update( $scope.entity ); //修改  
		}else{
			$scope.entity.parentId=$scope.parentId;// 赋予上级ID

			serviceObject=itemCatService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){

					//重新查询 
		        /*	$scope.reloadList();*///重新加载
                   $scope.findbyId($scope.parentId);

				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemCatService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemCatService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}

	$scope.parentId=	0; // 上级id
	/**
	 * 根据id查询商品分类
	 * @param id
	 */
	 $scope.findbyId =function (id) {
   $scope.parentId=id; //记住上级ID
		 itemCatService.findbyId(id).success(function (respones) {

		 	 $scope.list=respones;

		 })

	 }


	   //面包屑
	  $scope.grader=1;
	 $scope.setGrader= function (va) {
	 	  $scope.grader=va;
	 }

	 $scope.selectList=function (item_enyity) {

	 	 if ($scope.grader==1){    // 一级
	 	 	  $scope.entity_1=null;
	 	 	  $scope.entity_2=null;


		 }
		 if ($scope.grader==2){    // 一级
			 $scope.entity_1=item_enyity;
			 $scope.entity_2=null;


		 }
		 if ($scope.grader==3){    // 一级

			 $scope.entity_2=item_enyity;


		 }

		 $scope.findbyId(item_enyity.id);
	 }



});	
