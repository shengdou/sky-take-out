package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    public List<Dish> list(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.list(dish);
    }

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d,dishVO);

            //根据菜品id查询对应的口味
            List<DishFlavor> flavors = dishFlavorMapper.queryByDishId(d.getId());

            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

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
        Integer setMealCount =setmealMapper.countByDishId(ids);
        if (setMealCount>0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        dishMapper.deleteBat(ids);
        dishFlavorMapper.deleteByDish(ids);
    }

    /**
     * @description: 根据id查询菜品
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/29 12:04
     */
    @Override
    public DishVO findById(Long id) {
        DishVO dishVO =dishMapper.queryById(id);
        List<DishFlavor> flavors=dishFlavorMapper.queryByDishId(id);
        dishVO.setFlavors(flavors);
        log.info("根据id查询菜品{}",dishVO);
        return dishVO;
    }

    /**
     * @description: 更新菜品
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/29 15:48
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //更新菜品表
        dishMapper.update(dish);
        //删除先前口味表数据
        Long id = dishDTO.getId();
        List ids=new ArrayList<>();
        ids.add(id);
        dishFlavorMapper.deleteByDish(ids);
        //插入现在的口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&&flavors.size()>0){
            flavors.forEach(flavor->flavor.setDishId(id));
            dishFlavorMapper.insetBat(flavors);
        }
    }

    /**
     * @description: 菜品起售停售
     * @param:
     * @return:
     * @author Moyu
     * @date: 2024/4/29 21:56
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.update(dish);
        if (status== StatusConstant.DISABLE){
        List<Long> setmealIds= setmealDishMapper.countByDishId(dish.getId());
        if(setmealIds!=null&&setmealIds.size()>0) {
            Setmeal setmeal;
            for (Long setmealid : setmealIds) {
                setmeal = Setmeal.builder()
                        .id(setmealid)
                        .status(StatusConstant.DISABLE)
                        .build();
                setmealMapper.update(setmeal);
            }
        }
        }
    }
}
