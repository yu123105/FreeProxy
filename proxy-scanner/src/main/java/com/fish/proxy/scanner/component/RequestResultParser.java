package com.fish.proxy.scanner.component;


import com.fish.proxy.scanner.RequestResult;
import com.fish.proxy.scanner.ScannerResult;
import com.fish.proxy.scanner.ScannerTask;

public interface RequestResultParser {
    ScannerResult handleRequestResult(ScannerTask task, RequestResult requestResult);
}
