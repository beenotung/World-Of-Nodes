package myutils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public Key esc = new Key();
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key pageup = new Key();
	public Key pagedown = new Key();
	public Key equal = new Key();

	public KeyHandler(CanvasShell canvasShell) {
		canvasShell.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		toggle(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggle(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void toggle(int keyCode, boolean isPressed) {
		switch (keyCode) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			up.toggle(isPressed);
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			down.toggle(isPressed);
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			left.toggle(isPressed);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			right.toggle(isPressed);
			break;
		case KeyEvent.VK_PAGE_UP:
			pageup.toggle(isPressed);
			break;
		case KeyEvent.VK_PAGE_DOWN:
			pagedown.toggle(isPressed);
			break;
		case KeyEvent.VK_EQUALS:
			equal.toggle(isPressed);
			break;
		case KeyEvent.VK_ESCAPE:
			esc.toggle(isPressed);
			break;
		default:
			System.out.println("new keyCode" + keyCode);
		}
	}
}
