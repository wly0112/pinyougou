package com.pinyougou.service;

import entity.PageResult;
import com.pinyougou.pojo.TbBrand;

import java.util.List;
import java.util.Map;

/**
 * 商品接口
 */
public interface BrandService {

  public List<TbBrand> findAll();

    // 分页查询
   public PageResult findAllPage(Integer pageNum , Integer pageSize);

    /**
     * 添加
     * @param brand
     */
    public   void   insert(TbBrand brand);

    //根据id查询
    public  TbBrand findOne(Long id);

    //修改
    public  void  updata(TbBrand tbBrand);


    //删除

    public  void  delete (Long [] id);


      // 根据条件查询
     public PageResult serach (TbBrand tbBrand, Integer pageNum, Integer pageSize);

     //品牌下拉选框
     List<Map> selectOptionList();


}
