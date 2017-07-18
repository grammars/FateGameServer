package game.core.action;

import utils.Utils;

public class Action
{
	protected int id;
	public int getId() { return id; }
	
	/** 延迟运行时间 */
	protected long delay = 0;
	/** 运行间隔时间 */
	protected long interval = 0;
	/** 运行总次数（-1表示无限次） */
	protected int maxRunTimes = -1;
	
	/** 下次待命时间 */
	private long readyTime = 0;
	/** 当前运行次数 */
	private int curRunTimes = 0;
	
	public Action()
	{
		id = Utils.createTidInt();
	}
	
	/** 设置行为
	 * @param delay 延迟作用时间ms
	 * @param interval 运行间隔时间
	 * @param maxRunTimes 运行总次数（-1表示无限次） */
	public void setup(long delay, long interval, int maxRunTimes)
	{
		this.delay = delay;
		this.interval = interval;
		this.maxRunTimes = maxRunTimes;
		
		readyTime = Utils.now() + delay;
	}
	
	/** 是否能被作用(use前判断,false就不能use) */
	public boolean isAvailable()
	{
		if(readyTime > Utils.now()) { return false; }
		return true;
	}
	
	/** 尝试运行
	 * @return 是否运行了 */
	public boolean tryRun()
	{
		if(!isAvailable())
		{
			return false;
		}
		run();
		runned();
		return true;
	}
	
	/** 处理运行方法 */
	public void run()
	{
		//TODO
	}
	
	/** 成功运行一次之后的处理 */
	protected void runned()
	{
		readyTime = Utils.now() + interval;
		curRunTimes ++;
	}
	
	/** 是否已废弃(use后判断,true就该移除) */
	public boolean isInvalid()
	{
		if(maxRunTimes >= 0 && curRunTimes >= maxRunTimes) { return true; }
		return false;
	}
	
	/** 被销毁 */
	public void destroy()
	{
		//TODO
	}
}
