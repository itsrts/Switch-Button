package com.itsrts.utility;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.SeekBar;

/**
 * Created by rajdeep on 26/3/15.
 */
public class SwitchButton extends SeekBar implements
		SeekBar.OnSeekBarChangeListener, OnTouchListener {

	private static SwitchButton rexSwitch;
	int threshold = 15;
	private int init;
	private GradientDrawable activeBackground, inactiveBackground,
			activeButton, inactiveButton;
	private String activeColor = "#00e500";
	private String inactiveColor = "#F0F0F0";
	private String borderColor = "#d4d4d4";
	private String buttonColor = "#ffffff";
	private long lastTime;
	private MyHandler handler;
	private static final int SWITCH_OFF = 121;
	private static final int SWITCH_OFF_BYUSER = 122;
	private static final int SWITCH_ON = 131;
	private static final int SWITCH_ON_BYUSER = 132;
	boolean working;

	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SWITCH_OFF:
			case SWITCH_OFF_BYUSER:
				if (rexSwitch.getProgress() <= 0) {
					visibleOff(false);
					working = false;
					if (msg.what == SWITCH_OFF_BYUSER)
						if (listener != null)
							listener.onSwitchButtonStatusChange(rexSwitch,
									false);
				} else {
					working = true;
					rexSwitch.setProgress(rexSwitch.getProgress() - 10);
					sendEmptyMessageDelayed(msg.what, 1);
				}
				break;
			case SWITCH_ON:
			case SWITCH_ON_BYUSER:
				if (rexSwitch.getProgress() >= rexSwitch.getMax()) {
					visibleOn(false);
					working = false;
					if (msg.what == SWITCH_ON_BYUSER)
						if (listener != null)
							listener.onSwitchButtonStatusChange(rexSwitch, true);
				} else {
					working = true;
					rexSwitch.setProgress(rexSwitch.getProgress() + 10);
					sendEmptyMessageDelayed(msg.what, 1);
				}
				break;
			default:
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("ClickableViewAccessibility")
	void init() {
		setOnSeekBarChangeListener(this);
		setOnTouchListener(this);
		setMax(100);
		setPadding(getHeight() / 2, 0, getHeight() / 2, 0);
		setProgressDrawable(null);
		if (getProgress() <= getMax() / 2) {
			setThumb(getInactiveButtonBackground());
			setBackgroundDrawable(getInactiveBackground());
		} else {
			setThumb(getActiveButtonBackground());
			setBackgroundDrawable(getActiveBackground());
		}
		if (rexSwitch == null)
			rexSwitch = this;
		if (handler == null)
			handler = new MyHandler();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w != 0 && h != 0)
			init();
	}

	public void setActiveColor(String activeColor) {
		this.activeColor = activeColor;
		init();
	}

	public void setInactiveColor(String inactiveColor) {
		this.inactiveColor = inactiveColor;
		init();
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		init();
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
		init();
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int dpToPx(int dp) {
		Resources r = getResources();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				r.getDisplayMetrics());
		return (int) px;
	}

	GradientDrawable getActiveBackground() {
		activeBackground = new GradientDrawable();
		activeBackground.setShape(GradientDrawable.RECTANGLE);
		activeBackground.setStroke(dpToPx(2), Color.parseColor(activeColor));
		activeBackground.setCornerRadius((getHeight() / 2));
		activeBackground.setColor(Color.parseColor(activeColor));
		return activeBackground;
	}

	GradientDrawable getInactiveBackground() {
		inactiveBackground = new GradientDrawable();
		inactiveBackground.setShape(GradientDrawable.RECTANGLE);
		inactiveBackground.setStroke(dpToPx(2), Color.parseColor(borderColor));
		inactiveBackground.setCornerRadius((getHeight() / 2));
		inactiveBackground.setColor(Color.parseColor(inactiveColor));
		return inactiveBackground;
	}

	GradientDrawable getActiveButtonBackground() {
		activeButton = new GradientDrawable();
		activeButton.setShape(GradientDrawable.OVAL);
		activeButton.setStroke(dpToPx(2), Color.parseColor(activeColor));
		activeButton.setSize((getHeight()), (getHeight()));
		activeButton.setColor(Color.parseColor(buttonColor));
		return activeButton;
	}

	GradientDrawable getInactiveButtonBackground() {
		inactiveButton = new GradientDrawable();
		inactiveButton.setShape(GradientDrawable.OVAL);
		inactiveButton.setStroke(dpToPx(2), Color.parseColor(borderColor));
		inactiveButton.setSize(getHeight(), getHeight());
		inactiveButton.setColor(Color.parseColor(buttonColor));
		return inactiveButton;
	}

	public interface OnSwitchButtonStatusChangeListener {
		void onSwitchButtonStatusChange(SwitchButton switchButton,
				boolean status);
	}

	OnSwitchButtonStatusChangeListener listener;

	public void setOnSwitchButtonStatusChangeListener(
			SwitchButton.OnSwitchButtonStatusChangeListener listener) {
		this.listener = listener;
	}

	@Override
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		super.setOnSeekBarChangeListener(this);
	}

	public boolean isOn() {
		return getProgress() == getMax();
	}

	public boolean isOff() {
		return getProgress() == 0;
	}

	public SwitchButton(Context context) {
		super(context);
		init();
	}

	public SwitchButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	public void off(final boolean byUser) {
		if (!working)
			if (byUser)
				handler.sendEmptyMessageDelayed(SWITCH_OFF_BYUSER, 5);
			else
				handler.sendEmptyMessageDelayed(SWITCH_OFF, 5);

	}

	public void on(final boolean byUser) {
		if (!working) {
			if (byUser)
				handler.sendEmptyMessageDelayed(SWITCH_ON_BYUSER, 5);
			else
				handler.sendEmptyMessageDelayed(SWITCH_ON, 5);
		}
	}

	public void toggle() {
		if (init == 0)
			on(true);
		else
			off(true);
	}

	@SuppressWarnings("deprecation")
	public void visibleOff(boolean forcefully) {
		setBackgroundDrawable(getInactiveBackground());
		setThumb(getInactiveButtonBackground());
		if (forcefully)
			setProgress(0);
	}

	@SuppressWarnings("deprecation")
	public void visibleOn(boolean forcefully) {
		setBackgroundDrawable(getActiveBackground());
		setThumb(getActiveButtonBackground());
		if (forcefully)
			setProgress(getMax());
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (!fromUser)
			return;
		if (init == 0) {
			if (getProgress() >= threshold)
				visibleOn(false);
			else
				visibleOff(false);
		} else {
			if (getProgress() <= getMax() - threshold)
				visibleOff(false);
			else
				visibleOn(false);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		if (getProgress() >= getMax() / 2)
			init = getMax();
		if (getProgress() < getMax() / 2)
			init = 0;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		if (init == 0) {
			if (getProgress() >= threshold)
				on(true);
			else
				off(false);
		} else {
			if (getProgress() <= getMax() - threshold)
				off(true);
			else
				on(false);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastTime = System.currentTimeMillis();
			break;
		case MotionEvent.ACTION_UP:
			if (lastTime != 0 && System.currentTimeMillis() - lastTime < 300) {
				toggle();
				return true;
			}
		default:
			break;
		}
		return false;
	}
}
