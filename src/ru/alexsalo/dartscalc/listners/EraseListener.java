package ru.alexsalo.dartscalc.listners;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class EraseListener implements OnTouchListener{
	
	private TextView mtv;
	private CharSequence mdummy_zero;
	
	public EraseListener(TextView tv, CharSequence dummy_zero) {
		mtv = tv;
		mdummy_zero = dummy_zero; 
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		CharSequence text = mtv.getText();
		if (text.length() > 1)
			mtv.setText("0" + text.charAt(0));
		else
			mtv.setText(mdummy_zero);
		return false;
	}
}
