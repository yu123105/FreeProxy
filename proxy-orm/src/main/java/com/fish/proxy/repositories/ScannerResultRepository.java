package com.fish.proxy.repositories;


import com.fish.proxy.bean.scanner.ScannerResult;
import org.springframework.data.repository.CrudRepository;

public interface ScannerResultRepository extends CrudRepository<ScannerResult, Long> {

    ScannerResult findByIp(String ip);

}
