package com.server.repository;

import com.server.bean.Girl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JPA实现类
 *
 * @author CYX
 * @create 2018-07-10-18:44
 */
public interface GirlRepository extends JpaRepository<Girl, Integer> {

    /**
     * 通过年龄查询女生信息
     *
     * @param age 年龄
     * @return
     */
    List<Girl> findGirlsByAge(Integer age);

    /**
     * 根据年龄、cupsize查询女生信息
     *
     * @param age
     * @param cupSize
     * @return
     */
    List<Girl> findGirlsByAgeAndCupSize(Integer age, String cupSize);

    /**
     * 根据cupSize查找女生
     * <br>
     * 使用自定义SQL语句
     * <br>
     * '@Query':实用类配置自定义SQL的注解,后面参数nativeQuery = true,才是表明使用原生SQL.
     * 如果不配置,默认是false,则使用HQL查询方式.
     *
     * @param cupSize
     * @return
     */
    @Query(value = "SELECT g.id , g.age , g.cup_size FROM girl AS g where g.cup_size = ?1", nativeQuery = true)
    List<Girl> findGirlsByCupSize(String cupSize);

}
