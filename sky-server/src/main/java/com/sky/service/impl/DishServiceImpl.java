package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Moyu
 * @version 1.0
 * @description: 菜品服务类
 * @date 2024/4/25 21:41
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void save(DishDTO dishDTO) {
        log.info("新增菜品Servive：{}", dishDTO);
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&&flavors.size()>0){
            flavors.forEach(dishFlavor -> dishFlavor.setDishId(id));
            dishFlavorMapper.insetBat(flavors);
        }
    }

    /**
     * @description: 菜品分页查询
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/27 17:42
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询{}",dishPageQueryDTO);
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page= dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * @description: 菜品删除
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/29 0:35
     */
    @Override
    public void delete(List<Long> ids) {
        Integer count = dishMapper.selectByIdAndStatus(ids);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        Integer setMealCount =setmealMapper.selectById(ids);
        if (setMealCount>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        dishMapper.deleteBat(ids);
        dishFlavorMapper.deleteByDish(ids);
    }
}
