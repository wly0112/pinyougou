package com.pinyougou.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.container.page.Page;
import entity.PageResult;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BrandService;
import entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Brand")
public class BrandController {
    @Reference
    private BrandService brandService;
  //商品查询列表
    @RequestMapping("/findAll.do")
    public List<TbBrand> findAll(){


        return brandService.findAll();
    }

 //分页查询
@RequestMapping("/findAllPage.do")
    public PageResult findAllPage(Integer pageNum , Integer pageSize){

        return    brandService.findAllPage(pageNum,pageSize);
    }


    //添加
    @RequestMapping("/add.do")
    public Result add(@RequestBody TbBrand tbBrand){

        try {
            brandService.insert(tbBrand);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();

            return  new Result(false,"添加失败");
        }

    }

    //根据id查询
    @RequestMapping("/findOne.do")
    public  TbBrand findOne(Long id){

        return   brandService.findOne(id);

    }

    //修改
    @RequestMapping("/updata.do")
    public  Result updata(@RequestBody TbBrand tbBrand){

        try {
            brandService.updata(tbBrand);
            return  new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(false,"修改失败");
        }

    }


    //删除

     @RequestMapping("/delete.do")
    public  Result delete(Long  [] id ){

         try {
             brandService.delete(id);
             return new Result(true,"删除成功");
         } catch (Exception e) {
             e.printStackTrace();
              return new Result(false, "删除失败");

         }


     }
  @RequestMapping("/serach.do")
     public PageResult serach(@RequestBody TbBrand tbBrand , Integer pageNum , Integer pageSize){


           return  brandService.serach(tbBrand,pageNum,pageSize);
     }


     @RequestMapping("/selectOptionList.do")
     List<Map> selectOptionList(){

          return  brandService.selectOptionList();
     }
}
