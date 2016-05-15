/**
 * 
 */
package cn.itcast.pvz713.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.ccColor3B;

import android.view.MotionEvent;

import cn.itcast.pvz713.biz.GameData;
import cn.itcast.pvz713.biz.ViewService;

/**
 * @author Administrator
 *
 */
public class SelectScene extends ModelLayer {
	//为绘制的的植物更方便处理事件
	private ArrayList<CCSprite> spList = new ArrayList<CCSprite>();
	//为选中的植物更好的处理事件
	private ArrayList<CCSprite> selectList = new ArrayList<CCSprite>();
	
	public SelectScene(){
		init();
		
	}

	private void init() {
		//背景
		addChild(vh.getSprite("bk1.jpg", ccp(winSize.width-678, 0)), tagBg, tagBg);
		
		//topbar
		CCSprite bar = CCSprite.sprite("seedbank.png");
		bar.setRotation(90);
		bar.setScale(0.6);
		bar.setPosition(winSize.width/2, winSize.height-25);
		addChild(bar, tagTopBar, tagTopBar);
		//等级
		this.addChild(vh.getText("等级:"+GameData.GameLevel, ccp(15,winSize.height-30)
				, 18), tagLevel, tagLevel);
		
		//放植物的背景
		addChild(vh.getSprite("seedstore.png", ccp(5, 0), 0.5f),
				tagPlantBg, tagPlantBg);
		
		//开始游戏
		addChild(vh.getSprite("button4.png", ccp(winSize.width-140, 0)), tagPlay, tagPlay);
		
		//绘制植物
		for(int i=0;i<5; i++){
			CCSprite sp = CCSprite.sprite(String.format("xx%02d.png", i+1));
			sp.setAnchorPoint(0,0);
			sp.setPosition(20+65*(i%3), 150-85*(i/3));
			addChild(sp, tagPlant+i, tagPlant+i);
			spList.add(sp);
		}
		
		
		
	}
	//添加选中的植物
	private HashMap<Integer, Integer> selectMap = new HashMap<Integer, Integer>();
	
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint point = this.convertTouchToNodeSpace(event);
		int i=1;
		for(CCSprite sp : spList){
			if(CGRect.containsPoint(sp.getBoundingBox(), point) 
					&& selectMap.get(sp.getTag())==null){
				CCSprite sprite = CCSprite.sprite(String.format("xx%02d.png", i));
				sprite.setScale(0.5);
				sprite.setAnchorPoint(0,0);
				sprite.setPosition(sp.getPosition());
				this.addChild(sprite, tagSelectPlant+i, tagSelectPlant+i);
				
				CCMoveTo to = CCMoveTo.action(0.5f, ccp(160+selectMap.size()*50, winSize.height-45));
				sprite.runAction(to);
				
				selectList.add(sprite);
				GameData.selectPlantMap.put(tagSelectPlant+i, sp.getTag());
				selectMap.put(sp.getTag(), tagSelectPlant+i);
				return false;
			}
			i++;
		}
		
		//判断选中的植物是否被点击
		for(int j=0; j<selectList.size(); j++){
			CCSprite selctSp = selectList.get(j);
			if(CGRect.containsPoint(selctSp.getBoundingBox(), point)){
				//移除本身
				selctSp.removeSelf();
				//selectList中对应的数据删除
				selectList.remove(j);
				//selectMap中对应的数据删除
				Iterator<Integer> it = selectMap.keySet().iterator();
				while (it.hasNext()) {
					int key = it.next();
					int tag = selectMap.get(key);
					if(tag==selctSp.getTag()){
						selectMap.remove(key);	
						break;
					}
				}
				//GameData.selectPlantMap中对应的数据删除
				GameData.selectPlantMap.remove(selctSp.getTag());
				//让处在右边的选中植物执行向左移一位的动画
				for(int a=j; a<selectList.size(); a++){
					CCSprite moveSp = selectList.get(a);
					CCMoveBy by = CCMoveBy.action(0.3f, ccp(-50, 0));
					moveSp.runAction(by);
				}
				break;
			}
		}
		//
		
		return super.ccTouchesBegan(event);
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.ccTouchesMoved(event);
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint point = this.convertTouchToNodeSpace(event);
		CCSprite sp = (CCSprite) this.getChildByTag(tagPlay);
		if(CGRect.containsPoint(sp.getBoundingBox(), point)){
			
			vh.changeScene(new GameScene());
		}
		
		return super.ccTouchesEnded(event);
	}


	private final int tagBg = 1;
	private final int tagTopBar = 5;
	private final int tagPlantBg = 10;
	private final int tagLevel = 15;
	private final int tagPlay = 20;
	public static final int tagPlant = 30;
	private final int tagSelectPlant = 100;
	
}
