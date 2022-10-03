package color;

import java.nio.ByteBuffer;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.SGL;

public class PaletteTexture{
	
	private TextureImpl internalTexture; 
	private Color[] currentPalette;
	
	
	public PaletteTexture(TextureImpl texture, Color[] defaultPalette) {
		internalTexture = texture;
		currentPalette = defaultPalette;
		
		byte[] data = internalTexture.getTextureData();
		ByteBuffer db = ByteBuffer.allocateDirect(data.length);
		for(int p = 0; p < data.length/4; p++) {
			for(int v = 0; v < 4; v++) {
				db.put(p*4+v, nearest(data[p*4+v]));
			}
		}
		int srcPixelFormat = SGL.GL_RGBA;
		int componentCount = 4;
		int minFilter = SGL.GL_NEAREST;
		int magFilter = SGL.GL_NEAREST;
		InternalTextureLoader.get().reload(internalTexture, srcPixelFormat, componentCount, minFilter, magFilter, db);
	}
	
	public void changePalette(Color[] palette) {
		byte[] data = internalTexture.getTextureData();
		byte[] color = new byte[4];
		ByteBuffer db = ByteBuffer.allocateDirect(data.length);
		for(int p = 0; p < data.length/4; p++) {
			for(int v = 0; v < 4; v++) {
				color[v] = data[4*p+v];
			}
			boolean getNoChange = true;
			for(int i = 0; i < currentPalette.length; i++) {
				if(nearest(floatByte(currentPalette[i].r)) == color[0] &&
						nearest(floatByte(currentPalette[i].g)) == color[1] &&
						nearest(floatByte(currentPalette[i].b)) == color[2]) {
					db.put(p*4, floatByte(palette[i].r));
					db.put(p*4+1, floatByte(palette[i].g));
					db.put(p*4+2, floatByte(palette[i].b));
					db.put(p*4+3, floatByte(palette[i].a));
					getNoChange = false;
					break;
				}
			} if(getNoChange) {
				db.put(p*4, data[p*4]);
				db.put(p*4+1, data[p*4+1]);
				db.put(p*4+2, data[p*4+2]);
				db.put(p*4+3, data[p*4+3]);
			}
		}
		int srcPixelFormat = SGL.GL_RGBA;
		int componentCount = 4;
		int minFilter = SGL.GL_NEAREST;
		int magFilter = SGL.GL_NEAREST;
		InternalTextureLoader.get().reload(internalTexture, srcPixelFormat, componentCount, minFilter, magFilter, db);
		
	}
	private static byte nearest(byte b) {
		int val = unsignedByte(b);
		if(val > 252) {
			return (byte) 255;
		}
		if(val%8 >= 4) {
			return (byte) (((val>>3)+1)<<3);
		} else {
			return (byte) ((val>>3)<<3);
		}
	}
	private static int unsignedByte(byte b) {
		if(b < 0) {
			return 256 + b;
		} else {
			return b;
		}
	}
	private static byte floatByte(float f) {
		return (byte)Math.min((256*f),255);
	}
	
}
