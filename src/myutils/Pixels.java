package myutils;

public class Pixels {
	protected int[] pixels;
	public float scale, xOffset, yOffset;
	private final float DEFAULTZOOMRATE = 1.05f;
	public CanvasShell canvasShell;

	Pixels(int[] p, CanvasShell canvasShell) {
		this.pixels = p;
		this.canvasShell = canvasShell;
		resetOffsetScale();
	}

	public void add(Vector2D l, int i) {
		int x = (int) Math.round((l.x + -xOffset) * scale + canvasShell.cx);
		int y = (int) Math.round((l.y - yOffset) * scale + canvasShell.cy);
		if (inside(x, y, 0, 0, canvasShell.WIDTH - 1, canvasShell.HEIGHT - 1))
			pixels[x + y * canvasShell.WIDTH] = i;
	}

	public void clear(int c) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = c;
	}

	public static boolean inside(int x, int y, int xMin, int yMin, int xMax, int yMax) {
		return (x >= xMin) && (x <= xMax) && (y >= yMin) && (y <= yMax);
	}

	public void scrollX(int numTimesPressed) {
		xOffset += numTimesPressed / scale * Math.PI;
	}

	public void scrollY(int numTimesPressed) {
		yOffset += numTimesPressed / scale * Math.PI;
	}

	public void zoom(int r) {
		scale *= Math.pow(DEFAULTZOOMRATE, r);
	}

	protected void resetOffsetScale() {
		xOffset = 0;
		yOffset = 0;
		scale = 1;
	}

	public void setOffset(Vector2D locationRelative) {
		xOffset = locationRelative.x;// * Math.PI;// scale;//Math.PI;
		yOffset = locationRelative.y;// * Math.PI;// scale;//Math.PI;
	}

	public void convertOnScreen(Vector2D v, int x, int y) {
		v.x = x / scale - canvasShell.cx;
		v.y = y / scale - canvasShell.cy;
	}

	public void convertRelative(Vector2D v, int x, int y) {
		v.x = (x - canvasShell.cx) / scale + xOffset;
		v.y = (y - canvasShell.cy) / scale + yOffset;
	}

}
