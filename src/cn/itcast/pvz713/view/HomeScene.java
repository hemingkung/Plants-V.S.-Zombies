package cn.itcast.pvz713.view;

import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCShow;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

import cn.itcast.pvz713.biz.GameData;

public class HomeScene extends ModelLayer {
	
	public HomeScene(){
		init();
		menu();
	}

	private void menu() {
		CCMenu menu = CCMenu.menu();
		menu.setPosition(0,0);
		
		CCSprite normalSprite = CCSprite.sprite("map_button_menu.png");
		CCSprite selectedSprite = CCSprite.sprite("home_button.png");
		//菜单按钮
		CCMenuItem item = CCMenuItemSprite.item(normalSprite, selectedSprite, this, "menuCall");
		item.setAnchorPoint(0,0);
		item.setPosition(winSize.width-70,10);
		menu.addChild(item, tagMenu_menu, tagMenu_menu);
		//白天
		normalSprite = CCSprite.sprite("item_day.png");
		item = CCMenuItemSprite.item(normalSprite, normalSprite, this, "menuCall");
		item.setAnchorPoint(0,0);
		item.setPosition(30, 150);
		menu.addChild(item, tagMenu_day, tagMenu_day);
		//黑夜
		normalSprite = CCSprite.sprite("item_night.png");
		item = CCMenuItemSprite.item(normalSprite, normalSprite, this, "menuCall");
		item.setAnchorPoint(0,0);
		item.setPosition(160, 150);
		menu.addChild(item, tagMenu_night, tagMenu_night);
		
		this.addChild(menu, tagMenu, tagMenu);
		
	}
	private int num = 0;
	public void menuCall(Object obj){
		//得到CCMenuItem
		CCMenuItem item = (CCMenuItem) obj;
		//通过CCMenuItem的tag处理相应事件
		switch (item.getTag()) {
		case tagMenu_menu:
			if(num%2==0){
				HelpLayer help = new HelpLayer();
				this.addChild(help, tagHelp, tagHelp);
			}else{
				this.removeChildByTag(tagHelp, true);
			}
			num++;
			
			break;
		case tagMenu_day:
			GameData.GameType = GameData.Day;
			vh.changeScene(new SelectScene());
			break;
		case tagMenu_night:
			GameData.GameType = GameData.Night;
			vh.changeScene(new SelectScene());
			break;
		default:
			break;
		}
		
		
	}
	private void init() {
		
		//背景
		this.addChild(vh.getSprite("pvz_select_survive_mode.png", 
				ccp(0,0), 0.66f), tagBg, tagBg);
		//底部背景
		this.addChild(vh.getSprite("bar.9.png", ccp(0,0)),tagbottom, tagbottom);
		//头像
		this.addChild(vh.getSprite("wy_portrait_female_1.jpg", ccp(10,10)),tagIcon, tagIcon);
		//金钱
		this.addChild(vh.getText("金钱:"+GameData.GameMoney, 
				ccp(100, 10), 20, ccc3(80, 120, 160)), tagMoney, tagMoney);
		//等级
		this.addChild(vh.getText("等级:"+GameData.GameLevel, 
				ccp(200, 10), 20, ccc3(80, 120, 160)), tagLevel, tagLevel);
		
		
	}
	
	private final int tagBg = 1;//背景
	private final int tagbottom = 4;
	private final int tagIcon = 5;
	private final int tagMoney = 10;
	private final int tagLevel = 15;
	private final int tagHelp = 19;
	private final int tagMenu = 20;
	private final int tagMenu_menu = 25;
	private final int tagMenu_day = 30;
	private final int tagMenu_night = 35;

}
