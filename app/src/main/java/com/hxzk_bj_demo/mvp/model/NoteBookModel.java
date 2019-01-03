package com.hxzk_bj_demo.mvp.model;

import com.hxzk_bj_demo.javabean.NoteBookBean;

/**
 * Created by ${赵江涛} on 2018-1-30.
 * 作用:
 */

public interface NoteBookModel {

    NoteBookBean getNoteBook(int position);
    int getNotesCount();
    int insertNoteBook(NoteBookBean noteBookBean);
}
