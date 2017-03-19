package com.fish.proxy.scanner.component;


import com.fish.proxy.bean.scanner.ScannerResult;
import org.apache.http.concurrent.FutureCallback;

public interface ScannerResultHandler<T> extends FutureCallback<T>{
}
