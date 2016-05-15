package cn.itcast.pvz713.view;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

public class ModelSprite extends CCSprite {
	protected CGSize winSize = CCDirector.sharedDirector().getWinSize();
	protected String font = "hkbd.ttf";
	protected ViewHelp vh = ViewHelp.instance();
	
	public ModelSprite(String name){
		super(CCSprite.sprite(name).displayedFrame());
	}
}
