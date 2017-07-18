import gate.GateSrv;


public class AppGate
{
	public static GateSrv server;

	public static void main(String[] args)
	{
		GateSrv.getInstance().start();
	}

}
