/**
JPanel redrawing implementation adapted from: http://stackoverflow.com/questions/15377842/how-to-refresh-reload-image-inside-jpanel
 *
 */

package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class PreviewPanel extends JPanel
{

	public BufferedImage myImage = null;

	JLabel textLabel;
	JPanel text = new JPanel();
	JPanel prevImage = new JPanel();
	Thread loopThread;
	JLabel prevImageLabel = new JLabel();
	JButton boxButton;
	JButton sphereButton;
	int SLEEP_TIME = 100;
	LoopThread l;

	public PreviewPanel(BufferedImage img)
	{
		myImage = img;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		prevImage.add(prevImageLabel);
		textLabel = new JLabel("Preview Geometry: ");
		text.add(textLabel);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		boxButton = new JButton("Box");
		sphereButton = new JButton("Sphere");

		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.add(text);
		topPanel.add(boxButton);
		topPanel.add(sphereButton);

		add(topPanel);
		add(prevImage);

		// l = new LoopThread(SLEEP_TIME);
		// l.start();
	}

	public void setButtonListeners(ActionListener box, ActionListener sphere)
	{
		boxButton.addActionListener(box);
		sphereButton.addActionListener(sphere);
	}

	public void stopLoop()
	{
		l.looping = false;
	}

	public void startLoop()
	{
		l.looping = false;
	}

	public void updatePreview()
	{
		if (myImage != null)
		{

			Dimension textSize = new Dimension(myImage.getWidth(), 20);
			Dimension labelSize = new Dimension(myImage.getWidth(), myImage.getHeight());

			// text.setPreferredSize(textSize);
			text.setMaximumSize(textSize);

			prevImage.setPreferredSize(labelSize);
			ImageIcon i = new ImageIcon(myImage);
			prevImageLabel.setIcon(i);
			prevImageLabel.repaint();
		}
	}

	public void updatePreview(boolean resize)
	{
		if (myImage != null)
		{

			if (resize)
			{
				Dimension textSize = new Dimension(myImage.getWidth(), 20);
				Dimension labelSize = new Dimension(myImage.getWidth(), myImage.getHeight());

				// text.setPreferredSize(textSize);
				text.setMaximumSize(textSize);

				prevImage.setPreferredSize(labelSize);
			}
			ImageIcon i = new ImageIcon(myImage);
			prevImageLabel.setIcon(i);
			prevImageLabel.repaint();
		}
	}

	public void setImage(BufferedImage img)
	{
		myImage = img;
	}

	private class LoopThread extends Thread
	{
		int LOOP_TIME;
		boolean looping = true;

		public LoopThread(int time)
		{
			LOOP_TIME = time;
		}

		public void run()
		{
			while (looping)
			{
				updatePreview();
				try
				{
					Thread.sleep(SLEEP_TIME);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
