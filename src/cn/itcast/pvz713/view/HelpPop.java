package cn.itcast.pvz713.view;

import cn.itcast.pvz713.R;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

/**
 *  帮助界面
 */
public class HelpPop {
	
	public static void show(){
		final Dialog d = ViewHelp.getDialog();
		d.setContentView(R.layout.help);
		
		d.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				d.dismiss();
				
			}
		});
		
		d.show();
	}
	
	
}
