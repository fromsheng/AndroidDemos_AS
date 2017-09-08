package com.artion.androiddemos.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Looper;

/**
 * Loading加载框管理类，已做单例
 * 
 * @author jinsheng_cai
 * @since 2013-10-30
 */
public class LoadingDialog {

	private ProgressDialog progressDialog;

	private static InternalHandler sHandler = null;

	private static LoadingDialog instance = null;

	private LoadingDialog() {
	}

	public static LoadingDialog getInstance() {
		if (instance == null) {
			instance = new LoadingDialog();
		}

		return instance;
	}

	private static Handler getHandler() {
		synchronized (LoadingDialog.class) {
			if (sHandler == null) {
				sHandler = new InternalHandler();
			}
			return sHandler;
		}
	}

	/**
	 * 显示Loading，默认不可取消
	 *
	 * @param title
	 */
	public void showLoading(Context context, String title) {

		showLoading(context, title, false, false);

	}

	public void showLoading(Context context, String title,
							boolean isCanceledOnTouchOutside, ProgressListener listener) {
		showLoading(context, title, true, isCanceledOnTouchOutside, listener);
	}

	private void showLoading(final Context context, final String title,
							 final boolean isCancelable, final boolean isCanceledOnTouchOutside, final ProgressListener listener) {
		getHandler().post(new Runnable() {
			@Override
			public void run() {
				try {
					initProgressDialog(context, title);

					progressDialog.setCancelable(isCancelable);
					progressDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

					if (isCancelable) {
						progressDialog.setOnCancelListener(new OnCancelListener() {

							@Override
							public void onCancel(DialogInterface dialog) {
								if (listener != null) {
									listener.onCancel(dialog);
								}
							}
						});
					}

					progressDialog.show();
				} catch (Exception e) {

				}
			}
		});
	}

	/**
	 * 显示Loading，可控制是否按返回键取消及是否按框外取消
	 *
	 * @param title
	 * @param isCancelable
	 *            是否按返回键取消
	 * @param isCanceledOnTouchOutside
	 *            是否按框外取消
	 */
	public void showLoading(Context context, String title,
							boolean isCancelable, boolean isCanceledOnTouchOutside) {

		showLoading(context, title, isCancelable, isCanceledOnTouchOutside, null);

	}

	/**
	 * 显示Loading，默认不可取消
	 *
	 * @param titleRes
	 */
	public void showLoading(Context context, int titleRes) {

		showLoading(context, context.getString(titleRes));

	}

	/**
	 * 显示Loading，可控制是否按返回键取消及是否按框外取消
	 *
	 * @param titleRes
	 * @param isCancelable
	 *            是否按返回键取消
	 * @param isCanceledOnTouchOutside
	 *            是否按框外取消
	 */
	public void showLoading(Context context, int titleRes,
							boolean isCancelable, boolean isCanceledOnTouchOutside) {

		showLoading(context, context.getString(titleRes), isCancelable,
				isCanceledOnTouchOutside);

	}

	private synchronized void initProgressDialog(Context context, String msg) {
		if (progressDialog != null && progressDialog.isShowing()) {
			dismiss();
		}

		progressDialog = new ProgressDialog(context);

		progressDialog.setMessage(msg);

		progressDialog.setCancelable(false);//默认不可取消
		progressDialog.setCanceledOnTouchOutside(false);
	}

	private void dismiss() {
		try {
			if (null != progressDialog && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		} catch (Exception e) {

		} finally {
			progressDialog = null;
		}
	}

	/**
	 * 隐藏Loading框
	 */
	public void dismissLoading() {
		getHandler().post(new Runnable() {
			@Override
			public void run() {
				dismiss();
			}
		});
	}

	public interface ProgressListener {
		public void onCancel(DialogInterface dialog);
	}

	private static class InternalHandler extends Handler {
		public InternalHandler() {
			super(Looper.getMainLooper());
		}
	}
}
