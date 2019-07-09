package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrandExample;
import entity.PageResult;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
 @Autowired
      private TbBrandMapper tbBrandMapper;
    @Override
    public List<TbBrand> findAll() {
        return tbBrandMapper.selectByExample(null);
    }

    /**
     * 分页
     * @param pageNum  当前是第几页
     * @param pageSize  每页显示的条数
     * @return
     */
    @Override
    public PageResult findAllPage(Integer pageNum, Integer pageSize) {
  // mybaties 的插件
        PageHelper.startPage(pageNum,pageSize);
        Page<TbBrand> tbBrands = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
        return new PageResult(tbBrands.getTotal(),tbBrands.getResult());
    }
  // 添加
    @Override
    public void insert(TbBrand brand) {
        tbBrandMapper.insert(brand);
    }


      //根据id查询
    @Override
    public TbBrand findOne(Long id) {
        return tbBrandMapper.selectByPrimaryKey(id);
    }
  //修改
    @Override
    public void updata(TbBrand tbBrand) {

        tbBrandMapper.updateByPrimaryKey(tbBrand);

    }


     //删除
    @Override
    public void delete(Long[] id) {

        for (Long ids : id) {

            tbBrandMapper.deleteByPrimaryKey(ids);

        }

    }

    //模糊查询

    @Override
    public PageResult serach(TbBrand tbBrand, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);


        TbBrandExample exmaple = new TbBrandExample();
        TbBrandExample.Criteria criteria = exmaple.createCriteria();

        if (tbBrand!=null){
            if (tbBrand.getName()!=null&&tbBrand.getName().length()>0){
               criteria.andNameLike("%"+tbBrand.getName()+"%");
            }
            if (tbBrand.getFirstChar()!=null&&tbBrand.getFirstChar().length()>0){

              criteria.andFirstCharLike("%"+tbBrand.getFirstChar()+"%");

            }

        }


        Page<TbBrand> tbBrands = (Page<TbBrand>) tbBrandMapper.selectByExample(exmaple);


        return new PageResult(tbBrands.getTotal(),tbBrands.getResult());
    }

    @Override
    public List<Map> selectOptionList() {

      return    tbBrandMapper.selectOptionList();

    }


}
