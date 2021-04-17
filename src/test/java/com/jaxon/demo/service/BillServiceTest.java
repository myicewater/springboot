package com.jaxon.demo.service;

import com.jaxon.demo.es.bill.BillInfo;
import com.jaxon.demo.es.bill.BillService;
import net.bytebuddy.utility.RandomString;
import org.junit.Test;


import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BillServiceTest extends BaseTest{

    @Resource
    private BillService billService;


    @Test
    public void addBillTest(){

        BillInfo bill= new BillInfo(RandomString.make(32),"abcde","0","billfilename222",new Date(),45.3);
        billService.addBill(bill);

        Map map = new HashMap();
    }
}
