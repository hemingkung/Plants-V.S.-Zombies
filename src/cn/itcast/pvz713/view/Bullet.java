package cn.itcast.pvz713.view;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

public class Bullet extends ModelSprite {
	private int hurrt = 25;
	private Npc npc;
	
	public Bullet(String name, Npc npc) {
		super(name);
		this.npc = npc;
		this.setScale(0.6);
		
		float t = ccpDistance(this.getPosition(), npc.getPosition())/350;
		CGPoint p = npc.getPosition();
		
		CCMoveTo to = CCMoveTo.action(t, ccp(p.x-15, p.y+15));
		
		CCCallFunc fun = CCCallFunc.action(this, "call");
		this.runAction(CCSequence.actions(to, fun));
		
	}

	public static Bullet createBullet(String name, Npc npc) {
		Bullet bullet = new Bullet(name, npc);
		return bullet;
	}
	
	public void call(){
		
		int life = npc.getLife()-hurrt;
		if(life<=0){
			npc.detory();
		}else{
			npc.setLife(life);
		}
		this.removeSelf();
	}

}
