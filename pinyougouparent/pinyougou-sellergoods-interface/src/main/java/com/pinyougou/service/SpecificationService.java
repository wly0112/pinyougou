package com.pinyougou.service;
import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbSpecification;

import entity.PageResult;
import specification.Specifitiongroup;

/**
 * 服务层接口
 * @author Administrator
 *   规格
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Specifitiongroup specification);
	
	
	/**
	 * 修改
	 */
	public void update(Specifitiongroup specification);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Specifitiongroup findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize);

  // 规格下拉列表
	List<Map> selectOptionList();
}
