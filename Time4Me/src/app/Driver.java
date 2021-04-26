package app;

public class Driver {

	private static App app;
	
	public static void main(String args[]) {
		Settings.tWakeUp = "8:00";
		Settings.tSleep = "22:00";
		app = new App();
	}
	
	public static App getApp() {
		return app;
	}
}
