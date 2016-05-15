package cn.itcast.pvz713.view;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import cn.itcast.pvz713.biz.GameData;

public class Sun extends ModelSprite {
	private int money = 25;
	private boolean isDestory = false;
	
	private Sun(String name) {
		super(name);
		this.setScale(0.6);
		
		schedule("call", 3);
	}
	
	public void call(float f){
		unschedule("call");
		
		CCMoveBy by = CCMoveBy.action(1.5f, ccp(0, -300));
		this.runAction(by);
		
		isDestory = true;
	}
	
	public static Sun createSun(String name){
		Sun sun = new Sun(name);
		return sun;
	}
	
	public void move(CGPoint endPoint){
		if(isDestory)return;
		
		CCMoveTo to = CCMoveTo.action(0.8f, endPoint);
		CCCallFunc fun = CCCallFunc.action(this, "moveCall");
		
		this.runAction(CCSequence.actions(to, fun));
		
	}
	public void moveCall(){
		
		GameData.GameMoney += money;
		
		GameScene scene = (GameScene) this.getParent().getParent();
		scene.refreshMoney();
		isDestory = true;
		this.runAction(CCHide.action());
	}
	
	public boolean isDestory(){
		return isDestory;
	}
	
}
