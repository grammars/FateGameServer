package game.scene;

public class AStarNode
{
	/** F = G + H */
	public int F = 0;
	/** 从起点A，沿着产生的路径，移动到网格上指定方格的移动耗费 */
	public int G = 0;
	/** 从网格上那个方格移动到终点B的预估移动耗费 */
	public int H = 0;
	
	/** 父节点 */
	public AStarNode parentNode;
	
	/** 是否是阻碍点 */
	public boolean isBlock = false;
	
	/** 序列x */
	public int ind_X;
	/** 序列y */
	public int ind_Y;
	
	public AStarNode()
	{
		this.ind_X = 0;
		this.ind_Y = 0;
	}
	
	public AStarNode(int ix, int iy)
	{
		this.ind_X = ix;
		this.ind_Y = iy;
	}
	
	
	public String toString()
	{
		return "(" + ind_X + "," + ind_Y + ")";
	}
	
	public boolean equals(AStarNode node)
	{
		if(node == null) { return false; }
		if(this.ind_X == node.ind_X && this.ind_Y == node.ind_Y)
		{
			return true;
		}
		return false;
	}
}
