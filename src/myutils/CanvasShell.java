package myutils;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public abstract class CanvasShell extends Canvas implements Runnable {
	protected static final long serialVersionUID = 1L;

	protected boolean running = false;
	protected long tickCount = 0;
	protected int ticks = 0;
	protected int renders = 0;

	protected int WIDTH, HEIGHT, SCALE;
	protected String TITLE;
	protected double nsPerTick, nsPerRender;
	protected int background = Colors.get(0, 0, 0);

	protected JFrame frame;
	protected Graphics graphics;
	protected BufferStrategy bufferStrategy;
	protected BufferedImage image;
	protected Pixels screen;
	protected int x, y, xPos, yPos;

	protected KeyHandler keyHandler;
	protected MouseHandler mouseHandler;
	protected Vector2D mouseLocation;

	public CanvasShell(int width, int height, int scale, String title, double nsPerTick, double nsPerRender) {
		WIDTH = width / scale;
		HEIGHT = height / scale;
		SCALE = scale;
		TITLE = title;
		this.nsPerTick = nsPerTick;
		this.nsPerRender = nsPerRender;

		setMinimumSize(new Dimension(WIDTH * SCALE / 2, HEIGHT * SCALE / 2));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE * 2, HEIGHT * SCALE * 2));

		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		screen = new Pixels(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), WIDTH, HEIGHT);

		createBufferStrategy(3);
		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();

		keyHandler = new KeyHandler(this);
		mouseLocation = new Vector2D(WIDTH / 2, HEIGHT / 2);
		mouseHandler = new MouseHandler(this);
	}

	@Override
	public void run() {
		init();
		long last = System.nanoTime();
		long current;
		long debugTimer = System.currentTimeMillis();
		double deltaTick = 0;
		double deltaRender = 0;
		boolean shouldRender = false;
		while (running) {
			current = System.nanoTime();
			deltaTick += (current - last) / nsPerTick;
			deltaRender += (current - last) / nsPerRender;
			last = current;
			if (deltaTick > 1) {
				tick();
				ticks++;
				deltaTick -= 1;
				shouldRender = true;
			}
			if ((deltaRender > 1) && (shouldRender)) {
				render();
				renders++;
				deltaRender -= 1;
				shouldRender = false;
			}
			if (System.currentTimeMillis() - debugTimer >= 1000) {
				debugInfo();
				debugTimer += 1000;
			}
		}
		System.exit(0);
	}

	protected void tick() {
		tickCount++;
		defaultKeyHandling();
		defaultMouseHandling();
		myTick();
	}

	private void defaultKeyHandling() {
		if (keyHandler.esc.pressed) {
			stop();
		}
		if (keyHandler.up.pressed) {
			screen.scrollUp();
		}
		if (keyHandler.down.pressed) {
			screen.scrollUpDown();
		}
		if (keyHandler.left.pressed) {
			screen.scrollUpLeft();
		}
		if (keyHandler.right.pressed) {
			screen.scrollUpRight();
		}
		if (keyHandler.pageup.pressed) {
			screen.zoom(1);
		}
		if (keyHandler.pagedown.pressed) {
			screen.zoom(-1);
		}
		if (keyHandler.equal.pressed) {
			screen.reset();
		}
	}

	private void defaultMouseHandling() {
		if (mouseHandler.right.clicked) {
			screen.setOffset(mouseHandler.right.x, mouseHandler.right.y);
			mouseHandler.right.clicked = false;
		}
		if (mouseHandler.amountScrolled != 0) {
			screen.zoom(mouseHandler.amountScrolled);
			mouseHandler.amountScrolled = 0;
		}
	}

	protected void render() {
		myRender();

		graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		bufferStrategy.show();
	}

	protected void debugInfo() {
		System.out.println(ticks + " TPS, " + renders + "FPS");
		myDebugInfo();
		ticks = renders = 0;
	}

	protected abstract void init();

	protected abstract void myTick();

	protected abstract void myRender();

	protected abstract void myDebugInfo();

	public synchronized void start() {
		System.out.println("CanvasShell start");
		running = true;
		new Thread(this, TITLE + "-Thread").start();
	}

	public void stop() {
		System.out.println("CanvasShell stop");
		running = false;
	}

}
