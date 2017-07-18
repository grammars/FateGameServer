package game.scene;

import java.nio.ByteBuffer;

public class MapData
{
	/** 地图id */
	public int mapId;
	/** x轴格子数 */
	public int gridCountX;
	/** y轴格子数 */
	public int gridCountY;
	
	private Grid[] grids;
	
	public MapData()
	{
		//
	}
	
	private void install()
	{
		int total = gridCountX * gridCountY;
		grids = new Grid[total];
		for(int i = 0; i < gridCountY; i++)
		{
			for(int j = 0; j < gridCountX; j++)
			{
				Grid g = new Grid();
				g.x = j;
				g.y = i;
				grids[i * gridCountX + j] = g;
			}
		}
	}
	
	public Grid getGrid(int x, int y)
	{
		if(x < 0 || x >= gridCountX) { return null; }
		if(y < 0 || y >= gridCountY) { return null; }
		return grids[y*gridCountX + x];
	}
	
	/** 是否可移动 */
	public boolean canMove(int x, int y)
	{
		Grid g = getGrid(x, y);
		if(g != null)
		{
			return !g.isBlock;
		}
		else
		{
			return false;
		}
	}
	
	public void decode(byte[] bytes)
	{
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		mapId = buffer.getInt();
		gridCountX = buffer.getInt();
		gridCountY = buffer.getInt();
		//System.err.println("解码：mapId=" + mapId + " gridCountX=" + gridCountX + " gridCountY=" + gridCountY);
		
		install();
		
		for(int i = 0; i < grids.length; i++)
		{
			grids[i].decode(buffer);
		}
	}
	
	public String toString()
	{
		return "[MapData] mapId=" + mapId + " gridCountX=" + gridCountX + " gridCountY=" + gridCountY;
	}
	
	public static class Grid
	{
		/** 每个格子的像素宽 */
		public static final int PIXEL_W = 40;
		/** 每个格子的像素高 */
		public static final int PIXEL_H = 30;
		
		/** F = G + H */
		public int F = 0;
		/** 从起点A，沿着产生的路径，移动到网格上指定方格的移动耗费 */
		public int G = 0;
		/** 从网格上那个方格移动到终点B的预估移动耗费 */
		public int H = 0;
		/** 在AStarEngine中被用来记录反向父节点 */
		public Grid parent;
		
		public int x;
		public int y;
		
		/** 是否是阻碍点 [在AStarNode中] */
		public boolean isBlock = false;
		/** 是否是遮罩点 */
		public boolean isMask = false;
		/** 是否是摆摊点 */
		public boolean isStall = false;
		/** 是否是传送点 */
		public boolean isDeliver = false;
		/** 传送点数值 */
		public int deliverVal = 0;
		/** 是否是数字标识位 */
		public boolean isNumTag = false;
		/** 数字标识位的数值 */
		public int numTagVal = 0;
		
		public void decode(ByteBuffer buffer)
		{
			byte bt = buffer.get();
			if((bt & 1) != 0) { isBlock = true; } else { isBlock = false; }
			if((bt & 2) != 0) { isMask = true; } else { isMask = false; }
			if((bt & 4) != 0) { isStall = true; } else { isStall = false; }
			if((bt & 8) != 0) { isDeliver = true; } else { isDeliver = false; }
			if((bt & 16) != 0) { isNumTag = true; } else { isNumTag = false; }
			deliverVal = buffer.getShort();
			numTagVal = buffer.getShort();
		}
		
//		public boolean equals(Grid other)
//		{
//			if(other == null) { return false; }
//			return (this.x == other.x && this.y == other.y);
//		}
		
		public String toString()
		{
			return "("+x+","+y+")";
		}
	}
	
}
