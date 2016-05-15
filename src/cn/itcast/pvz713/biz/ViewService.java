package cn.itcast.pvz713.biz;

import java.util.Calendar;

import org.cocos2d.nodes.CCDirector;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

public class ViewService {
	private static String key = "days713";
	/**
	 * 获取累计天数
	 * @return
	 */
	public static String getDays() {
		int days = 1;//累计天数
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		if(!TextUtils.isEmpty(LocalData.getStrFromPref(key))){
			String str = LocalData.getStrFromPref(key);//累计天数_该天在一年中的累计天数
			String[] array = str.split("_");
			if(day==Integer.valueOf(array[1])){//同一天登录
				return array[0];
			}else{
				days = Integer.valueOf(array[0])+1;
			}
		}
		LocalData.saveStrToPref(key, days+"_"+day);
		return String.valueOf(days);
	}
	/**
	 * 获取版本号
	 * @return
	 */
	public static String getVersion() {
		
		PackageManager pm = CCDirector.theApp.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(CCDirector.theApp.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
