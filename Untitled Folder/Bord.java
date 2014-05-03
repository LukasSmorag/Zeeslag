public class Bord {
	private Veld [][] bord=new Veld[10][10];
	
	Bord()
	{
		for(int r=0;r<10;r++){
			for(int k=0;k<10;k++){
				bord[r][k]=new Veld();
			}
		}
	}
	
	public void stelVeldGeraakt(int r,int k)
	{
		bord[r][k].isGeraakt();
	}
	
	public boolean veldIsBoot(int r,int k)
	{
		if (bord[r][k].geefBoot()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean veldIsGeraakt(int r,int k)
	{
		if (bord[r][k].geefGeraakt()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void zetOpBord(Boot dezeboot)
	{
		for(int i=0;i<dezeboot.geefLengte();i++){
			bord[dezeboot.geefRbegin()+i*dezeboot.geefRdir()][dezeboot.geefKbegin()+i*dezeboot.geefKdir()].isBoot();
		}
	}
	
	public boolean plaatsbaar(Boot dezeboot)
	{
		boolean plaatsbaar=true;
		int t=0;
		while(t<dezeboot.geefLengte() && plaatsbaar){
			if(bord[dezeboot.geefRbegin()+t*dezeboot.geefRdir()][dezeboot.geefKbegin()+t*dezeboot.geefKdir()].geefBoot()){
				plaatsbaar=false;
			}
			t++;
		}
		return plaatsbaar;
	}
	
	public void print()
	{
		System.out.println("   A B C D E F G H I J");
		for(int r=0;r<10;r++){
			if(r==10){
				System.out.print(r+"|");
			} else {
				System.out.print(r+" |");
			}
			for(int k=0;k<10;k++){
				if(k==9){
					if(bord[r][k].geefBoot()){
						System.out.print("X\n");
					} else {
						System.out.print(" \n");
					}
				} else {
					if(bord[r][k].geefBoot()){
						System.out.print("X ");
					} else {
						System.out.print("  ");
					}
				}
			}
		}
	}
	
	public void printGemaskeerd()
	{
		System.out.println("   A B C D E F G H I J");
		for(int r=0;r<10;r++){
			if(r==10){
				System.out.print(r+"|");
			} else {
				System.out.print(r+" |");
			}
			for(int k=0;k<10;k++){
				if(k==9){
					if(bord[r][k].geefGeraakt()){
						if(bord[r][k].geefBoot()){
							System.out.print("X\n");
						} else {
							System.out.print(" \n");
						}
					} else {
						System.out.print("#\n");
					}
				} else {
					if(bord[r][k].geefGeraakt()){
						if(bord[r][k].geefBoot()){
							System.out.print("X ");
						} else {
							System.out.print("  ");
						}
					} else {
						System.out.print("# ");
					}
				}

			}
		}
	}
}
