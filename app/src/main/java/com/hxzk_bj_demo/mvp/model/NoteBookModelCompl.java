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
    public NoteBookBean getNoteBook(int id) {
        //根据每个item的id查询并不是position
        NoteBookBean bean =DataSupport.find(NoteBookBean.class,id);
        return bean;
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
              List<NoteBookBean> listData =DataSupport.findAll(NoteBookBean.class);
             for(int i=0;i<listData.size();i++){
                 //根据插入数据对象的时间判断是否插入成功
                 if(date.equals(listData.get(i).getDate())){
                     return i+1;
                 }
             }
          }
        return -1;
    }

    @Override
    public boolean deleteNote(NoteBookBean note, int adapterPos) {
        int bol =DataSupport.deleteAll(NoteBookBean.class,"date = ?",note.getDate());
        if(bol > 0){
            return true;
        }else{
            return false;
        }
    }




}
