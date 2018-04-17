package evoquatic;

public class Console {
	private static long startTime;
	private static boolean init = false;
	private static boolean debug = false;
	
	public static void init(boolean debug) {
		if(!init) {
			init = true;
			startTime = System.nanoTime();
			Console.debug = debug;
			if(debug) {
				log("Console initialized in debug mode.");
				debug("Debug messages look like this.");
			}
			else log("Console initialized.");
		}
	}
	
	public static void log(String... s) {
		if(init) {
			long currentTime = System.nanoTime();
			long relTime = currentTime - startTime;
			long secondsTotal = relTime/1000000000;
			String minutes = Long.toString(secondsTotal/60);
			String seconds = Long.toString(secondsTotal%60);
			
			if(seconds.length() == 1) seconds = "0"+seconds;
			
			for(int i = 0; i < s.length; i++) {
				System.out.println("["+minutes+":"+seconds+"] "+s[i]);
				EPanel.log("["+minutes+":"+seconds+"] "+s[i]);
			}
		}
	}
	
	public static void debug(String... s) {
		if(debug && init) {
			long currentTime = System.nanoTime();
			long relTime = currentTime - startTime;
			long secondsTotal = relTime/1000000000;
			String minutes = Long.toString(secondsTotal/60);
			String seconds = Long.toString(secondsTotal%60);
			
			if(seconds.length() == 1) seconds = "0"+seconds;
			
			for(int i = 0; i < s.length; i++) {
				System.out.println("["+minutes+":"+seconds+" DEBUG] "+s[i]);
				EPanel.log("["+minutes+":"+seconds+" DEBUG] "+s[i]);
			}
		}
	}
}
