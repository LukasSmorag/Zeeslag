package util;
import entities.Game;

public class Launcher {
	
	public static void printHeader() {
		System.out.println("************************************");
		System.out.println("*         OPEN-ZEESLAG 2.0         *");
		System.out.println("************************************");
	}

	public static void main(String[] args) {
		printHeader();
		Game game = new Game();
		game.start();
	}
}
