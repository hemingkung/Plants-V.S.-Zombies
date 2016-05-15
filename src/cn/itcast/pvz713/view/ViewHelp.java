package cn.itcast.pvz713.view;

import java.util.ArrayList;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import cn.itcast.pvz713.R;
import cn.itcast.pvz713.biz.GameData;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

public class ViewHelp {
	private static ViewHelp vh;
	private ViewHelp(){
		
	}
	public static ViewHelp instance(){
		if(vh==null){
			vh = new ViewHelp();
		}
		return vh;
	}
	
	/**
	 *  切换场景
	 */
	public void changeScene(CCLayer layer){
		CCScene scene = CCScene.node();
		scene.addChild(layer);
		CCDirector.sharedDirector().replaceScene(scene);
	}
	
	/**
	 * 返回一个精灵
	 * @param name
	 * @param point
	 * @return
	 */
	public CCSprite getSprite(String name, CGPoint point){
		return getSprite(name, point, 1);
	}
	public CCSprite getSprite(String name, CGPoint point, float f){
		CCSprite sprite = CCSprite.sprite(name);
		sprite.setScale(f);
		sprite.setAnchorPoint(0,0);
		sprite.setPosition(point);
		return sprite;
	}
	
	
	public CCLabel getText(String name, CGPoint point, float size){
		return getText(name, point, size, ccColor3B.ccBLUE);
	}
	public CCLabel getText(String name, CGPoint point, float size, ccColor3B color3){
		CCLabel text = CCLabel.labelWithString(name, "hkbd.ttf", size);
		text.setColor(color3);
		text.setAnchorPoint(0,0);
		text.setPosition(point);
		return text;
	}
	
	/**
	 * 初始化一个对话框
	 * @param f
	 * @returnf
	 */
	public static Dialog getDialog(){
		Dialog d = new Dialog(CCDirector.theApp,R.style.init_game);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = d.getWindow();
		WindowManager.LayoutParams lp=window.getAttributes();
		lp.dimAmount=0f;//(0-1)
		window.setAttributes(lp);
		window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		return d;
	}
	public ArrayList<CCSpriteFrame> getSpriteArray(int type) {
		ArrayList<CCSpriteFrame> array = new ArrayList<CCSpriteFrame>();
		switch (type) {
		case GameData.Sun:
			for(int i=1; i<=8; i++){
				CCSprite sp = CCSprite.sprite(String.format("p_1_0%01d.png", i));
				array.add(sp.displayedFrame());
			}
			break;
		case GameData.Plant:
			for(int i=1; i<=8; i++){
				CCSprite sp = CCSprite.sprite(String.format("p_2_0%01d.png", i));
				array.add(sp.displayedFrame());
			}
			break;
		case GameData.Npc:
			for(int i=1; i<=7; i++){
				CCSprite sp = CCSprite.sprite(String.format("z_1_%02d.png", i));
				array.add(sp.displayedFrame());
			}
			break;
		}
		return array;
	}
	/**
	 * 返回两个数之间的一个数
	 * @param i
	 * @param j
	 * @return
	 */
	public int random(int i, int j) {
		return (int) (Math.random()*(j-i+1) + i);
	}

	
}
