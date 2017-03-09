package com.fish.proxy.scanner.component;


import com.fish.proxy.scanner.bean.RequestResult;
import com.fish.proxy.scanner.bean.ScannerResult;
import com.fish.proxy.scanner.bean.ScannerTask;

public interface RequestResultParser {
    ScannerResult handleRequestResult(ScannerTask task, RequestResult requestResult);
}
