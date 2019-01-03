package com.hxzk_bj_demo.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * CreateBy HaiyuKing
 * Used activity集合，用于保存打开的activity【栈——先进后出】
 */
public class ActivityManager {
	
	// 栈
	public static Stack<Activity> activityStack;
	
	private static ActivityManager managerInstance;

	/**
	 * 获得管理器
	 * */
	public static ActivityManager getScreenManager() {
		if (managerInstance == null) {
			managerInstance = new ActivityManager();
		}
		return managerInstance;
	}
	
	/**
	 * 增加一个Activity
	 * */
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 弹出栈顶的activity（也就是最后添加进去的activity）并finish
	 * */
	public void popLastActivity() {
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			activity.finish();
			activity = null;
		}
	}


	/**
	 * 弹出指定的activity并finish
	 * */
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}


	/**
	 * 获得栈顶的activity，也就是当前界面的上一个界面*/
	public Activity topActivity() {
		if (activityStack == null || activityStack.size() == 0) {
			return null;
		}
		Activity activity = activityStack.lastElement();
		return activity;
	}


	
	/**弹出所有的activity并finish*/
	public void popAllActivity() {
		while (true) {
			Activity activity = topActivity();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}


	/**弹出除去指定的activity以外所有的activity并finish*/
	public void popAllActivityExceptOne(Class<?> cls) {
		while (true) {
			Activity activity = topActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}




	/**弹出后加入的n个activity并finish*/
	public void popActivity(int num) {
		for (int i = 0; i < num; i++) {
			Activity activity = topActivity();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}


	/**打印栈里面的activity名称*/
	public void LogAllActivityNames(){
		if(activityStack != null && activityStack.size() > 0){
			for(int i=0;i<activityStack.size();i++){
				Activity activity = activityStack.get(i);
				LogUtil.e("BaseActivity", i + "::" + activity.getClass().getSimpleName());
			}
		}
	}


    /**
     * 通过类名得到当前Acivity在栈中的位置
	 * @param cls
     * @return
     */
	public int getIndexByClassName(Class<?> cls){
		for(int i=0;i<activityStack.size();i++){
			Activity activity = activityStack.get(i);
			if(activity.getClass().equals(cls)){
				return i;
			}
		}
		return 0;
	}



	/**
	 * 通过Activity名得到当前Acivity在栈中的位置
	 * @param activity
	 * @return
	 */
	public int getIndexByActivtiyName(Activity activity){
		for(int i=0;i<activityStack.size();i++){
			Activity activity0 = activityStack.get(i);
			if(activity0.getClass().equals(activity.getClass())){
				return i;
			}
		}
		return 0;
	}


    /**
     * 删除两个Activity之间的Activity
	 * @param from
     * @param to
	 */
	public void removeBetweenActivitys(int from, int to){
		for(int i=from;i>=to;i--){
			Activity activity = activityStack.get(i);
			if(activity != null){
				activityStack.remove(activity);
				activity.finish();//增加移除方法
			}
		}
	}



	/**
	 *
	 * @param clazz 类名
	 * @return 根据类名返回Activity
	 */
	public Activity getActivity(Class<?> clazz) {
		if (activityStack != null) {
			for (Activity activity : activityStack
					) {
				if (activity.getClass().equals(clazz)) {
					return activity;
				}
			}
		}
		return null;
	}
	
}
