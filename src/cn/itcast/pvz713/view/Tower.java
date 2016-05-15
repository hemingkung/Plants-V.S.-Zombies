package cn.itcast.pvz713.view;

import java.util.Iterator;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCSprite;

import cn.itcast.pvz713.biz.GameData;

public class Tower extends ModelSprite {
	private int id;
	private int life;//生命值
	private int type;//类型
	private boolean isDestory = false;
	
	
	private Tower(String name, int type){
		super(name);
		
		this.setScale(0.65);
		
		if(type==GameData.Sun){
			//5秒创建一个阳光
			schedule("buildSun", 5);
		}else{
			schedule("createBullet", 2);
		}
		//序列帧动画
		
		CCAnimation anim = CCAnimation.animation("", 0.2f, vh.getSpriteArray(type));
		CCAnimate a = CCAnimate.action(anim);
		this.runAction(CCRepeatForever.action(a));
	}
	
	
	public static Tower createTower(String name, int id, int life, int type){
		Tower tower = new Tower(name, type);
		tower.id = id;
		tower.type = type;
		tower.life = life;
		return tower;
	}
	/**
	 * 5秒创建一个阳光
	 * @param f
	 */
	public void buildSun(float f){
		Sun sun = Sun.createSun("sun.png");
		sun.setPosition(this.getPosition().x+30, getPosition().y-25);
		GameData.sunList.add(sun);
		this.getParent().addChild(sun);
	}
	public void createBullet(float f){
		if(GameData.npcMap.size()!=0){
			Iterator<Integer> it = GameData.npcMap.keySet().iterator();
			while(it.hasNext()){
				int key = it.next();
				Npc npc = GameData.npcMap.get(key);
				//控制子弹的创建条件
				if(npc.getPosition().x-getPosition().x<150){
					String name = "";
					if(vh.random(1, 2)==1){
						name = "bullet.png";
					}else{
						name = "bullet_1.png";
					}
					Bullet bullet = Bullet.createBullet(name, npc);
					bullet.setAnchorPoint(0,0);
					bullet.setPosition(getPosition().x+14, getPosition().y+8);
					this.getParent().addChild(bullet);
					break;
				}
			}
		}
	}


	public int getLife() {
		return life;
	}


	public void setLife(int life) {
		this.life = life;
	}
	
	public void destory(){
		this.runAction(CCHide.action());
		isDestory = true;
		schedule("remove", 1);
	}
	public void remove(float f){
		unschedule("remove");
		GameData.towerMap.remove(id);
		this.removeSelf();
	}
	public boolean isDestory(){
		return isDestory;
	}
	
}
