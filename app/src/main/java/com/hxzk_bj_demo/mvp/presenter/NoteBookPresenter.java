package com.hxzk_bj_demo.mvp.presenter;

import android.view.ViewGroup;
import android.widget.EditText;

import com.hxzk_bj_demo.javabean.NoteBookBean;
import com.hxzk_bj_demo.mvp.view.NoteBookView;
import com.hxzk_bj_demo.ui.adapter.NotesViewHolder;

/**
 * Created by ${赵江涛} on 2018-1-30.
 * 作用:
 */

public interface NoteBookPresenter {

    /**
     * 文本内容
     */
    void clickNewNoteBook(EditText mEditText);
    /**
     * sanckbar使用
     */
    void setView(NoteBookView mNoteBookView);


    /**
     * 创建adatper的view holder
     * @param parent
     * @param viewType
     * @return
     */
    NotesViewHolder createViewHolder(ViewGroup parent, int viewType);

    void bindViewHolder(NotesViewHolder holder, int position);

    int getNotesCount();

    /**
     * 执行删除recyclerview操作
     * @param note
     * @param adapterPos
     * @param layoutPos
     */
    void clickDeleteNote(NoteBookBean note, int adapterPos, int layoutPos);

}
