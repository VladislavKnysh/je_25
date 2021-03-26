package com.company.controllers;

import com.company.annotations.*;
import com.company.dto.Note;
import com.company.sql.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



import java.io.IOException;
import java.util.List;

@Controller
public class MainController extends JsonController {
    @Autowired
    public NoteService noteService;


    @GetMapping("get")
    public void getAllNotes(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Note> notes = noteService.getAll();
        req.setAttribute("notes",notes);

        req.getRequestDispatcher("/WEB-INF/views/menu.jsp")
                .forward(req, resp);
    }


    @PostMapping("post")
    public void postNote(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Note note = readJson(Note.class, req);
        noteService.add(note);

}
    @GetDynamicMapping("notes/{id}")
    public void doGetById(String id, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Note note = getIdNote(id);
        req.setAttribute("note",note);
        req.getRequestDispatcher("/WEB-INF/views/note.jsp").forward(req, resp);

    }

    @GetMapping("notes/")
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");


            Note note = getIdNote(id);
            req.setAttribute("note",note);
            req.getRequestDispatcher("/WEB-INF/views/note.jsp").forward(req, resp);

    }

    private boolean contains(String id) {
        for (Note note : noteService.getAll()) {
            if (note.getNoteId().equals(Integer.parseInt(id))) {
                return true;
            }
        }
        return false;
    }

    private Note getIdNote(String id) {
        for (Note note : noteService.getAll()) {
            if (note.getNoteId().equals(Integer.parseInt(id))) {
                return note;
            }
        }
        return null;
    }

    @PostMapping("addnote")
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Note n = new Note()
                .setNoteName(req.getParameter("name"))
                .setNoteDescription(req.getParameter("description"));
        noteService.add(n);

        resp.sendRedirect(req.getContextPath()+"/get");

    }

    @PostMapping("post/delete")
    public void doPostDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }
    @DeleteMapping("post/delete")
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Note note = readJson(Note.class, req);
        int index = note.getNoteId();

        noteService.delete(index);
        resp.sendRedirect(req.getContextPath()+"/get");

    }
}
