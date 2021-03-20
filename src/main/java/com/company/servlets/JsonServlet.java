package com.company.servlets;

import com.company.NoteService;
import com.company.sql.UberFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonServlet extends HttpServlet {

    protected ObjectMapper mapper = new ObjectMapper();

    protected NoteService noteService = UberFactory.instance().getNoteService();

    protected void writeJson(Object obj, HttpServletResponse response) {
        try {
            String strResponse = mapper.writeValueAsString(obj);
            response.setContentType("text/html");
            PrintWriter printWriter = response.getWriter();
            printWriter.write(strResponse);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected  <T> T readJson(Class<T> clazz, HttpServletRequest req) throws IOException {

        return mapper.readValue(req.getInputStream(), clazz);

    }

}
