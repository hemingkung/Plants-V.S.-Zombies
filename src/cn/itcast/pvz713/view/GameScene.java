package cn.itcast.pvz713.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXLayer;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import cn.itcast.pvz713.biz.GameData;

public class GameScene extends ModelLayer {
	private CCTMXTiledMap tiledMap;
	private ArrayList<CCSprite> touchList = new ArrayList<CCSprite>();
	private CGSize mapSize;
	private ArrayList<CGPoint> roadList = new ArrayList<CGPoint>();
	
	public GameScene(){
		super();
		initMap();
		parseMap();
		initView();
		//僵尸机制:一波8,完全消失才继续下一波
		schedule("createNpc", 4);
	}
	
	public void createNpc(float f){
		unschedule("createNpc");
		schedule("createNpcItem", 2);
	}
	
	int npcId = 0;
	private boolean isNext = false;
	public void createNpcItem(float f){
		
		if(!isNext && GameData.npcMap.size()<8){
			ArrayList<CGPoint> road = new ArrayList<CGPoint>();
			road.add(roadList.get(0));
			int i = vh.random(1, 5);
			road.add(roadList.get(i*2-1));
			road.add(roadList.get(i*2));
			
			Npc npc = Npc.createNpc("z_1_01.png", npcId, road);
			GameData.npcMap.put(npcId, npc);
			tiledMap.addChild(npc, tagNpc-npcId, tagNpc-npcId);
		}else{
			isNext = true;
		}
		if(GameData.npcMap.size()==0){
			isNext = false;
		}
		npcId++;
	}

	private void initView() {
		//顶部栏
		CCSprite topBar = CCSprite.sprite("sdbank.png");
		topBar.setScale(0.55f);
		topBar.setAnchorPoint(0.5f, 0.5f);
		topBar.setPosition(winSize.width/2, winSize.height-25);
		this.addChild(topBar, tagTobBar, tagTobBar);
		//金钱
		CCLabel money = CCLabel.labelWithString(String.valueOf(GameData.GameMoney), 
				font, 26);
		money.setAnchorPoint(0,0);
		money.setPosition(12,3);
		money.setColor(ccColor3B.ccRED);
		topBar.addChild(money, tagMoney, tagMoney);
		
		Iterator<Integer> it = GameData.selectPlantMap.keySet().iterator();
		while(it.hasNext()){
			int key = it.next();
			switch (GameData.selectPlantMap.get(key)) {
			case SelectScene.tagPlant://向日葵
				CCSprite sun = CCSprite.sprite("seed_flower.png");
				sun.setAnchorPoint(0,0);
				sun.setPosition(200, winSize.height-45);
				sun.setScale(0.3f);
				touchList.add(sun);
				this.addChild(sun, tagSun, tagSun);
				break;
			case SelectScene.tagPlant+1://豌豆
				CCSprite plant = CCSprite.sprite("seed_pea.png");
				plant.setAnchorPoint(0,0);
				plant.setPosition(300, winSize.height-45);
				plant.setScale(0.3f);
				touchList.add(plant);
				this.addChild(plant, tagPlant, tagPlant);
				break;
			default:
				break;
			}
		}
		
	}
	private boolean isTouchPlant = false;
	private CCSprite touchSprite;//点击植物,新创建的
	private String name;//将要被创建的植物路径
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint point = this.convertTouchToNodeSpace(event);
		for(CCSprite sp : touchList){
			if(CGRect.containsPoint(sp.getBoundingBox(), point)){
				if(sp.getTag()==tagSun){//向日葵
					name = "p_1_01.png";
					GameData.TowerType = GameData.Sun;
				}else{//豌豆
					GameData.TowerType = GameData.Plant;
					name = "p_2_01.png";
				}
				touchSprite = CCSprite.sprite(name);
				touchSprite.setScale(0.65f);
				touchSprite.setPosition(sp.getPosition());
				this.addChild(touchSprite, tagTouchPlant, tagTouchPlant);
				isTouchPlant = true;
				break;
			}
		}
		CGPoint mapPoint = tiledMap.convertTouchToNodeSpace(event);
		//销毁该销毁的阳光
		for(int i=GameData.sunList.size()-1; i>=0; i--){
			Sun sun = GameData.sunList.get(i);
			if(sun.isDestory()){
				GameData.sunList.remove(i);
			}
		}
		
		//处理阳光点击
		for(Sun sun : GameData.sunList){
			if(CGRect.containsPoint(sun.getBoundingBox(), mapPoint)){
				//计算地图拖动的距离
				int m = (int) (mapSize.width/2 - tiledMap.getPosition().x);
				CGPoint endPoint = ccp(140+m, winSize.height-30);
				sun.move(endPoint);
			}
		}
		
		
		
		return super.ccTouchesBegan(event);
	}
	private CGPoint midPoint;
	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint mapPoint = tiledMap.convertTouchToNodeSpace(event);
		
		//手指离开屏幕之前所点位置有图片;该位置能创建植物
		if(isTouchPlant && isCanCreatePlant(mapPoint) && !GameData.towerMap.containsKey(plantId) &&
				((GameData.TowerType==GameData.Sun && GameData.GameMoney>=50)
				|| GameData.TowerType==GameData.Plant && GameData.GameMoney>=100)){//在地图上创建植物
			int life = 0;
			if(GameData.TowerType == GameData.Sun){//向日葵
				life = 100;
				GameData.GameMoney -= 50;
			}else{
				GameData.GameMoney -= 100;
				life = 200;
			}
			refreshMoney();
			
			Tower tower = Tower.createTower(name, plantId, life, GameData.TowerType);
			tower.setPosition(midPoint);
			tiledMap.addChild(tower);
			GameData.towerMap.put(plantId, tower);
//			plantIdMap.put(plantId, plantId);
			
			
		}
		if(isTouchPlant){
			this.removeChildByTag(tagTouchPlant, true);
			isTouchPlant = false;
		}
		return super.ccTouchesEnded(event);
	}
	/**
	 * 刷新金钱
	 */
	public void refreshMoney() {
		CCLabel money = (CCLabel) this.getChildByTag(tagTobBar).getChildByTag(tagMoney);
		money.setString(String.valueOf(GameData.GameMoney));
	}
	private int plantId;
	//保存该图块的id,表示已经放了植物
//	private HashMap<Integer,Integer> plantIdMap = new HashMap<Integer, Integer>();
	private boolean isCanCreatePlant(CGPoint mapPoint) {
		int x = (int) (mapPoint.x/tiledMap.getTileSize().width);//列
		int y = (int) ((mapSize.height-mapPoint.y)/tiledMap.getTileSize().height);//行
		
		midPoint = ccp(x*tiledMap.getTileSize().width+23,
				mapSize.height-y*tiledMap.getTileSize().height-5);
		
		CCTMXLayer layer = tiledMap.layerNamed("块层 1");
		plantId = layer.tileGIDAt(ccp(x,y));
		HashMap<String, String> map = tiledMap.propertiesForGID(plantId);
		if(map!=null && map.get("buildable")!=null){
			return true;
		}
		return false;
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if(isTouchPlant){//点中了植物,进行对新创建的植物的拖动
			CGPoint point = this.convertTouchToNodeSpace(event);
			touchSprite.setPosition(point);
		}else{
			tiledMap.touchMove(event, tiledMap);
		}
		
		return super.ccTouchesMoved(event);
	}

	private void parseMap() {
		CCTMXObjectGroup group = tiledMap.objectGroupNamed("road01");
		if(group==null)return;
		ArrayList<HashMap<String, String>> array = group.objects;
		for(HashMap<String, String> map : array){
			String key = "x";
			int x = Integer.parseInt(map.get(key));
			key = "y";
			int y = Integer.parseInt(map.get(key));
			roadList.add(ccp(x, y));
		}
	}

	/**
	 * 初始化地图,执行动作
	 */
	private void initMap() {
		if(GameData.GameType == GameData.Day){
			tiledMap = CCTMXTiledMap.tiledMap("itcast_map_day.tmx");
		}else{
			tiledMap = CCTMXTiledMap.tiledMap("itcast_map_night.tmx");
		}
		mapSize = tiledMap.getContentSize();
		tiledMap.setAnchorPoint(0.5f, 0.5f);
		tiledMap.setPosition(mapSize.width/2, mapSize.height/2);
		
		CCMoveBy by = CCMoveBy.action(1, ccp(winSize.width - mapSize.width, 0));
		CCDelayTime delay = CCDelayTime.action(2);
		CCSequence seq = CCSequence.actions(by, delay, by.reverse());
		tiledMap.runAction(seq);
		this.addChild(tiledMap, tagMap, tagMap);
		
	}
	private final int tagMap = 1;
	private final int tagTobBar = 10;
	private final int tagSun = 20;
	private final int tagPlant = 30;
	private final int tagMoney = 40;
	private final int tagTouchPlant = 100;
	private final int tagNpc = 200;
}
