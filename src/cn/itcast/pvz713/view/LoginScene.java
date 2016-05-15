package cn.itcast.pvz713.view;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import cn.itcast.pvz713.R;
import cn.itcast.pvz713.biz.GameData;
import cn.itcast.pvz713.biz.ViewService;


public class LoginScene extends ModelLayer {

	public LoginScene(){
		
		GameData.init();
		init();
		
		schedule("call", 0.5f);
		
	}
	public void call(float t){
		unschedule("call");//把call定时器销毁
		
		FaithLayer layer = new FaithLayer();
		this.addChild(layer, tagfailPop, tagfailPop);
		
		vh.changeScene(new HomeScene());
		
	}

	private void init() {
		//背景
		CCSprite bg = CCSprite.sprite("cover.jpg");
		bg.setScale(0.6f);
		bg.setAnchorPoint(0,0);
		this.addChild(bg, tagBg, tagBg);
		
		//累计天数
		CCSprite dayBg = CCSprite.sprite("well_detail.png");
		dayBg.setPosition(winSize.width/2, 36);
		this.addChild(dayBg,tagDayBg, tagDayBg);
		
		CCLabel label = CCLabel.labelWithString(
				CCDirector.theApp.getResources().getString(R.string.day1)+ViewService.getDays()
				+CCDirector.theApp.getResources().getString(R.string.day2)
				, font, 20);
		label.setAnchorPoint(0,0);
		label.setPosition(15, 25);
		dayBg.addChild(label, tagDayInfo, tagDayInfo);
		
		//进度条
		CCSprite bar = CCSprite.sprite("sc_publish_spin.png");
		bar.setPosition(winSize.width/2, winSize.height/2);
		this.addChild(bar, tagProgress, tagProgress);
		
		CCRotateBy by = CCRotateBy.action(1, 180);
		bar.runAction(CCRepeatForever.action(by));
		
		//版本号
		label = CCLabel.labelWithString("版本号:"+ViewService.getVersion(), font, 20);
		label.setAnchorPoint(0,0);
		label.setPosition(15, 25);
		label.setColor(ccColor3B.ccBLUE);
		this.addChild(label, tagVersion, tagVersion);
		
		
		
		
		
	}
	private final int tagBg = 1;//背景
	private final int tagDayBg = 5;
	private final int tagDayInfo = 10;
	private final int tagProgress = 15;
	private final int tagVersion = 20;
	private final int tagfailPop = 25;
	
}
