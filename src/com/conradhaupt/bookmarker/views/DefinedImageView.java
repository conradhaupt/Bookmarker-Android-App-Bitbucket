package com.conradhaupt.bookmarker.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class DefinedImageView extends ImageView {
	/* Variables */
	// Object Variables

	// Static Variables

	/* Methods */
	public DefinedImageView(Context context) {
		super(context);
	}

	public DefinedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DefinedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Process wrap content
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = width * getDrawable().getIntrinsicHeight()
				/ getDrawable().getIntrinsicWidth();
		setMeasuredDimension(width, height);
	}
}