
public class Veld {
	private boolean boot;//is veld een boot of niet
	private boolean geraakt;//is veld reeds geraakt of niet
	
	Veld()
	{
		boot=false;
		geraakt=false;
	}
	
	public boolean geefGeraakt()
	{
		return geraakt;
	}
	
	public boolean geefBoot()
	{
		return boot;
	}
	
	public void isBoot()
	{
		boot=true;
	}
	
	public void isGeraakt()
	{
		geraakt=true;
	}
}
