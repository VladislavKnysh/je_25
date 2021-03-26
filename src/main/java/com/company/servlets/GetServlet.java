//package com.company.servlets;
//
//import com.company.dto.Note;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/get")
//public class GetServlet extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        List<Note> notes = noteService.getAll();
//        req.setAttribute("notes",notes);
//
//        req.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(req, resp);
//
//
////        writeJson(notes, resp);
//    }
//
//
//
//
//}
