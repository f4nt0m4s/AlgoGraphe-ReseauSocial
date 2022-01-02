package fr.algographe.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import fr.algographe.Controleur;
import fr.algographe.Sommet;

public class LabelSommet extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	private Controleur ctrl;

	private String text;
	private Color colorHover;
	private int width;
	private int height;

	public LabelSommet(String text, Controleur c, Color colorHover, int width, int height) {
		super(text, JLabel.CENTER);
		this.ctrl = c;
		this.text = text;
		this.colorHover = colorHover;
		this.width = width;
		this.height = height;
		this.setOpaque(true);
		this.setPreferredSize(new java.awt.Dimension(width, height));
		MouseHandlerLabelSommet mouseHandler = new MouseHandlerLabelSommet();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		this.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Oval
		BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs);
		//int thick =  (int) bs.getLineWidth();
		//g2d.drawOval(thick, thick, super.getWidth() - thick*2, super.getHeight() - thick*2);
		g2d.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		//super.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
		/*
		java.awt.Font labelFont = super.getFont();
		String labelText = super.getText();

		int stringWidth = super.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = super.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = super.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		super.setFont(new java.awt.Font(labelFont.getName(), java.awt.Font.PLAIN, fontSizeToUse));*/
		
		// Text
		/*java.awt.Font font = new java.awt.Font("Arial", java.awt.Font.PLAIN, 12);
		g2d.setFont(font);
		java.awt.FontMetrics fm = g2d.getFontMetrics();
		int x = ((getWidth() - fm.stringWidth(getText())) / 2);
		int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
		g2d.drawString(this.getText(), x, y);*/
		// Dipose resources graphics
	}

	private void refresh(String text) {
		this.text = text;
		this.setText(text);
	}

	private class MouseHandlerLabelSommet extends MouseAdapter {
		
		public MouseHandlerLabelSommet() {}

		@Override
		public void mouseEntered(MouseEvent e) {
			setForeground(LabelSommet.this.colorHover);
			//System.out.println("mouseEntered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setForeground(Color.BLACK);
			//System.out.println("mouseExited");
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			//System.out.println("mousePressed");
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			//System.out.println("mouseReleased");
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isRightMouseButton(e)) {
				JPopupMenu popupMenu = new JPopupMenu("Edition"); {
					JMenuItem item = new JMenuItem("Information");
					item.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Sommet s = LabelSommet.this.ctrl.getSommet(LabelSommet.this.getText());
							String msg = s.toString();
							JOptionPane.showMessageDialog(LabelSommet.this.getParent(), msg);
						}
					});
					popupMenu.add(item);
					item = new JMenuItem("Renommer");
					item.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String nom = JOptionPane.showInputDialog(LabelSommet.this.getParent(), 
							"Entrer le nouveau nom du sommet " + LabelSommet.this.getText(),
							"Nouveau nom", JOptionPane.PLAIN_MESSAGE);
							if (nom != null && nom.length() > 0) {
								// Modification
								Sommet s = LabelSommet.this.ctrl.getSommet(LabelSommet.this.text);
								if (s != null) {
									s.setNom(nom);
									LabelSommet.this.refresh(s.getNom());
								} else {
									JOptionPane.showMessageDialog(LabelSommet.this.getParent(), "Le nom du sommet " + LabelSommet.this.text + "n'a pas pu être modifié",
									"Erreur du nouveau du sommet", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					});
					popupMenu.add(item);
					item = new JMenuItem("Supprimer");
					item.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String msg = "Êtes-vous sur de vouloir supprimer le sommet " + LabelSommet.this.getText() + " ?";
							String title = "Suppression du sommet " + LabelSommet.this.getText();
							int input = JOptionPane.showConfirmDialog(LabelSommet.this.getParent(), msg, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
							if (input == JOptionPane.YES_OPTION) {
								Sommet s = LabelSommet.this.ctrl.getSommet(LabelSommet.this.getText());
								if (s != null) {
									LabelSommet.this.ctrl.supprimerSommet(s);
									LabelSommet.this.ctrl.refreshDessin();
								}
							}
						}
					});
					popupMenu.add(item);
					Container parent = LabelSommet.this.getParent();
					popupMenu.show(parent, LabelSommet.this.getX() + LabelSommet.this.getWidth(), LabelSommet.this.getY());
				}
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			setForeground(LabelSommet.this.colorHover);
			int posX = LabelSommet.this.getX();
			int posY = LabelSommet.this.getY();
			int widthLabel = LabelSommet.this.getWidth();
			int heightLabel = LabelSommet.this.getHeight();
			
			Dimension dimension = LabelSommet.this.getParent().getSize();

			int parentWidth = (int) LabelSommet.this.getParent().getSize().getWidth();
			int parentHeight = (int) LabelSommet.this.getParent().getSize().getHeight();

			int newPosX = posX + e.getX();
			int newPosY = posY + e.getY();

			if (newPosX < 0) {
				newPosX = 0;
			} else if (newPosX >= parentWidth) {
				newPosX = (int) parentWidth;
			}

			if (newPosY < 0) {
				newPosY = 0;
			} else if (newPosY >= parentHeight) {
				newPosY = (int) parentHeight;
			}

			if (newPosX > 0 && newPosX < (newPosX + widthLabel) 
				&& newPosY > 0 && (newPosY + heightLabel) < parentHeight) {
				LabelSommet.this.setBounds(newPosX, newPosY, widthLabel, heightLabel);
			}
		}
	}
}