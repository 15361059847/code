package com.immoc.repository;

import com.immoc.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lenovo on 2019/8/19.
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

}
