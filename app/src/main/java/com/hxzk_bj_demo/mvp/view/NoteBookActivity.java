package com.hxzk_bj_demo.mvp.view;


import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.javabean.NoteBookBean;
import com.hxzk_bj_demo.mvp.presenter.NoteBookPresenter;
import com.hxzk_bj_demo.mvp.presenter.NoteBookPresenterCompl;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.NotesViewHolder;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${赵江涛} on 2018-1-30.
 * 作用:
 */

public class NoteBookActivity extends BaseBussActivity implements NoteBookView {


    @BindView(R.id.edt_content_notebook)
    EditText mEdt_Content_Notebook;
    @BindView(R.id.recycler_notebook)
    RecyclerView mRecycler_Notebook;
    @BindView(R.id.fab_add_notebook)
    FloatingActionButton mFab_Add_Notebook;
    @BindView(R.id.realtive_notebook)
    RelativeLayout mRealtive_Notebook;


    NoteBookPresenter mNoteBookPresenter;
      NoteBookAdapter mNoteBookAdapter;

    @Override
    protected int setLayoutId() {
        _context = NoteBookActivity.this;
        return R.layout.activity_notebook;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar(R.drawable.back, "笔记本");
        initMVP();
    }

    private void initMVP() {
        //将当前Activity通过有参构造传入
        mNoteBookPresenter = new NoteBookPresenterCompl(this,NoteBookActivity.this);

        mNoteBookAdapter = new NoteBookAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecycler_Notebook.setLayoutManager(linearLayoutManager);
        mRecycler_Notebook.setAdapter(mNoteBookAdapter);
        mRecycler_Notebook.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }


    @OnClick({R.id.edt_content_notebook, R.id.fab_add_notebook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_content_notebook:
                break;
            case R.id.fab_add_notebook:
                mNoteBookPresenter.clickNewNoteBook(mEdt_Content_Notebook);
                break;
                default:
        }
    }



    @Override
    public void showSnackBar(String msg) {
        //参数一与toast有所不同
        Snackbar.make(mRealtive_Notebook, msg, Snackbar.LENGTH_LONG).show();
    }


    /**
     *  添加成功之后清空Edittext输入框
     */
    @Override
    public void clearEditext() {
        mEdt_Content_Notebook.setText("");
    }

    @Override
    public void insertIetmNotify(int position) {
          int indexPosition =position;
          mNoteBookAdapter.notifyItemInserted(position);
    }

    @Override
    public void rangePositionNotify(int startPosition, int stopPosition) {
        int mindexPosition =startPosition;
        int mstopPosition =stopPosition;
        mNoteBookAdapter.notifyItemRangeChanged(startPosition,stopPosition);
    }

    @Override
    public void removeItemNotify(int position) {
        mNoteBookAdapter.notifyItemRemoved(position);
    }

    @Override
    public void notifyDataSetChanage() {

    }

    @Override
    public void showAlert(AlertDialog alertDialog) {
        alertDialog.show();
    }


    /**
     * 适配器
     */
    private   class NoteBookAdapter extends RecyclerView.Adapter<NotesViewHolder> {
        @Override
        public int getItemCount() {
            //返回数据库数据条目
            int count = mNoteBookPresenter.getNotesCount();
            return count;
        }

        @Override
        public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mNoteBookPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(NotesViewHolder holder, int position) {
                List<NoteBookBean> noteBookBeanList = DataSupport.findAll(NoteBookBean.class);
                NoteBookBean noteBookBean = noteBookBeanList.get(position);
                 int id =noteBookBean.getId();
                 mNoteBookPresenter.bindViewHolder(holder, id);
        }
    }


}