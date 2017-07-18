package ds.geom;

public class Point
{
	public int x;

	public int y;

	public long value;
	
	public Point()
	{
	}

	public Point(Point point)
	{
		setLocation(point.x, point.y);
	}

	public Point(int x, int y)
	{
		setLocation(x, y);
	}

	public void setLocation(Point point)
	{
		setLocation(point.x, point.y);
	}

	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.value = (this.y << 16) | this.x;
	}

	public void setLocation(double x, double y)
	{
		int tx = (int) x;
		int ty = (int) y;
		if (((x * 10) % 10) > 5)
			tx++;
		if (((y * 10) % 10) > 5)
			ty++;
		setLocation(tx, ty);
	}

	public int distanceSq(int x, int y)
	{
		return (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y);
	}

	public int distanceSq(Point point)
	{
		return distanceSq(point.x, point.y);
	}

	public double distance(int x, int y)
	{
		int n = distanceSq(x, y);
		return Math.sqrt(n);
	}

	public double distance(Point point)
	{
		return distance(point.x, point.y);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Point)
		{
			return equals((Point)obj);
		}
		return false;
	}
	
	public boolean equals(Point pt)
	{
		if(pt == null) { return false; }
		if(this.x != pt.x) { return false; }
		if(this.y != pt.y) { return false; }
		return true;
	}
	
	public boolean equals(int px, int py)
	{
		if(this.x != px) { return false; }
		if(this.y != py) { return false; }
		return true;
	}

	public String toString()
	{
		return "Point[x=" + this.x + ",y=" + this.y + "]";
	}
	
}
