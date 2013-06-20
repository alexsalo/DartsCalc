package ru.alexsalo.dartscalc.listners;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class NumberListner implements OnTouchListener{
	
		private TextView mtv;
		private CharSequence mdummy_zero;
		
		public NumberListner(TextView tv, CharSequence dummy_zero) {
			mtv = tv;
			mdummy_zero = dummy_zero; 
		}
		
		public boolean onTouch(View v, MotionEvent event) {
			CharSequence text = mtv.getText();
			int n = (Integer) v.getTag();
			if (text.equals(mdummy_zero) || text.equals("0"))
				if (n!=0)
					mtv.setText("0" + String.valueOf(n));
				else 
					mtv.setText("0");
			else if (Integer.parseInt(text.toString()) * 10 + n <= 60)
				mtv.setText(mtv.getText().charAt(1)
						+ String.valueOf(n));
			return false;
		}
	
}
