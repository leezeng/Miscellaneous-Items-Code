package com.server.controller;

import com.server.bean.Girl;
import com.server.repository.GirlRepository;
import com.server.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 女生-Controller
 *
 * @author CYX
 * @create 2018-07-10-18:59
 */
@RestController
public class GirlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GirlController.class);

    @Autowired
    private GirlRepository girlRepository;

    @Autowired
    private TransactionService transactionService;

    /**
     * 添加一个女生信息
     *
     * @param cupSize
     * @param age
     * @return
     */
    @PostMapping(value = "/girls")
    public String addGirlInfo(@RequestParam("cupSize") String cupSize, @RequestParam("age") Integer age) {

        final String method = "addGirlInfo";
        LOGGER.info("method : {} , cupSize : {} , age : {} - start", new Object[]{method, cupSize, age});

        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);

        Girl resultGirl = girlRepository.save(girl);

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, resultGirl});
        return resultGirl.toString();

    }

    /**
     * 通过id查询一个女生信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/girls/{id}")
    public Girl getGirlInfoByID(@PathVariable("id") Integer id) {

        final String method = "getGirlInfoByID";
        LOGGER.info("method : {} , id : {} - start", new Object[]{method, id});

        Girl girl = girlRepository.findById(id).get();

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, girl.toString()});
        return girl;
    }

    /**
     * 通过id更新女生信息
     *
     * @param id
     * @param cupSize
     * @param age
     * @return
     */
    @PutMapping(value = "/girls/{id}")
    public String updataGirlByID(
            @PathVariable("id") Integer id,
            @RequestParam("cupSize") String cupSize,
            @RequestParam("age") Integer age) {

        final String method = "updataGirlByID";
        LOGGER.info("method : {} , id : {} , cupSize : {} , age : {} - start", new Object[]{method, id, cupSize, age});

        Girl girl = new Girl();
        girl.setId(id);
        girl.setAge(age);
        girl.setCupSize(cupSize);

        Girl result = girlRepository.save(girl);

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, result.toString()});
        return result.toString();
    }


    /**
     * 获取所有女生信息
     *
     * @return
     */
    @GetMapping(value = "/girls")
    public List<Girl> getAllGirlsInfos() {

        final String method = "getAllGirlsInfos";
        LOGGER.info("method : {} - start", new Object[]{method});

        List<Girl> girls = girlRepository.findAll();

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, girls.toString()});
        return girls;
    }

    /**
     * 通过id删除一个女生信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/girls/{id}")
    public Boolean deleteGirlInfoByID(@PathVariable("id") Integer id) {

        final String method = "deleteGirlInfoByID";
        LOGGER.info("method : {} , id : {} - start", new Object[]{method, id});

        boolean result = true;

        try {
            girlRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = false;
        }

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, result});
        return result;

    }

    /**
     * 通过age查询女生信息
     *
     * @param age
     */
    @GetMapping(value = "/girls/byAge/{age}")
    public List<Girl> findGirlsByAge(@PathVariable("age") Integer age) {

        final String method = "findGirlsByAge";
        LOGGER.info("method : {} , age : {} - start", new Object[]{method, age});

        List<Girl> result = null;
        try {
            result = girlRepository.findGirlsByAge(age);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, result});
        return result;
    }

    /**
     * 通过年龄和cupSize查找女生信息
     *
     * @param age
     * @param cupSize
     * @return
     */
    @GetMapping(value = "/girls/byAgeAndCupSize")
    public List<Girl> findGirlsByAgeAndCupSize(Integer age, String cupSize) {

        final String method = "findGirlsByAgeAndCupSize";
        LOGGER.info("method : {} , age : {} , cupSize : {} - start", new Object[]{method, age, cupSize});

        List<Girl> result = null;
        try {
            result = girlRepository.findGirlsByAgeAndCupSize(age, cupSize);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("method : {} , result : {} - end ", new Object[]{method, result});
        return result;
    }

    /**
     * 获取女生信息列表-根据年龄排序
     *
     * @return
     */
    @GetMapping(value = "/girls/sortByAge")
    public List<Girl> findAllGirlsWithSort() {

        final String method = "findAllGirlsWithSort";
        LOGGER.info("method : {} - start", new Object[]{method});

        List<Girl> result = null;
        try {
            result = girlRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, result});
        return result;
    }

    /**
     * 查询女生信息-分页
     *
     * @param page
     * @return
     */
    @GetMapping(value = "/girls/byPage")
    public List<Girl> findGirlsByPage(@RequestParam("page") int page) {

        final String method = "findGirlsByPage";
        LOGGER.info("method : {} , page : {} - start", new Object[]{method, page});

        Girl girl = new Girl();
        girl.setPageSize(5);
        girl.setPage(page);

        PageRequest pageRequest = PageRequest.of(girl.getPage() - 1, girl.getPageSize());

        Page<Girl> girlPages = girlRepository.findAll(pageRequest);
        List<Girl> result = girlPages.getContent();

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, result});
        return result;
    }

    /**
     * 通过cupSize查询女生信息-使用自定义SQL
     * <p>
     * '@Modifying':该注解配合'@Query'注解使用<br>
     *
     * @param cupSize
     * @return
     */
    @GetMapping(value = "/girls/byCupSize")
    @Modifying
    public List<Girl> findGirlsByCupSize(@RequestParam("cupSize") String cupSize) {

        final String method = "findGirlsByCupSize";
        LOGGER.info("method : {} , cupSize : {} - start", new Object[]{method, cupSize});

        List<Girl> result = null;
        try {
            result = girlRepository.findGirlsByCupSize(cupSize);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, result});
        return result;
    }

    //====================测试事务====================
    //====================测试事务====================
    //====================测试事务====================

    /**
     * 添加一个女生信息,用来测试事务(测试事务回滚情况)
     *
     * @param cupSize
     * @param age
     * @return
     */
    @PostMapping(value = "/addGirlInfoTestRollBack")
    public String addGirlInfoTestRollBack(@RequestParam("cupSize") String cupSize, @RequestParam("age") Integer age) {

        final String method = "addGirlInfoTestRollBack";
        LOGGER.info("method : {} , cupSize : {} , age : {} - start", new Object[]{method, cupSize, age});

        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);

        Girl resultGirl = transactionService.saveGirlWithRollBack(girl);

        LOGGER.info("method:{} , result : {} - end", new Object[]{method, resultGirl});
        return resultGirl.toString();

    }

    /**
     * 添加一个女生信息,用来测试事务(测试事务不回滚情况)
     *
     * @param cupSize
     * @param age
     * @return
     */
    @PostMapping(value = "/addGirlInfoTestNoRollBack")
    public String addGirlInfoTestNoRollBack(@RequestParam("cupSize") String cupSize, @RequestParam("age") Integer age) {

        final String method = "addGirlInfoTestNoRollBack";
        LOGGER.info("method : {} , cupSize : {} , age : {} - start", new Object[]{method, cupSize, age});

        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);

        Girl resultGirl = transactionService.saveGirlWithOutRollBack(girl);

        LOGGER.info("method : {} , result : {} - end", new Object[]{method, resultGirl});
        return resultGirl.toString();

    }

}
