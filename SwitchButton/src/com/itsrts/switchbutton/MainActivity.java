package com.itsrts.switchbutton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.itsrts.utility.SwitchButton;
import com.itsrts.utility.SwitchButton.OnSwitchButtonStatusChangeListener;

public class MainActivity extends Activity implements
		OnSwitchButtonStatusChangeListener {

	SwitchButton switchButton;
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView);
		switchButton = (SwitchButton) findViewById(R.id.switchbutton);
		switchButton.setActiveColor("#34aacd");
		switchButton.visibleOff(true);
		switchButton.setOnSwitchButtonStatusChangeListener(this);
	}

	@Override
	public void onSwitchButtonStatusChange(SwitchButton switchButton,
			boolean status) {
		String str = textView.getText().toString();
		if (status)
			str += "\nStatus Changed : TRUE/ON";
		else
			str += "\nStatus Changed : FALSE/OFF";
		textView.setText(str);

		Date d = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(d.getYear(), d.getMonth(), d.getDate(), d.getHours(),
				d.getMinutes(), d.getSeconds());
		calendar.getTimeInMillis();
		d = calendar.getTime();
		d.getTime();
		
		System.currentTimeMillis();

		TimeZone zone = TimeZone.getDefault();
		zone.getRawOffset();

		long asd = calendar.get(Calendar.ZONE_OFFSET)
				+ calendar.get(Calendar.DST_OFFSET);
		asd = Calendar.getInstance().getTimeInMillis() - asd;

		String timeZone = new SimpleDateFormat("Z").format(new Date());
		String time = timeZone.substring(0, 3) + ":" + timeZone.substring(3, 5);
		long milli = 0;
		int factor = 1;
		if (timeZone.contains("-"))
			factor = -1;
		try {
			int hours = Integer.parseInt(timeZone.substring(0, 3)
					.replace("+", "").replace("-", ""));
			int min = Integer.parseInt(timeZone.substring(3, 5)
					.replace("+", "").replace("-", ""));
			milli = ((hours * 60 + min) * 60) * 1000;
		} catch (Exception e) {
		}
		Log.e("rex", time);
		Log.e("rex", "" + asd);

	}
}
