package com.company.sql;

import com.company.NoteService;

public class UberFactory {

    private static final UberFactory INSTANCE = new UberFactory();
    private NoteService noteService;

    public static UberFactory instance(){
        return INSTANCE;
    }

    private UberFactory(){
        noteService = new NoteService();
    }

    public NoteService getNoteService() {
        return noteService;
    }
}
