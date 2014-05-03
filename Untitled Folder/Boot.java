public class Boot {
	private int lengte;//lengte boot
	private int rdir,kdir,rbegin,kbegin;//richtingsincrementor rijen, richt.incr.kolommen,beginrij,beginkolom
	private boolean gezonken;
	
	Boot(int l)
	{
		lengte = l;
		gezonken = false;
	}
	

	public void rdir(int r)
	{
		rdir = r;
	}
	
	public void kdir(int k)
	{
		kdir = k;
	}
	
	public void rbegin(int r)
	{
		rbegin = r;
	}
	
	public void kbegin(int k)
	{
		kbegin=k;
	}
	
	public int geefLengte()
	{
		return lengte;
	}
	
	public int geefRdir()
	{
		return rdir;
	}
	
	public int geefKdir()
	{
		return kdir;
	}
	
	public int geefRbegin()
	{
		return rbegin;
	}
	
	public int geefKbegin()
	{
		return kbegin;
	}
	
	public void stelGezonken()
	{
		gezonken = true;
	}
	
	public boolean vraagGezonken(Bord ditbord)
	{
		gezonken = true;
		int i=0;
		while (i < lengte && gezonken) {
			if (!ditbord.veldIsGeraakt(rbegin+i*rdir, kbegin+i*kdir)) {
				gezonken = false;
			}
			i++;
		}
		return gezonken;
	}
}
