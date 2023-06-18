package com.heart.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean拷贝工具类
 */
public class BeanCopyUtils {

    public BeanCopyUtils() {
    }

    /**
     * 将 source 对象转变成 class 指定的类型对象
     *
     * @param source 源对象
     * @param clazz  目标对象 class
     * @return Bean拷贝结果
     */
    public static <T> T copyBean(Object source, Class<T> clazz) {
        T target = null;
        try {
            // 根据目标 class 创建实例对象
            target = clazz.newInstance();
            // 进行属性拷贝
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回对象
        return target;
    }

    /**
     * 将源对象集合拷贝成目标对象集合
     *
     * @param sourceList 源对象集合
     * @param clazz      目标对象的类
     * @return 目标对象集合
     */
    public static <V, T> List<T> copyBeanList(List<V> sourceList, Class<T> clazz) {
        return sourceList
                .stream()
                .map(v -> copyBean(v, clazz))
                .collect(Collectors.toList());
    }

}
