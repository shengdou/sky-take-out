package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description:   套餐Service实现类
 * @date 2024/5/9 22:00
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;
    /**
     * @description: 新增套餐Service
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/5/9 22:34
     */
    public void save(SetmealDTO setmealDTO){
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);//提起套餐表数据
        setmealMapper.insert(setmeal);//插入套餐表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();//提取套餐-菜品表数据
        for(SetmealDish setmealDish : setmealDishes){//为菜品添加插入后获得的套餐id
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishMapper.insertSetMealDishBat(setmealDishes);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    /**
     * @description: 根据id查询套餐
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/5/9 22:02
     */
    @Override
    public SetmealVO seletctById(Integer id) {
        Setmeal setmeal=setmealMapper.selectById(id);//查询套餐表
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishMapper.selectDishById(id));//查询套餐-菜品表
        return setmealVO;
    }


/**
 * @description: 分页查询Service
 * @param:
 * @return:
 * @author Moyu
 * @date: 2024/5/11 9:17
 */
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        PageResult pageResult=new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());
        return pageResult;
    }

    /**
     * @description: 删除菜品service
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/5/11 20:52
     */
    @Override
    public void delete(List<Integer> ids) {
        Integer count= setmealMapper.countByIdStatus(ids);//查询是否包含起售套餐
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        } else{
            setmealMapper.deleteSetmealBat(ids);//删除套餐表
            setmealDishMapper.deleteSetmealDishBat(ids);//删除套餐-菜品表
        }

    }

    /**
     * @description: 更新套餐service
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/5/12 10:40
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);//更新套餐表

        //删除原套餐-菜品
        List ids=new ArrayList<Integer>();
        ids.add(setmeal.getId());
        setmealDishMapper.deleteSetmealDishBat(ids);

        //更新套餐菜品表
        for(SetmealDish setmealDish:setmealDTO.getSetmealDishes()){
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishMapper.insertSetMealDishBat(setmealDTO.getSetmealDishes());
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        if(status== StatusConstant.ENABLE){
            List<Dish> dishList = dishMapper.getBySetmealId(id);
            if(dishList != null && dishList.size() > 0){
                dishList.forEach(dish -> {
                    if(StatusConstant.DISABLE == dish.getStatus()){
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.update(setmeal);
    }
}
