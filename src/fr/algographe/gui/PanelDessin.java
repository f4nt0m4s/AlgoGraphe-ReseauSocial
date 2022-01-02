package fr.algographe.gui;

import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.lang.Math;

import fr.algographe.Controleur;
import fr.algographe.Sommet;
import fr.algographe.Utilisateur;
import fr.algographe.Page;

public class PanelDessin extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private final Color COLOR_HOVER_UTILISATEUR = Color.RED;
	private final Color COLOR_HOVER_PAGE = Color.BLUE;

	private final int OVAL_WIDTH = 75;
	private final int OVAL_HEIGHT = 35;

	private Controleur ctrl;
	private Map<Sommet, LabelSommet> map;

	public PanelDessin(Controleur c) {
		super();
		this.ctrl = c;
		this.setLayout(null);

		final int SIZE = this.ctrl.getLstSommets().size();
		
		this.map = new HashMap<Sommet, LabelSommet>();
		int x, y = 0;
		String sToolTip = "";
		for (int i = 0; i < SIZE; i++) {
			Sommet s = this.ctrl.getLstSommets().get(i);
			Color colorHover = (s instanceof Utilisateur) ? COLOR_HOVER_UTILISATEUR : (s instanceof Page) ? COLOR_HOVER_PAGE : Color.BLACK;
			this.map.put(s, new LabelSommet(s.getNom(), c, colorHover, OVAL_WIDTH, OVAL_HEIGHT));
			// x = y = 100 * i/2;
			// this.map.get(s).setBounds(x, y, OVAL_WIDTH, OVAL_HEIGHT);
			sToolTip = "<html>" +  this.ctrl.getLstSommets().get(i).toString() + "</html>";
			this.map.get(s).setToolTipText(sToolTip);
			this.add(this.map.get(s));
		}
		
		MouseHandlerPanelDessin mouseHandler = new MouseHandlerPanelDessin();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		
		// int top, int left, int bottom, int right
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK) );
		this.setVisible(true);
	}

	public void placeSommets() {
		Dimension preferredSize = this.getParent().getPreferredSize();
		int parentWidth = (int) (preferredSize.getWidth() - OVAL_WIDTH);
		int parentHeight = (int) (preferredSize.getHeight() - OVAL_HEIGHT);
		List<Point> lstPointsUsed = new ArrayList<Point>();
		int newX = 0;
		int newY = 0;
		for(Map.Entry<Sommet, LabelSommet> entry : this.map.entrySet()) {
			Point x = this.generateNewPoints(1, parentWidth, 1, parentHeight);
			while (lstPointsUsed.contains(x)) {
				x = this.generateNewPoints(1, parentWidth, 1, parentHeight);
			}
			lstPointsUsed.add(x);
			entry.getValue().setBounds((int) (x.getX()), (int) (x.getY()), OVAL_WIDTH, OVAL_HEIGHT);
		}
	}

	private Point generateNewPoints(int minWidth, int maxWidth, int minHeight, int maxHeight) {
		int newX = (int) ((Math.random() * (maxWidth - minWidth)) + minWidth);
		int newY = (int) ((Math.random() * (maxHeight - minHeight)) + minHeight);
		return new Point(newX, newY);
	}

	// Mise à jour de la zone de dessin
	public void refreshDessin() {
		Iterator<Map.Entry<Sommet, LabelSommet>> it = this.map.entrySet().iterator();
		// Suppresion d'un ou plusieurs sommet de la zone de dessin
		while (it.hasNext()) {
			Map.Entry<Sommet, LabelSommet> entry = it.next();
			Sommet s = entry.getKey();
			LabelSommet ls = entry.getValue();
			if (!this.ctrl.getLstSommets().contains(s) || this.ctrl.getSommet(s.getNom()) != s) {
				this.remove(ls);
				it.remove();
			}
		}
		// Ajout d'un ou plusieurs sommet de la zone de dessin
		Dimension preferredSize = this.getParent().getPreferredSize();
		int parentWidth = (int) (preferredSize.getWidth() - OVAL_WIDTH);
		int parentHeight = (int) (preferredSize.getHeight() - OVAL_HEIGHT);
		for (Sommet s : this.ctrl.getLstSommets()) {
			if (!this.map.containsKey(s)) {
				Sommet nouveauSommet = s;
				Color colorHover = (nouveauSommet instanceof Utilisateur) ? COLOR_HOVER_UTILISATEUR : (nouveauSommet instanceof Page) ? COLOR_HOVER_PAGE : Color.BLACK;
				LabelSommet lblSommet = new LabelSommet(nouveauSommet.getNom(), this.ctrl, colorHover, OVAL_WIDTH, OVAL_HEIGHT);
				Point x = this.generateNewPoints(1, parentWidth, 1, parentHeight);
				lblSommet.setBounds((int) (x.getX()), (int) (x.getY()), OVAL_WIDTH, OVAL_HEIGHT);
				String sToolTip = "<html>" +  nouveauSommet.toString() + "</html>";
				lblSommet.setToolTipText(sToolTip);
				this.map.put(s, lblSommet);
				this.add(lblSommet);
			}
		}
		repaint();
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//System.out.println("[PanelDessin] paintComponent -> repaint");
		this.repaint();
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int xSrc = 0;
		int ySrc = 0;
		int xDest = 0;
		int yDest = 0;

		for (Map.Entry<Sommet, LabelSommet> mapSommet : this.map.entrySet()) {
			List<? extends Sommet> lst = null;
			Color color = null;
			Sommet sommet = mapSommet.getKey();
			if (sommet instanceof Utilisateur) {
				lst = sommet.getLstVoisins();
			} else if (sommet instanceof Page) {
				lst = ((Page) sommet).getLstAdmins();
			}
			
			if (lst != null) {
				for (Map.Entry<Sommet, LabelSommet> mapVoisin : this.map.entrySet()) {
					Sommet sVoisin = mapVoisin.getKey();
					if (lst.contains(sVoisin) )
					{
						LabelSommet lblSommet = mapSommet.getValue();
						LabelSommet lSommetVoisin = mapVoisin.getValue();

						xSrc = lblSommet.getX() + (lblSommet.getWidth()/2);
						ySrc = lblSommet.getY() + (lblSommet.getHeight()/2);
						
						xDest = lSommetVoisin.getX();
						yDest = lSommetVoisin.getY();
						
						// Vérifie si noeud source se trouve sur la partie droite du noeud de destination sur l'axe x
						if (lSommetVoisin.getX() > (lblSommet.getX() + lblSommet.getWidth())) {
							xDest = lSommetVoisin.getX();
							yDest = lSommetVoisin.getY() + (lSommetVoisin.getHeight()/2);
						} // Vérifie si noeud source se trouve sur la partie gauche du noeud de destination sur l'axe x
						else if (lSommetVoisin.getX() + lSommetVoisin.getWidth() < lblSommet.getX()) {
							xDest = lSommetVoisin.getX() + lSommetVoisin.getWidth();
							yDest = lSommetVoisin.getY() + (lSommetVoisin.getHeight()/2);
						} // Sinon le noeud source se trouve dans la partie centre 
						else {
							xDest = lSommetVoisin.getX() + (lSommetVoisin.getWidth()/2);
							if (lblSommet.getY() > lSommetVoisin.getY()) {
								yDest = lSommetVoisin.getY() + (lSommetVoisin.getHeight());
							} else {
								yDest = lSommetVoisin.getY();
							}
						}
						dessinerFleche(g2d, Color.BLACK, xSrc, ySrc, xDest, yDest, 10, 10);
					}
				}
			}
		}
	}
	
	private class MouseHandlerPanelDessin extends MouseAdapter {

		public MouseHandlerPanelDessin() {}

		@Override
		public void mouseEntered(MouseEvent e) {
			//System.println("mouseEntered");
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//System.println("mouseExited");
			repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			//System.println("mouseMoved : " + e.getX() + ":" + e.getY());
			repaint();
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			//System.println("mousePressed");
			repaint();
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			//System.println("mouseReleased");
			repaint();
		}
		
		public void mouseClicked(MouseEvent e) {
			//System.println("mouseClicked");
			repaint();
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e) {
			//System.println("mouseDragged");
			repaint();
		}
	}
	
	/**
		* source : https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java
		* Draw an arrow line between two points.
		* @param g2d the graphics component.
		* @param x1 x-position of first point.
		* @param y1 y-position of first point.
		* @param x2 x-position of second point.
		* @param y2 y-position of second point.
		* @param d  the width of the arrow.
		* @param h  the height of the arrow.
	*/
	private void dessinerFleche(Graphics2D g2d, Color color, int x1, int y1, int x2, int y2, int d, int h) 
	{
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx*dx + dy*dy);
		double xm = D - d, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;
	
		x = xm*cos - ym*sin + x1;
		ym = xm*sin + ym*cos + y1;
		xm = x;
	
		x = xn*cos - yn*sin + x1;
		yn = xn*sin + yn*cos + y1;
		xn = x;
	
		int[] xpoints = {x2, (int) xm, (int) xn};
		int[] ypoints = {y2, (int) ym, (int) yn};
	
		g2d.setColor( color );
		g2d.drawLine(x1, y1, x2, y2);
		g2d.fillPolygon(xpoints, ypoints, 3);
	}

}