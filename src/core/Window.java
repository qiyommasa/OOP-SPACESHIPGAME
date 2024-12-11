package core;

import javax.swing.JFrame;

public class Window extends JFrame{
	public static double winWidth = 500;
	public static double winHeight = 600;
	
	private static String winName;
	
	public Window(String winName, double winWidth, double winHeight) {
		super(winName);
		
		Window.winName = winName;
		Window.winWidth = winWidth;
		Window.winHeight = winHeight;
		
		setWindowAttributes();
	}
	

	public void packWindow() {
		pack();
		setResizable(false);
	}
	
	private void setWindowAttributes() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static double getWinWidth() {
		return winWidth;
	}
	
	public static double getWinHeight() {
		return winHeight;
	}
	
	public static String getWinName() {
		return winName;
	}
}
