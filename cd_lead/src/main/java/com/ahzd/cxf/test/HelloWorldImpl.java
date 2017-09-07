
package com.ahzd.cxf.test;

import javax.jws.WebService;

@WebService(endpointInterface = "com.ahzd.cxf.test.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

