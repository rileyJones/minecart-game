package color;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class ColorDisplayTest extends BasicGame {

	ArrayList<Color> colors;
	
	public ColorDisplayTest(String title) {
		super(title);
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ColorDisplayTest("Test"));
		app.setDisplayMode(896, 672, false);
		app.setShowFPS(false);
		app.setVSync(true);
		app.start();
		
		
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		int height = (int) Math.sqrt(colors.size());
		int width = (int) Math.ceil(colors.size()*1.0/height);
		float rectWidth = container.getWidth() / width;
		float rectHeight = container.getHeight() / height;
		for(int x = 0; x < width; x++) {
			for( int y = 0; y < height; y++) {
				if(x + y*width < colors.size()) {
					g.setColor(colors.get(x+y*width));
				}
				g.fill(new Rectangle(rectWidth * x, rectHeight * y, rectWidth, rectHeight));
			}
		}
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		colors = new ArrayList<Color>();
		colors.add(ColorSelector.get().getColor(CNAME.SKY, CTYPE.FADED, false, false));
		colors.add(ColorSelector.get().getColor(CNAME.NEON, CTYPE.FADED, false, false));
		colors.add(ColorSelector.get().getColor(CNAME.ORANGE, CTYPE.FADED, false, false));
		colors.add(ColorSelector.get().getColor(CNAME.GREEN, CTYPE.FADED, false, false));
		colors.add(ColorSelector.get().getColor(CNAME.PINK, CTYPE.FADED, false, false));
		//colors.add(ColorSelector.get().getColor(CNAME.PURPLE, CTYPE.FADED, false, false));
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
	}
}
