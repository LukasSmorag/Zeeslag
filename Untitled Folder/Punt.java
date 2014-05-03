
public class Punt {
		private int r,k;
		private int kwaliteit;
		
		Punt(int rr,int kk)
		{
			r=rr;
			k=kk;
		}
		
		public void setR(int rr)
		{
			r=rr;
		}
		
		public void setK(int kk)
		{
			k=kk;
		}
		
		public int geefKwaliteit()
		{
			return kwaliteit;
		}
		
		public void stelKwaliteit(int kw)
		{
			kwaliteit = kw;
		}
		
		//geeft r als false en k als true
		public int geef(boolean p)
		{
			if(p==false){
				return r;
			} else {
				return k;
			}
		}
}
