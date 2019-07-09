package com.pinyougou.sellergoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;


import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;
import specification.Goods;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbBrandMapper tbBrandMapper;
    @Autowired
    private TbSellerMapper tbSellerMapper;

    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbGoods goods) {
        goodsMapper.insert(goods);
    }


    /**
     * 修改
     */
    @Transactional
    @Override
    public void update(Goods goods) {
        //更新基本数据
         goodsMapper.updateByPrimaryKey(goods.getTbGoods());
         //更新扩展数据
        tbGoodsDescMapper.updateByPrimaryKey(goods.getTbGoodsDesc());

        //先删除原有的SKU数据
        TbItemExample example= new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goods.getTbGoods().getId());
        tbItemMapper.deleteByExample(example);

        //插入新的sku数据
        saveItemList(goods);

    }

    private  void  saveItemList(Goods goods){
        if ("1".equals(goods.getTbGoods().getIsEnableSpec())) {

            List<TbItem> tbItems = goods.getTbItems(); //获取商品SKU列表
            for (TbItem tbItem : tbItems) {
                // 获取标题
                String title = goods.getTbGoods().getGoodsName();

                Map<String, Object> specMap = JSON.parseObject(tbItem.getSpec());
                for (String key : specMap.keySet()) {

                    title += " " + specMap.get(key);
                }

                tbItem.setTitle(title); // 设置标题
                setItemValues(goods, tbItem);
                tbItemMapper.insert(tbItem);
            }


        } else {
            //规格没有启用
            TbItem tbItem = new TbItem();
            tbItem.setTitle(goods.getTbGoods().getGoodsName()); // 商品SPU
            tbItem.setPrice(goods.getTbGoods().getPrice()); //价格
            tbItem.setStatus("1");//状态
            tbItem.setIsDefault("1");//是否默认
            tbItem.setNum(99999); //库存数量
            tbItem.setSpec("{}");//规格选项
            setItemValues(goods, tbItem);
            tbItemMapper.insert(tbItem);


        }

    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Goods findOne(Long id) {
        Goods goods = new Goods();
          // 根据id查询基本信息
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setTbGoods(tbGoods);

         //根据id查询扩展信息
        TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(id);
        goods.setTbGoodsDesc(tbGoodsDesc);

           //查询SKU列表
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id); // 查询条件 商品id
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        goods.setTbItems(tbItems);
        return goods;
    }

    /**
     * 批量删除
     */
    @Override
    @Transactional
    public void delete(Long[] ids) {
        for (Long id : ids) {

            TbGoods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setIsDelete("1");

            goodsMapper.updateByPrimaryKey(goods);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        Criteria criteria = example.createCriteria();
        criteria.andIsDeleteIsNotNull() ; // 查询非删除状态

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
               // criteria.andSellerIdLike("%" + goods.getSellerId() + "%");
                //精准查询
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 商品的添加  组合
     *
     * @param goods
     */

    @Transactional
    @Override
    public void insertGoods(Goods goods) {
        //设置申请状态为 未审核 “0”
        goods.getTbGoods().setAuditStatus("0");
        //获得商品的SPE  并进行添加
        goodsMapper.insert(goods.getTbGoods());

        //获取 商品的 扩展列表//设置 ID
        goods.getTbGoodsDesc().setGoodsId(goods.getTbGoods().getId());
        tbGoodsDescMapper.insert(goods.getTbGoodsDesc());// 进行添加

        saveItemList(goods);



    }

    /**
     * 状态修改
     *
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(Long[] ids, String status) {

        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setAuditStatus(status);  //设置状态
             goodsMapper.updateByPrimaryKey(tbGoods); // 更新

        }

    }

    private void setItemValues(Goods goods, TbItem tbItem) {
        tbItem.setGoodsId(goods.getTbGoods().getId());//设置商品SPU编号
        tbItem.setSellerId(goods.getTbGoods().getSellerId()); //设置商家编号
        tbItem.setCategoryid(goods.getTbGoods().getCategory3Id()); // 设置商品分类编号3级
        tbItem.setCreateTime(new Date()); //设置创建日期
        tbItem.setUpdateTime(new Date());  //设置日期
        //品牌名称
        TbBrand tbBrand = tbBrandMapper.selectByPrimaryKey(goods.getTbGoods().getBrandId());
        tbItem.setBrand(tbBrand.getName()); // 设置品牌名称

        //商家名称
        TbSeller tbSeller = tbSellerMapper.selectByPrimaryKey(goods.getTbGoods().getSellerId());
        tbItem.setSeller(tbSeller.getNickName()); //设置商家名称
              //				分类名称
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(goods.getTbGoods().getCategory3Id());
        tbItem.setCategory(tbItemCat.getName());// 设置分类名称

        //图片地址（取spu的第一个图片）
        List<Map> imageMap = JSON.parseArray(goods.getTbGoodsDesc().getItemImages(), Map.class);

        if (imageMap.size() > 0) {

            tbItem.setImage((String) imageMap.get(0).get("url")); // 设置图片
        }


    }



}
