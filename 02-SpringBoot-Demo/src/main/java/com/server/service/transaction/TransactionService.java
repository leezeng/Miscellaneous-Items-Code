package com.server.service.transaction;

import com.server.bean.Girl;

/**
 * @author CYX
 */
public interface TransactionService {

    Girl saveGirlWithRollBack(Girl girl);

    Girl saveGirlWithOutRollBack(Girl girl);

}
