package color;

import java.util.HashMap;

import org.newdawn.slick.Color;

public class ColorSelector {
	
	private HashMap<String, Color> colorMap;
	private static final Color DEFAULT_COLOR = new Color(0x808080);
	private static ColorSelector self = null;
	
	private ColorSelector() {
		colorMap = new HashMap<String, Color>();
	}
		
	public static ColorSelector get() {
		if(self == null) {
			self = new ColorSelector();
		}
		return self;
	}
	
	public Color getColor(CNAME color, CTYPE brightness, boolean halfValue, boolean extraDark) {
		String colorString = colorString(color, brightness, halfValue, extraDark);
		if(!colorMap.containsKey(colorString)) {
			colorMap.put(colorString, createColor(color, brightness, halfValue, extraDark));
		}
		return colorMap.get(colorString);
	}
	
	
	private String colorString(CNAME color, CTYPE brightness, boolean halfValue, boolean extraDark) {
		StringBuilder sb = new StringBuilder();
		sb.append(color.name()).append(brightness.name()).append(halfValue).append(extraDark);
		return sb.toString();
	}
	
	private Color createColor(CNAME color, CTYPE brightness, boolean halfValue, boolean extraDark) {
		final int HIGH_PRIMARY   = 0xf0;
		final int LOW_PRIMARY    = 0xb0;
		final int HIGH_SECONDARY = 0x80;
		final int LOW_SECONDARY  = 0x30;
		
		
		switch(color) {
			default:
			case GREY:
				{
					int color1;
					switch(brightness) {
						case FADED:
						case BRIGHT:
							color1 = HIGH_PRIMARY;
							break;
						case LIGHT:
							color1 = LOW_PRIMARY;
							break;
						default:
						case MAIN:
							color1 = HIGH_SECONDARY;
							break;
						case DARK:
						case DEEP:
							color1 = LOW_SECONDARY;
							break;
					}
					if(halfValue) {
						color1>>=1;
					}

					if(extraDark) {
						color1 -= 0x18;
					}
					return new Color(color1, color1, color1);
				}
			case RED:
			case GREEN:
			case BLUE:
			case YELLOW:
			case MAGENTA:
			case CYAN:
				{
					int color1;
					int color2;
					switch(brightness) {
						case BRIGHT:
							color1 = HIGH_PRIMARY;
							color2 = LOW_PRIMARY;
							break;
						case LIGHT:
							color1 = HIGH_PRIMARY;
							color2 = HIGH_SECONDARY;
							break;
						case FADED:
							color1 = LOW_PRIMARY;
							color2 = HIGH_SECONDARY;
							break;
						default:
						case MAIN:
							color1 = HIGH_PRIMARY;
							color2 = LOW_SECONDARY;
							break;
						case DARK:
							color1 = LOW_PRIMARY;
							color2 = LOW_SECONDARY;
							break;
						case DEEP:
							color1 = HIGH_SECONDARY;
							color2 = LOW_SECONDARY;
							break;
					}
					if(halfValue) {
						color1>>=1;
						color2>>=1;
					}
					if(extraDark) {
						color1 -= 0x18;
						color2 -= 0x18;
					}
					switch(color) {
						case RED:
							return new Color(color1, color2, color2);	
						case GREEN:
							return new Color(color2, color1, color2);
						case BLUE:
							return new Color(color2, color2, color1);
						case YELLOW:
							return new Color(color1, color1, color2);
						case MAGENTA:
							return new Color(color1, color2, color1);
						case CYAN:
							return new Color(color2, color1, color1);
						default:
							return DEFAULT_COLOR;
					}
				}
			case ORANGE:
			case NEON:
			case PINK:
			case PURPLE:
			case TEAL:
			case SKY:
				{
					int color1;
					int color2;
					int color3;
					switch(brightness) {
						case FADED:
						case BRIGHT:
							color1 = HIGH_PRIMARY;
							color2 = LOW_PRIMARY;
							color3 = HIGH_SECONDARY;
							break;
						case LIGHT:
							color1 = HIGH_PRIMARY;
							color2 = LOW_PRIMARY;
							color3 = LOW_SECONDARY;
							break;
						default:
						case MAIN:
							color1 = HIGH_PRIMARY;
							color2 = HIGH_SECONDARY;
							color3 = LOW_SECONDARY;
							break;
						case DARK:
						case DEEP:
							color1 = LOW_PRIMARY;
							color2 = HIGH_SECONDARY;
							color3 = LOW_SECONDARY;
							break;
					}
					if(halfValue) {
						color1>>=1;
						color2>>=1;
						color3>>=1;
					}

					if(extraDark) {
						color1 -= 0x18;
						color2 -= 0x18;
						color3 -= 0x18;
					}
					switch(color) {
						case ORANGE:
							return new Color(color1, color2, color3);
						case NEON:
							return new Color(color2, color1, color3);
						case PINK:
							return new Color(color1, color3, color2);
						case PURPLE:
							return new Color(color2, color3, color1);
						case TEAL:
							return new Color(color3, color1, color2);
						case SKY:
							return new Color(color3, color2, color1);
						default:
							return DEFAULT_COLOR;
					}
				}	
		}
		
	}
	
}
