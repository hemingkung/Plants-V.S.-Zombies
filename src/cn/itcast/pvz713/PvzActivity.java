package cn.itcast.pvz713;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import cn.itcast.pvz713.view.LoginScene;

import android.app.Activity;
import android.os.Bundle;

public class PvzActivity extends Activity {
    private CCDirector d = CCDirector.sharedDirector();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView view = new CCGLSurfaceView(this);
        setContentView(view);
        
        d.attachInView(view);
        
        d.setDisplayFPS(true);
        d.setAnimationInterval(1.0f/60);
        d.setDeviceOrientation(d.kCCDeviceOrientationPortrait);
        d.setScreenSize(480, 320);
        
        CCScene scene = CCScene.node();
        scene.addChild(new LoginScene());
        d.runWithScene(scene);
    }
	@Override
	protected void onDestroy() {
		d.end();
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		d.pause();
		super.onPause();
	}
	@Override
	protected void onResume() {
		d.resume();
		super.onResume();
	}
    
    
}