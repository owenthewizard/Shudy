package worlds;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Image;

import actors.Label;
import actors.ProjLaser;
import actors.ShudyActor;
import actors.Star;
import engine.Utils;
import mayflower.Keyboard;
import mayflower.Mayflower;
import mayflower.World;

import org.ini4j.Wini;

public abstract class ShudyWorld extends World
{
	private static final Wini ini = Utils.getConfig("settings.ini");

	public static final int WIDTH = Integer.parseInt( ini.get("Display", "width") );
	public static final int HEIGHT = Integer.parseInt( ini.get("Display", "height") );
	public static final int RATE = Integer.parseInt( ini.get("Display", "rate") );

	public static final boolean VSYNC = Boolean.parseBoolean( ini.get("Display", "vsync") );
	public static final boolean FULLSCREEN = Boolean.parseBoolean( ini.get("Display", "fullscreen") );
	public static final boolean FPS = Boolean.parseBoolean( ini.get("Display", "showFPS") );
	public static final boolean WIDESCREEN = Boolean.parseBoolean( ini.get("Display", "widescreen") );
	public static final boolean SOUND = Boolean.parseBoolean( ini.get("Sound", "enabled") );
	
	public static final Image BACKGROUND = (WIDESCREEN)?
			Utils.getScaledImage("assets/img/worlds/starfield_169.png", WIDTH, HEIGHT) :
			Utils.getScaledImage("assets/img/worlds/starfield_43.png", WIDTH, HEIGHT);

	public ShudyWorld()
	{ this(true); }

	public ShudyWorld(boolean drawStars)
	{
		setBackground(BACKGROUND);

		setPaintOrder(Star.class, ProjLaser.class, ShudyActor.class, Label.class);
		
		if (drawStars && Utils.starActors != null)
			for (Star s : Utils.starActors)
				addObject( s, s.getX(), s.getY() );
		else if ( drawStars )	
		{
			generateStars();
			Utils.starActors = this.getObjects(Star.class);
		}
	}

	@Override
	public void act()
	{
		Display.sync(RATE);
		
		if (Mayflower.isKeyPressed(Keyboard.KEY_END))
		{
			Mayflower.setWorld(new MainMenu());
		}
	}

	private void generateStars()
	{
		for ( int w = 0; w < WIDTH; w += 64 )
			for ( int h = 0; h < HEIGHT; h += 64 )
			{
				Star s = new Star(w + (int) ( Math.random() * 32 ) - 16, h + (int) ( Math.random() * 32 ) - 16);
				addObject( s, s.getX(), s.getY() );
			}
	}
}
