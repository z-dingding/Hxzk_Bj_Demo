package com.hxzk_bj_demo.mvp.view;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.mvp.presenter.NoteBookPresenter;
import com.hxzk_bj_demo.mvp.presenter.NoteBookPresenterCompl;
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity;
import com.hxzk_bj_demo.ui.adapter.NotesViewHolder;

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
        mNoteBookPresenter = new NoteBookPresenterCompl(this);


        mNoteBookAdapter = new NoteBookAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
        }
    }


    @Override
    public void showSnackBar(String msg) {
        //参数一与toast有所不同
        Snackbar.make(mRealtive_Notebook, msg, Toast.LENGTH_SHORT).show();
    }


    //添加成功之后清空Edittext输入框
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
    public void removeItemNotify(int postion) {

    }

    @Override
    public void notifyDataSetChanage() {

    }


    /**
     * 适配器
     */
    private   class NoteBookAdapter extends RecyclerView.Adapter<NotesViewHolder> {


        @Override
        public int getItemCount() {
            int count = mNoteBookPresenter.getNotesCount();
            return mNoteBookPresenter.getNotesCount();
        }

        @Override
        public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return mNoteBookPresenter.createViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(NotesViewHolder holder, int position) {
                 mNoteBookPresenter.bindViewHolder(holder, position);
        }
    }


}