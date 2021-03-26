//package com.company.servlets;
//
//import com.company.controllers.MainController;
//import com.company.dto.Note;
//import com.company.dto.NoteResponse;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//public class NoteServlet extends MainController {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        if (id != null && contains(id)) {
//            Note note = getIdNote(id);
////            writeJson(new NoteResponse().setNote(note).setStatus("ok"), resp);
//            req.setAttribute("note",note);
//            req.getRequestDispatcher("/WEB-INF/views/note.jsp").forward(req, resp);
//
//        }else writeJson( new NoteResponse().setError("USER NOT FOUND").setStatus("ok"), resp);
//    }
//
//    private boolean contains(String id) {
//        for (Note note : noteService.getAll()) {
//            if (note.getNoteId().equals(Integer.parseInt(id))) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private Note getIdNote(String id) {
//        for (Note note : noteService.getAll()) {
//            if (note.getNoteId().equals(Integer.parseInt(id))) {
//                return note;
//            }
//        }
//        return null;
//    }
//
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Note n = new Note()
//                .setNoteName(req.getParameter("name"))
//                .setNoteDescription(req.getParameter("description"));
//        noteService.add(n);
//
//        resp.sendRedirect(req.getContextPath()+"/get");
//
//    }
//}
