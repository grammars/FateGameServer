package game.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import game.scene.MapData.Grid;

public class AStarEngine
{
	private static AStarEngine instance;
	public static AStarEngine getInstance()
	{
		if(instance == null) { instance = new AStarEngine(); }
		return instance;
	}
	
	/** 对角移动付出的代价 */
	private static final int COST_DIAGONAL = 14;
	/** 直线移动付出的代价 */
	private static final int COST_STRAIGHT = 10;
	
	/** 最大路径限制 */
	private static final int PATH_MAX = 500;
	
	/** 关闭列表（不再处理的节点）*/
	private ArrayList<Grid> _closeList = new ArrayList<>();
	
	/** 当前节点 */
	private Grid _curNode;
	
	/** 开始节点 */
	private Grid _startNode;
	/** 结束节点 */
	private Grid _endNode;
	
	/** 地图数据 */
	private MapData _data;
	
	/** 开放列表（等待处理的节点）*/
	private ArrayList<Grid> _openList = new ArrayList<>();
	
	/** 父节点路径 */
	private ArrayList<Grid> _parentNodes = new ArrayList<>();
	
	/** 设置地图配置 */
	public void setMapData(MapData value)
	{
		this._data = value;
	}
	/** 获取地图配置 */
	public MapData getMapData() { return _data; }
	
	/** 开始寻路(没有包含起始Node[startX,startY]) */
	public ArrayList<Grid> findPath(int startX, int startY, int endX, int endY)
	{
		ArrayList<Grid> ARR_NULL = new ArrayList<>();
		_openList.clear();
		_closeList.clear();
		_startNode = null;
		_endNode = null;
		_curNode = null;
		_startNode = getNonBlockNode(startX, startY); //.getNonBlockNode(startX, startY);
		_endNode = getNonBlockNode(endX, endY);
		if (_startNode == null || _endNode == null || _startNode.isBlock || _endNode.isBlock)
		{
			return ARR_NULL;
		}
		//用于计算寻路的节点的尝试次数
		int times = 0;
		_parentNodes.clear();;
		_startNode.parent = null;
		_openList.add(_startNode);
		while (true)
		{
			if (_openList.size() < 1 || times >= PATH_MAX)
			{
				return ARR_NULL;
			}
			_curNode = _openList.remove(0);//shift();
			//System.out.println("引擎==>_openList.shift() " + _curNode);
			if ( EQL(_curNode,_endNode) )
			{
				//System.out.println("!EQL("+_curNode.parent+","+_startNode.parent+")");
				while ( !EQL(_curNode.parent,_startNode.parent) )//
				{
					_parentNodes.add(_curNode);
					_curNode = _curNode.parent;
				}
				Collections.reverse(_parentNodes);//_parentNodes.reverse()
				return _parentNodes;
			}
			_closeList.add(_curNode);
			detectionAround();
			times++;
		}
	}
	
	private Grid getNonBlockNode(int ix, int iy)
	{
		Grid grid = _data.getGrid(ix, iy);
		if(grid != null && grid.isBlock) { return null; }
		return grid;
	}
	
	private static boolean EQL(Grid a, Grid b)
	{
		if(a == null && b == null) { return true; }
		else if(a == null || b == null) { return false; }
		return a == b;
	}
	
	
	/** 检测四周的节点 */
	private void detectionAround()
	{
		int iX =_curNode.x;
		int iY =_curNode.y;
		addOpenList(getNonBlockNode(iX - 1, iY), COST_STRAIGHT); //左
		addOpenList(getNonBlockNode(iX + 1, iY), COST_STRAIGHT); //右			
		addOpenList(getNonBlockNode(iX, iY - 1), COST_STRAIGHT); //上
		addOpenList(getNonBlockNode(iX, iY + 1), COST_STRAIGHT); //下
		addOpenList(getNonBlockNode(iX + 1, iY - 1), COST_DIAGONAL);
		addOpenList(getNonBlockNode(iX + 1, iY + 1), COST_DIAGONAL);
		addOpenList(getNonBlockNode(iX - 1, iY - 1), COST_DIAGONAL);
		addOpenList(getNonBlockNode(iX - 1, iY + 1), COST_DIAGONAL);
		
		Comparator<Grid> c = new Comparator<Grid>()
				{
					@Override
					public int compare(Grid o1, Grid o2)
					{
						if(o1.F > o2.F) { return 1; }
						else if(o1.F < o2.F) { return -1; }
						return 0;
					}
				};
				
		Collections.sort(_openList, c);
	}
	
	/** 路径评估(距离优先) */
	private void getFGH(Grid node, int cost)
	{
		int valG = _curNode.G + cost;
		node.G = valG < node.G ? valG + cost : node.G;
		node.H = ( Math.abs(node.x - _endNode.x) + Math.abs(node.y - _endNode.y) ) * 10;
		node.F = node.G + node.H;
	}
	
	
	/** 添加一个节点到开放列表 */
	private boolean addOpenList(Grid node, int cost)
	{
		//System.out.println("引擎==>addOpenList " + node);
		//可走且不在关闭列表里
		if (node!=null && !node.isBlock && _closeList.indexOf(node) < 0)
		{
			//不在开放列表中
			if (_openList.indexOf(node) < 0)
			{
				node.F=0;
				node.G=0;
				node.H=0;
				node.parent = _curNode;
				getFGH(node, cost);
				_openList.add(node);
				//System.out.println("引擎==>addOpenList::不在开放列表中node="+ node+ " _curNode="+ _curNode);
				return true;
			}
		}
		return false;
	}
	
	
}
