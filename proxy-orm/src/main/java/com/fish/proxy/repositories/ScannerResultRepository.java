package com.fish.proxy.repositories;


import com.fish.proxy.bean.scanner.ScannerResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

public interface ScannerResultRepository extends CrudRepository<ScannerResult, Long> {

    ScannerResult findByTaskIp(String ip);

}
