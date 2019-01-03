package com.hxzk_bj_demo.mvp.model;

import com.hxzk_bj_demo.javabean.NoteBookBean;
import com.hxzk_bj_demo.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

/**
 * Created by ${赵江涛} on 2018-1-30.
 * 作用:
 */

public class NoteBookModelCompl implements NoteBookModel {

    @Override
    public NoteBookBean getNoteBook(int position) {
        List<NoteBookBean> mListNoteBookBean =DataSupport.findAll(NoteBookBean.class);
        int listsize =mListNoteBookBean.size();
        NoteBookBean bean =DataSupport.find(NoteBookBean.class,position+1);
        return DataSupport.find(NoteBookBean.class,position+1);
    }

    @Override
    public int getNotesCount() {
        return DataSupport.findAll(NoteBookBean.class).size();
    }

    @Override
    public int insertNoteBook(NoteBookBean noteBookBean) {
        String date =noteBookBean.getDate();
          if(noteBookBean.save()){
              LogUtil.e(TAG,"数据库插入数据成功");
             // NoteBookBean bookBean = (NoteBookBean) DataSupport.where("content = ?", content).find(NoteBookBean.class);
              List<NoteBookBean> listData =DataSupport.findAll(NoteBookBean.class);
             for(int i=0;i<listData.size();i++){
                 if(date.equals(listData.get(i).getDate())){
                     return i+1;
                 }
             }
          }
        return -1;
    }

}
