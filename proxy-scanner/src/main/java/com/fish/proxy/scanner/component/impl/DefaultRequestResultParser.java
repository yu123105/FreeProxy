package com.fish.proxy.scanner.component.impl;

import com.fish.proxy.bean.scanner.RequestResult;
import com.fish.proxy.bean.scanner.ScannerResult;
import com.fish.proxy.bean.scanner.ScannerTask;
import com.fish.proxy.scanner.component.RequestResultParser;

/**
 * Created by Administrator on 2017/3/10.
 */
public class DefaultRequestResultParser implements RequestResultParser {
    @Override
    public ScannerResult handleRequestResult(ScannerTask task, RequestResult requestResult) {
        return new ScannerResult(task, requestResult);
    }
}
