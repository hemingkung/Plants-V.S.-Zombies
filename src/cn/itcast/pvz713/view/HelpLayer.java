package cn.itcast.pvz713.view;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;
import android.widget.Toast;

public class HelpLayer extends ModelLayer {

	public HelpLayer(){
		setIsTouchEnabled(true);
		init();
		
		
		this.setPosition(0, -300);
		CCMoveBy by = CCMoveBy.action(0.5f, ccp(0, 300));
		this.runAction(by);
		
	}

	private void init() {
		
		CCSprite sp = CCSprite.sprite("seedbank.png");
		sp.setScaleX(0.6f);
		sp.setScaleY(0.45f);
		sp.setAnchorPoint(0,0);
		sp.setPosition(winSize.width-68, 74);
		this.addChild(sp, tagBg, tagBg);
		
		CCSprite btn = CCSprite.sprite("reputation.png");
		btn.setPosition(sp.getContentSize().width/2, sp.getContentSize().height/2);
		sp.addChild(btn,tagBtn, tagBtn);
		
		
	}
	
	
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint aPoint = this.getChildByTag(tagBg).convertTouchToNodeSpace(event);
		
		CCSprite sp = (CCSprite) this.getChildByTag(tagBg).getChildByTag(tagBtn);
		
		if(CGRect.containsPoint(sp.getBoundingBox(), aPoint)){
			
			CCDirector.theApp.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//ui线程执行
					HelpPop.show();
					
				}
			});
			
		}
		
		return super.ccTouchesEnded(event);
	}



	private final int tagBg = 1;//背景
	private final int tagBtn = 4;
}
