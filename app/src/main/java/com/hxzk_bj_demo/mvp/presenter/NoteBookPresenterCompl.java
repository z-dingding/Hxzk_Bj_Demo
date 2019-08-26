package com.hxzk_bj_demo.mvp.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

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

    Context mContext ;

    /**
     *   大部分都是直接传接口,此处传实现类
     */
    public NoteBookPresenterCompl(NoteBookActivity noteBookActivity,Context context) {
        this.mContext =context;
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
                clickDeleteNote(noteBookBean, notesViewHolder.getAdapterPosition(), notesViewHolder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getNotesCount() {
        return mNoteBookModel.getNotesCount();
    }


    /**
     * 获取viewd层的实现类
     */
    public NoteBookView getView() throws NullPointerException {
        if (null != mNoteBookView) {
            return mNoteBookView.get();
        } else {
            throw new NullPointerException("");
        }
    }

    @Override
    public void clickDeleteNote(NoteBookBean note, int adapterPos, int layoutPos) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteNote(note, adapterPos, layoutPos);
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertBuilder.setMessage("确定删除该条记录？");
        AlertDialog alertDialog = alertBuilder.create();
        try {
            getView().showAlert(alertDialog);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }




    /**
     * 定义删除Rccyclerview单个数据的方法
     * @param note
     * @param adapterPos
     * @param layoutPos
     */
    public void deleteNote(final NoteBookBean note, final int adapterPos,final int layoutPos){
        new AsyncTask<Void,Void,Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {
                return mNoteBookModel.deleteNote(note,adapterPos) ;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(aBoolean){
                    getView().removeItemNotify(layoutPos);
                    getView().showSnackBar("已删除 0-0");
                }else{
                    getView().showSnackBar("...删除不了 ⊙﹏⊙");
                }

            }
        }.execute();
    }
}