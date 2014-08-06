package myutils;

public class Mouse {
	public int x, y;
	public boolean clicked = false;
	public int numTimesPressed = 0;

	public void toggle(int xPos,int yPos,boolean isClicked) {
		x=xPos;
		y=yPos;
		if (clicked = isClicked)
			numTimesPressed++;
	}
}
