package ru.sberstart.handler.util;

import com.sun.net.httpserver.HttpExchange;

public class RequestParamTransformer {
    public static Integer handleGetRequest(HttpExchange httpExchange) {
        return Integer.parseInt(httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1]);
    }
}
