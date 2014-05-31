package com.conradhaupt.bookmarker.views;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.LinearLayout;

public class CheckableLinearLayout extends LinearLayout implements Checkable {
	/* Variables */
	// Object Variables
	private boolean isChecked;
	private List<Checkable> checkableViews;

	// Static Variables

	/* Methods */
	public CheckableLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CheckableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CheckableLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		final int childCount = this.getChildCount();
		for (int i = 0; i < childCount; i++) {
			findCheckableChildren(this.getChildAt(i));
		}
	}

	private void findCheckableChildren(View view) {
		if (view instanceof Checkable) {
			this.checkableViews.add((Checkable) view);
		}
		if (view instanceof ViewGroup) {
			final ViewGroup vg = (ViewGroup) view;
			final int childCount = vg.getChildCount();
			for (int i = 0; i < childCount; i++) {
				findCheckableChildren(vg.getChildAt(i));
			}
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		this.isChecked = checked;
		if (checkableViews != null) {
			for (Checkable c : checkableViews) {
				try {
					c.setChecked(checked);
				} catch (NullPointerException e) {
					System.out.println(e);

				}
			}
		}
	}

	@Override
	public void toggle() {
		setChecked(!(this.isChecked));
	}

}
