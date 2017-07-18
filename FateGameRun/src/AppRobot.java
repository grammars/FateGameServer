import robot.RobotClient;
import framework.Log;
import framework.SetupCfg;


public class AppRobot
{

	public static void main(String[] args)
	{
		Log.init();
		SetupCfg.load();
		
		for(int i = 0; i < 2000; i++)
		{
			System.err.println("i="+i);
			Thread t = new Thread()
			{
				public void run()
				{
					RobotClient rc = new RobotClient();
					rc.start();
				};
			};
			t.start();
		}
	}

}
