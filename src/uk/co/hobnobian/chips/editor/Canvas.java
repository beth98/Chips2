package uk.co.hobnobian.chips.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import uk.co.hobnobian.chips.editor.options.OptionsDisplayer;
import uk.co.hobnobian.chips.gui.ImageCache;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener{
	private static final long serialVersionUID = 3226831824185259183L;
	private Editor editor;
	private OptionsDisplayer options;
	
	private static final double trackSensitivity = 4;
	
	private int lastX = 0;
	private int lastY = 0;
	
	
	private ImageCache imageCache;
	
	private double[] offsetcentre = {0,0};
	private int size = 64;
	
	public Canvas(Editor e) {
		editor = e;
		options = new OptionsDisplayer();
		imageCache = editor.getImageCache();
		
		super.addMouseListener(this);
		super.addMouseMotionListener(this);
		super.addMouseWheelListener(this);
		
		
		super.setPreferredSize(new Dimension(448,448));
		super.setMinimumSize(new Dimension(448,448));
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
	    
	    int[] windowRadius = new int[] {getSize().width/2, getSize().height/2};
		int[] topleftblockcoord = new int[] {((int)offsetcentre[0])-(windowRadius[0]/size),((int)offsetcentre[1])-(windowRadius[1]/size)};
		double[] offset = new double[] {(offsetcentre[0]*size)%size, (offsetcentre[1]*size)%size};
		
		int py = 0;
		int by = topleftblockcoord[1];
		
		int px = 0;
		int bx = topleftblockcoord[0];
		while (py < getSize().height) {
		    while (px < getSize().width) {
		        int finalx = (int) (px-offset[0]);
		        int finaly = (int) (py-offset[1]);
		        
		        Image i = imageCache.loadImage(editor.getMap().getAt(bx, by).getImage(editor.getVars()), size);
		        setSquare(finalx, finaly, i, g2d);
		        
		        bx++;
		        px+=size;
		    }
		    py+=size;
		    by++;
		    px = 0;
		    bx = topleftblockcoord[0];
		}
		
		if (editor.getSelected() != null) {
		    g2d.setColor(Color.RED);
		    for (int[] pos : editor.getSelected().getAreaSelection()) {
		        g2d.drawRect(toXY(pos)[0], toXY(pos)[1], size, size);
		    }
		    
		    g2d.setColor(Color.YELLOW);
            for (int[] pos : editor.getSelected().getDragSelection()) {
                g2d.drawRect(toXY(pos)[0], toXY(pos)[1], size, size);
            }
		}
		
		options.display(g2d, getSize().width, getSize().height);
	}
	
	private void setSquare(int x, int y, Image image, Graphics2D g) {
		g.drawImage(image, x, y, this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    if (e.getButton() == 1) {
	        editor.currentlySelected = new Selection();
	        editor.getSelected().mouseMoved(getXY(e)[0], getXY(e)[1]);
	        repaint();
	    }
		
	}

	private int[] getXY(MouseEvent e) {
		int relativex = e.getX()/size;
		int relativey = e.getY()/size;
		
		relativex-=(getSize().width/2)/size;
		relativey-=(getSize().height/2)/size;
		
		relativex+=offsetcentre[0];
		relativey+=offsetcentre[1];
		return new int[]{relativex,relativey};
	}
	
	private int[] toXY(int x1, int y1) {
	    double x = x1;
	    double y = y1;
	    
	    x-=offsetcentre[0];
        y-=offsetcentre[1];
	    x+=(getSize().width/2)/(double)size;
        y+=(getSize().height/2)/(double)size;
	    return new int[] {(int) (x*size)-(size/2), (int) (y*size)-(size/2)};
	}
	
	private int[] toXY(int[] pos) {
	    return toXY(pos[0], pos[1]);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int[] actualpos = getXY(e);
		if (e.getClickCount() == 1) {
			if (e.getButton() == 1) {
				editor.setBlock(actualpos[0], actualpos[1]);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getButton() == 3 || (e.getButton() == 1 && e.isControlDown())) {
			
			moveCamera(e);
		}
		else if (e.getButton() == 1) {
		    if (editor.getSelected() == null) {
		        editor.currentlySelected = new Selection();
		    }
		    editor.getSelected().mouseMoved(getXY(e)[0], getXY(e)[1]);
		    repaint();
		    
		}
	}
	
	private int xChange = 0;
	private int yChange = 0;
	private void moveCamera(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		xChange += x-lastX;
		yChange += y-lastY;

		double c = 16.0;
		double actualXChange = (xChange/(double)size)/c;
		double actualYChange = (yChange/(double)size)/c;
		
		if (Math.abs(actualXChange) > 0.125) {
			offsetcentre[0]-=actualXChange;
			xChange = 0;
		}
		if (Math.abs(actualYChange) > 0.125) {
			offsetcentre[1]-=actualYChange;
			yChange = 0;
		}

		
		checkCentre();
		
		
		
		repaint();
	}
	
	private void checkCentre() {
		if (offsetcentre[0] < 0) {
			offsetcentre[0] = 0;
			xChange = 0;
		}
		else if (offsetcentre[0] > 255) {
			offsetcentre[0] = 255;
			xChange = 0;
		}
		if (offsetcentre[1] < 0) {
			offsetcentre[1] = 0;
			yChange = 0;
		}
		else if (offsetcentre[1] > 255) {
			offsetcentre[1] = 255;
			yChange = 0;
		}
	}
	
	private void checkZoom() {
	    if (size < 4) {
	        size = 4;
	    }
	    else if (size > 64) {
	        size = 64;
	    }
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == 37) {
			offsetcentre[0]-=0.125;
		}
		else if (code == 39) {
			offsetcentre[0]+=0.125;
		}
		if (code == 38){
			offsetcentre[1]-=0.125;
		}
		else if (code == 40) {
			offsetcentre[1]+=0.125;
		}
		
		if (e.getKeyChar() == '[') {
		    size/=2;
		    checkZoom();
		}
		else if (e.getKeyChar() == ']') {
		    size*=2;
            checkZoom();
		}
		checkCentre();
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.isControlDown()) {
            int r = e.getWheelRotation();
            if (r < 0) {
                size*=2;
            }
            else if (r > 0){
                size/=2;
            }
            checkZoom();
        }
        
        if (e.isShiftDown()) {
            offsetcentre[0]+=(e.getWheelRotation()/(double)size)*trackSensitivity;
        }
        else {
            offsetcentre[1]+=(e.getWheelRotation()/(double)size)*trackSensitivity;
        }
        checkCentre();
        
        repaint();
        
    }
}
