package com.ivan.servlet;

import com.ivan.server.Request;
import com.ivan.server.Response;

public interface Servlet {

    void init() throws Exception;

    void destory() throws Exception;

    void service(Request request, Response response) throws Exception;
}
