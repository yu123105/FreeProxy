package com.fish.proxy.scanner.component;


import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;

public interface RequestResultParser {
    ScannerResult handleRequestResult(ScannerTask task, RequestResult requestResult);
}
