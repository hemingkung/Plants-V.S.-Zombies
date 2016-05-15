package cn.itcast.pvz713.view;

import java.util.ArrayList;
import java.util.Iterator;

import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXLayer;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.types.CGPoint;

import cn.itcast.pvz713.biz.GameData;

public class Npc extends ModelSprite {
	private ArrayList<CGPoint> roadPointArray;
	private int id;
	private int life = 100;
	private CCSequence seq;
	private int hurrt = 30;
	private boolean destory = false;

	private Npc(String name, ArrayList<CGPoint> roadPointArray) {
		super(name);
		this.roadPointArray = roadPointArray;
		this.setScale(0.6);
		
		
		this.setAnchorPoint(0,0);
		this.setPosition(roadPointArray.get(0));
		
		walk();
		schedule("refresh", 0.5f);
	}
	
	public static Npc createNpc(String name, int id, ArrayList<CGPoint> roadPointArray){
		Npc npc = new Npc(name, roadPointArray);
		npc.id = id;
		return npc;
	}
	private int num = 1;
	/**
	 * 行走功能
	 */
	private void walk(){
		float t = ccpDistance(this.getPosition(), roadPointArray.get(num))/40;
		CCMoveTo to = CCMoveTo.action(t, roadPointArray.get(num));
		CCCallFunc fun = CCCallFunc.action(this, "walkCall");
		seq = CCSequence.actions(to, fun);
		this.runAction(seq);
		
		CCAnimation anim = CCAnimation.animation("", 0.2f, 
				vh.getSpriteArray(GameData.Npc));
		CCAnimate a = CCAnimate.action(anim);
		this.runAction(CCRepeatForever.action(a));
	}
	public void walkCall(){
		stopAllActions();
		if(num==1){
			num++;
			walk();
		}else{
			detory();
		}
		
	}
	
	private int n = 0;
	public void refresh(float t){
//		if(n==3){
//			//销毁可以销毁的植物
//			Iterator<Integer> it = GameData.towerMap.keySet().iterator();
//			while(it.hasNext()){
//				int key = it.next();
//				Tower tower = GameData.towerMap.get(key);
//				if(tower.isDestory()){
//					GameData.towerMap.remove(key);
//					tower.removeSelf();
//				}
//			}
//		}
//		n++;
		
		
		//检测所在的图块id是否有植物
		CCTMXTiledMap tiledMap = (CCTMXTiledMap) this.getParent();
		int x = (int) (this.getPosition().x/tiledMap.getTileSize().width);//列
		int y = (int) ((tiledMap.getContentSize().height-getPosition().y)
				/tiledMap.getTileSize().height);//行
		CCTMXLayer layer = tiledMap.layerNamed("块层 1");
		int tiledId = layer.tileGIDAt(ccp(x,y));
		
		if(GameData.towerMap.containsKey(tiledId)){//npc所在的图块有植物
			
			this.stopAction(seq);
			Tower tower = GameData.towerMap.get(tiledId);
			int life = tower.getLife()-hurrt;
			if(life<=0){
				tower.destory();
				walk();
			}else{
				tower.setLife(life);
			}
			
		}
		
		
	}
	
	/**
	 * 销毁
	 */
	public void detory() {
		GameData.npcMap.remove(id);
		this.removeSelf();
		
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	
}
