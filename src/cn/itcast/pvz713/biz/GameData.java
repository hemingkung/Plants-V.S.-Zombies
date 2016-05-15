package cn.itcast.pvz713.biz;

import java.util.ArrayList;
import java.util.HashMap;

import cn.itcast.pvz713.view.Npc;
import cn.itcast.pvz713.view.Sun;
import cn.itcast.pvz713.view.Tower;

public class GameData {
	public static int GameMoney;
	public static int GameLevel;
	
	public static final int Day = 1;
	public static final int Night = 2;
	public static int GameType = 0;
	
	public static HashMap<Integer, Integer> selectPlantMap;
	public static final int Sun = 1;//向日葵类型
	public static final int Plant = 2;//豌豆
	public static final int Npc = 3;
	public static int TowerType = 0;
	
	public static ArrayList<Sun> sunList;
	public static HashMap<Integer, Npc> npcMap;
	public static HashMap<Integer, Tower> towerMap;
	/**
	 * 游戏的初始化
	 */
	public static void init() {
		//连接网络
		
		//加载图片
		
		//初始化数据库
		
		//...
		GameMoney = 500;
		GameLevel = 1;
		selectPlantMap = new HashMap<Integer, Integer>();
		sunList = new ArrayList<Sun>();
		npcMap = new HashMap<Integer, Npc>();
		towerMap = new HashMap<Integer, Tower>();
	}

}
