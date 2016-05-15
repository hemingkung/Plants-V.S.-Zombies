package cn.itcast.pvz713.view;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGSize;

public class ModelLayer extends CCLayer {
	protected CGSize winSize = CCDirector.sharedDirector().getWinSize();
	protected String font = "hkbd.ttf";
	protected ViewHelp vh = ViewHelp.instance();
	public ModelLayer(){
		setIsTouchEnabled(true);
	}
}
