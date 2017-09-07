package com.ahzd.cxf.test;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
    String sayHi(String text);
}

