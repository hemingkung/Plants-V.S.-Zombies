package cn.itcast.pvz713.view;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import cn.itcast.pvz713.biz.ViewService;

/**
 * 失败的弹框
 */
public class FaithLayer extends ModelLayer {

	public FaithLayer(){
		setIsTouchEnabled(true);
		init();
		
	}

	private void init() {
		//背景
		CCSprite bg = CCSprite.sprite("sub_video_bg.png");
		bg.setPosition(winSize.width/2, winSize.height/2);
		this.addChild(bg, tagBg, tagBg);
		//提示
		CCLabel label = CCLabel.labelWithString("连接失败, 请重试", font, 20);
		label.setPosition(winSize.width/2, winSize.height/2+30);
		label.setColor(ccColor3B.ccBLUE);
		this.addChild(label, tagInfo, tagInfo);
		
		//退出按钮
		CCSprite btn = CCSprite.sprite("menu_bn_quit.png");
		btn.setPosition(winSize.width/2, winSize.height/2-40);
		this.addChild(btn, tagBtn, tagBtn);
		
		
	}
	
	
	
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint aPoint = this.convertTouchToNodeSpace(event);
		CCSprite sp = (CCSprite) this.getChildByTag(tagBtn);
		
		if(CGRect.containsPoint(sp.getBoundingBox(), aPoint)){
			CCSprite btn = CCSprite.sprite("menu_bn_quit2.png");
			btn.setPosition(winSize.width/2, winSize.height/2-20);
			this.addChild(btn, tagBtns, tagBtns);
		}
		
		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint aPoint = this.convertTouchToNodeSpace(event);
		CCSprite sp = (CCSprite) this.getChildByTag(tagBtn);
		
		if(CGRect.containsPoint(sp.getBoundingBox(), aPoint)){
			
			CCDirector.theApp.finish();
			
		}
		
		
		
		return super.ccTouchesEnded(event);
	}




	private final int tagBg = 1;
	private final int tagInfo = 5;
	private final int tagBtn = 10;
	private final int tagBtns = 15;
}
