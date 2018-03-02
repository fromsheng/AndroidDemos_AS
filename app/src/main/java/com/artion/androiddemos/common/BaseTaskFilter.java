package com.artion.androiddemos.common;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 任务过滤基类，子类继承后可配合单例执行。
 * <br/>实现同一类型任务添加执行可过滤掉重复的执行
 * @param <T>
 */
public abstract class BaseTaskFilter<T> {
	private Hashtable<String, T> mTaskMap;
	private AtomicBoolean mExecRunning = new AtomicBoolean(false);

	public void submitTask(String key, T obj) {
		putTask(key, obj);
		execTask(key);
	}

	protected void putTask(String key, T obj) {
		if(key == null || obj == null) {
			return;
		}
		if(mTaskMap == null) {
			mTaskMap = new Hashtable<String, T>();
		}
		mTaskMap.put(key, obj);
	}

	protected Object getTask(String key) {
		if(key == null) {
			return null;
		}
		if(mTaskMap != null) {
			return mTaskMap.get(key);
		}
		return null;
	}
	
	public void clearTask() {
		if(mTaskMap != null) {
			mTaskMap.clear();
		}
	}
	
	public abstract void execTaskReally(T t);
	public abstract boolean isSame(T t1, T t2);
	
	private void execTaskInThread(String key) {
		if(key == null) {
			return;
		}
		T obj = (T) getTask(key);
		if(obj != null) {
			execTaskReally(obj);
			T obj2 = (T) getTask(key);
			if(obj2 != null && !isSame(obj, obj2)) {
				//更新了，再次执行一次
				execTaskInThread(key);
			}
		}
	}
	
	
	/**
	 * 执行任务
	 * 暂缺失:如果任务同时put并且exec时会出现有最新的任务不执行，如果put时不在同一循环应该没啥问题
	 * 		eg:for(){new Thread(){put and exec}}
	 * @param key
	 */
	protected void execTask(String key) {
		//TODO 非空判断及锁控制，线程执行
		if(key == null) {
			return;
		}
		if(mExecRunning.get()) {
			return;
		}
		mExecRunning.set(true);
		execTaskInThread(key);
		mExecRunning.set(false);
	}
}
