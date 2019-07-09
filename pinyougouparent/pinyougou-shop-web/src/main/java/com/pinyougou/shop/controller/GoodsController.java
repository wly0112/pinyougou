package com.pinyougou.shop.controller;
import java.util.List;

import com.pinyougou.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbGoods;


import entity.PageResult;
import entity.Result;
import specification.Goods;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbGoods goods){
		try {
			goodsService.add(goods);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		//检查是否是当前商家
		Goods usergood = goodsService.findOne(goods.getTbGoods().getId());

		//获取当前登录的商家id
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!usergood.getTbGoods().getSellerId().equals(sellerId)){
			//不一致
			return  new Result(false,"非法操作");


		}
		try {
			goodsService.update(goods);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){

		//获取商家ID
		String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
		//设置查询条件
		goods.setSellerId(sellerId);
		return goodsService.findPage(goods, page, rows);		
	}

	/**
	 * 商品添加
	 * @param goods
	 * @return
	 */
	@RequestMapping("/insertGoods")
	public Result insertGoods(@RequestBody Goods goods){
	   // 获取登录的商家的用户名
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		  goods.getTbGoods().setSellerId(userName); // 设置用户名
		try {
			goodsService.insertGoods(goods);
			return  new Result(true,"商品添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"商品添加失败");
		}


	}


}
