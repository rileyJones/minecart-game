package color;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;


public class PaletteTest extends BasicGame {
	int i;
	Image a;
	PaletteTexture b;
	Color[] c;
	public PaletteTest(String title) {
		super(title);
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new PaletteTest("Test"));
		app.setDisplayMode(896, 672, false);
		app.setVSync(true);
		app.setShowFPS(false);
		app.start();
		
		
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		b.changePalette(c);
		a.draw(0,0,8);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		a = new Image("resources/Arrow.png");
		a.setFilter(Image.FILTER_NEAREST);
		b = new PaletteTexture((TextureImpl) a.getTexture(), new Color[] {new Color(0x000000), new Color(0xff0000), new Color(0x00ff00), new Color(0x0000ff)});
		c = new Color[4];
		c[0] = ColorSelector.get().getColor(CNAME.GREY, CTYPE.DEEP, true, true);
		c[1] = ColorSelector.get().getColor(CNAME.GREEN, CTYPE.MAIN, true, true);
		c[2] = ColorSelector.get().getColor(CNAME.GREEN, CTYPE.LIGHT, false, true);
		c[3] = ColorSelector.get().getColor(CNAME.TEAL, CTYPE.MAIN, true, false);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
	}
}
