package myutils;

public class Mouse {
	public int x, y;
	public Vector2D locationOnScreen = new Vector2D();
	public Vector2D locationRelative = new Vector2D();
	public boolean clicked = false;
	public int numTimesClicked = 0;

	public void toggle(int xPos, int yPos, boolean isClicked, Pixels screen) {
		x = xPos;
		y = yPos;
		screen.convertOnScreen(locationOnScreen, x, y);
		screen.convertRelative(locationRelative, x, y);
		if (clicked = isClicked)
			numTimesClicked++;
	}

	public void reset() {
		this.numTimesClicked = 0;
	}
}
