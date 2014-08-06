package myutils;

public class Colors {

	public static int get(double d) {
		return (int) Math.round(d*255)&255;
	}

	public static int get(double r, double g, double b) {
		//System.out.println("r:"+r+", g:"+g+", b:"+b+", res:"+(int)(get(r) << 16 | get(g) << 8 | get(b)));
		return get(r) << 16 | get(g) << 8 | get(b);
	}

}
