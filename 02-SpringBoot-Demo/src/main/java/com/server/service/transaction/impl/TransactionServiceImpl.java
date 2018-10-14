package com.server.service.transaction.impl;

import com.server.bean.Girl;
import com.server.repository.GirlRepository;
import com.server.service.transaction.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务接口实现类
 *
 * @author CYX
 * @create 2018-07-15-18:03
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private GirlRepository girlRepository;

    /**
     * '@Transactional'注解的rollbackFor属性，指定特定异常时，数据回滚
     *
     * @param girl
     * @return
     */
    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public Girl saveGirlWithRollBack(Girl girl) {

        Girl resultGirl = girlRepository.save(girl);

        //手动触发IllegalArgumentException异常
        if (resultGirl.getAge() == 22) {
            throw new IllegalArgumentException("age:22，已存在，数据将回滚");
        }

        return resultGirl;
    }

    /**
     * '@Transactional'注解的noRollbackFor属性,指定特定异常时,数据不回滚.
     *
     * @param girl
     * @return
     */
    @Override
    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    public Girl saveGirlWithOutRollBack(Girl girl) {

        Girl resultGirl = girlRepository.save(girl);
        if (girl.getAge() == 22) {
            throw new IllegalArgumentException("age:22,已存在,数据不会回滚,依旧插入数据库");
        }

        return resultGirl;
    }

}
