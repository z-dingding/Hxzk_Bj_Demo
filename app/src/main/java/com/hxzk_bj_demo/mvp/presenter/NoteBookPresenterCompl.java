package com.hxzk_bj_demo.mvp.presenter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.NoteBookBean;
import com.hxzk_bj_demo.mvp.model.NoteBookModel;
import com.hxzk_bj_demo.mvp.model.NoteBookModelCompl;
import com.hxzk_bj_demo.mvp.view.NoteBookActivity;
import com.hxzk_bj_demo.mvp.view.NoteBookView;
import com.hxzk_bj_demo.ui.adapter.NotesViewHolder;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ${赵江涛} on 2018-1-30.
 * 作用:
 */

public class NoteBookPresenterCompl implements  NoteBookPresenter {

    WeakReference<NoteBookView> mNoteBookView;
    NoteBookModel mNoteBookModel;

       //大部分都是直接传接口,此处传实现类
    public NoteBookPresenterCompl(NoteBookActivity noteBookActivity) {
        //注意这里不是this.xx=xx,而是new
        mNoteBookView = new WeakReference<NoteBookView>(noteBookActivity);
        mNoteBookModel=new NoteBookModelCompl();
    }


    @Override
    public void clickNewNoteBook(EditText mEditText) {
        if (!TextUtils.isEmpty(mEditText.getText())) {
            String content=mEditText.getText().toString();
            String date =getDate();
              NoteBookBean noteBookBean =new NoteBookBean(content,date);
            int insertIndex = mNoteBookModel.insertNoteBook(noteBookBean);
            if(insertIndex != -1){
                getView().clearEditext();
                getView().insertIetmNotify(insertIndex);
                getView().rangePositionNotify(insertIndex,mNoteBookModel.getNotesCount());
            }else{
                getView().showSnackBar("添加失败了⊙﹏⊙");
            }
        } else {
           getView().showSnackBar("\"请输点内容呗 ⊙﹏⊙\"");
        }
    }

    /**
     * 获取当前插入时间
     * @return
     */
    private String getDate() {
        return new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    @Override
    public void setView(NoteBookView noteBookView) {
        mNoteBookView = new WeakReference<NoteBookView>(noteBookView);
    }




    @Override
    public NotesViewHolder createViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewTaskRow = inflater.inflate(R.layout.item_notebook, parent, false);
        NotesViewHolder notesViewHolder =new NotesViewHolder(viewTaskRow);
        return notesViewHolder;
    }

    @Override
    public void bindViewHolder( NotesViewHolder notesViewHolder, int position) {
        NoteBookBean noteBookBean =mNoteBookModel.getNoteBook(position);
        notesViewHolder.tvContent.setText(noteBookBean.getContent());
        notesViewHolder.tvDate.setText(noteBookBean.getDate());
        notesViewHolder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastCustomUtil.showShortToast("点击了删除操作");
            }
        });
    }

    @Override
    public int getNotesCount() {
        return mNoteBookModel.getNotesCount();
    }




    //SnackBar调用需要
    public NoteBookView getView() throws NullPointerException {
        if (null != mNoteBookView) {
            return mNoteBookView.get();
        } else {
            throw new NullPointerException("");
        }
    }
}