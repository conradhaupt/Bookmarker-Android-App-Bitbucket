package com.conradhaupt.bookmarker.views;

import com.conradhaupt.bookmarker.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class DefinedImageView extends ImageView {
	/* Variables */
	// Object Variables
	private int mode = MODE_BASE_HEIGHT;

	// Static Variables
	public static final int MODE_BASE_WIDTH = 0;
	public static final int MODE_BASE_HEIGHT = 1;

	/* Methods */
	public DefinedImageView(Context context) {
		super(context);
	}

	public DefinedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		handleAttributes(context, attrs);
	}

	public DefinedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		handleAttributes(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Process wrap content
		int width = 0;
		int height = 0;
		if (getMode() == MODE_BASE_WIDTH) {
			// Handle if the image must be based off of its width
			width = MeasureSpec.getSize(widthMeasureSpec);
			height = width * getDrawable().getIntrinsicHeight()
					/ getDrawable().getIntrinsicWidth();
			// } else if (getMode() == MODE_BASE_HEIGHT) {
		} else {
			// Handle if the image must be based off of its height
			height = MeasureSpec.getSize(heightMeasureSpec);
			width = height * getDrawable().getIntrinsicWidth()
					/ getDrawable().getIntrinsicHeight();
		}
		System.out.println("Measured Dimensions: " + width + "X" + height
				+ " with mode "
				+ (mode == MODE_BASE_HEIGHT ? "height" : "width"));
		setMeasuredDimension(width, height);
	}

	private void handleAttributes(Context context, AttributeSet attrs) {
		// Get a typed array of all the values
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.DefinedImageView, 0, 0);

		// Alter values depending on the attributes
		try {
			setMode(ta.getInteger(R.styleable.DefinedImageView_mode,
					MODE_BASE_HEIGHT));
		} finally {
			// Recycle the typedArray
			ta.recycle();
		}

		invalidate();
		requestLayout();
	}

	/**
	 * @return the mode
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
		invalidate();
		requestLayout();
	}
}