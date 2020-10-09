package ru.sberstart.handler.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.net.httpserver.HttpExchange;
import lombok.SneakyThrows;

import java.io.OutputStream;

public class ResponseMaker<T> {
    @SneakyThrows
    public void makeResponse(T t, HttpExchange httpExchange){
        OutputStream outputStream = httpExchange.getResponseBody();
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

        String s = writer.writeValueAsString(t);

        httpExchange.sendResponseHeaders(200, s.length());

        outputStream.write(s.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
