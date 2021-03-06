package uk.co.hobnobian.chips.main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import uk.co.hobnobian.chips.game.backend.Block;
import uk.co.hobnobian.chips.game.backend.EditableMap;
import uk.co.hobnobian.chips.game.blocks.GreenBlock;
import uk.co.hobnobian.chips.game.blocks.GreenButton;
import uk.co.hobnobian.chips.game.options.StartupMenu;

public class Main {
	public static final int protocolID = 1;
	public static final int defaultPort = 1234;
	
	public static void main(String[] args) {
		Block.setup();
//		saveMap();
//		w.add(new )
		
//		saveMap();
		
//		saveMap();
//		singlePlayer();
//		if (args.length > 0) {
//			if (args[0].equalsIgnoreCase("singleplayer")) {
//				singlePlayer();
//			}
//			else if (args[0].equalsIgnoreCase("server")) {
//				int port = defaultPort;
//				if (args.length > 1) {
//					try {
//						port = Integer.parseInt(args[1]);
//					}
//					catch(NumberFormatException e){}
//				}
//				server(port);
//			}
//			else if (args[0].equalsIgnoreCase("client")) {
//				String host = "localhost";
//				int port = defaultPort;
//				
//				if (args.length > 1) {
//					String entire = args[1];
//					String[] parts = entire.split(":");
//					host = parts[0];
//					if (parts.length > 1) {
//						try {
//							port = Integer.parseInt(parts[1]);
//						}
//						catch(NumberFormatException e){}
//					}
//				}
//				
//				System.out.println("Connection to "+host+":"+port);
//				client(host, port);
//			}
//		}
//		else {
//			new StartupMenu();
//		}
		
		
		
		new StartupMenu();
//		singlePlayer();
//		server(8080);
//		if (args.length > 0) {
//			server(8080);
//		}
//		else {
//			client("localhost",8080);
//		}
		
//		
	}
	
	
//	public static void server(int port) {
//		Window w = new Window();
//		GraphicsServerLayer l = new GraphicsServerLayer();
//		Image i;
//		try {
//			i = ImageIO.read(Main.class.getResource("/uk/co/hobnobian/chips/assets/air.png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT);;
//		} catch (IOException e) {
//			return;
//		}
//		Renderer r = new Renderer(l, i, w);
//		w.add(r);
//		
//		EditableMap m = new EditableMap();
//		m.setAt(new GreenButton(), 4, 0);
//		m.setAt(new GreenButton(), 1, 0);
//		m.setAt(new GreenBlock(), 0, 1);
//		m.setAt(new GreenBlock(), 1, 1);
//		m.setAt(new GreenBlock(), 2, 0);
//		
//		GameVariables vars = new GameVariables();
//		Game g = new Game(l,m, vars);
//		
//		ServerSocket c;
//		try {
//			c = new ServerSocket(port);
//			Socket s = c.accept();
//			c.close();
//			Server server = new Server(s, m, g);
//			w.setup();
//			g.update();
//			g.start();
//			new Thread(server).start();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//	}
	
//	public static void client(String host, int port) {
//		Window w = new Window();
//		GraphicsServerLayer l = new GraphicsServerLayer();
//		Image i;
//		try {
//			i = ImageIO.read(Main.class.getResource("/uk/co/hobnobian/chips/assets/air.png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT);;
//		} catch (IOException e) {
//			return;
//		}
//		Renderer r = new Renderer(l, i,w);
//		w.add(r);
//		
//		GameVariables vars = new GameVariables();
//		Game g = new Game(l, vars);
//		Socket s;
//		try {
//			s = new Socket(host, port);
//			Client c = new Client(s, g);
//			g.setMap(c.getMap());
//			new Thread(c).start();
//			w.setup();
//			g.update();
//			g.start();
//			
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return;
//		}
//		
//	}
	
//	public static void singlePlayer() {
//		Window w = new Window();
//		GraphicsServerLayer l = new GraphicsServerLayer();
//		Image i;
//		try {
//			i = ImageIO.read(Main.class.getResource("/uk/co/hobnobian/chips/assets/air.png")).getScaledInstance(64, 64, Image.SCALE_DEFAULT);;
//		} catch (IOException e) {
//			return;
//		}
//		Renderer r = new Renderer(l, i,w);
//		w.add(r);
//		
//		EditableMap m = new EditableMap();
//		m.setAt(new GreenButton(), 4, 0);
//		m.setAt(new GreenButton(), 1, 0);
//		m.setAt(new GreenBlock(), 0, 1);
//		m.setAt(new GreenBlock(), 1, 1);
//		m.setAt(new GreenBlock(), 2, 0);
//		
//		
//		GameVariables vars = new GameVariables();
//		Game g = new Game(l,m, vars);
//		w.setup();
//		g.update();
//		g.start();
//	}
	
	private static void saveMap() {
		EditableMap m = new EditableMap();
		m.setAt(new GreenButton(), 4, 0);
		m.setAt(new GreenButton(), 1, 0);
		m.setAt(new GreenBlock(), 0, 1);
		m.setAt(new GreenBlock(), 1, 1);
		m.setAt(new GreenBlock(), 2, 0);
		
//		m.setAt(new MoveableBlock(), 5, 5);
		
		
        try {
        	FileOutputStream fileOut = new FileOutputStream("/Users/ollie/map.chips");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(m);
			objectOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
