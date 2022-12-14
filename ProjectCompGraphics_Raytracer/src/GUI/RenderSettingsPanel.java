
package GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import objects.Sphere;
import raytracer.RenderSettings;
import raytracer.RenderViewer;
import raytracer.Renderer;
import scene.Scene;
import scene.SpheresInRoom;

public class RenderSettingsPanel extends JPanel {
	Scene myScene;

	JPanel mainPanel;
	//ResolutionPanel resPanel;
	ParameterPanel width;
	ParameterPanel height;
	CheckBoxPanel threadingPanel;
	CheckBoxPanel phongPanel;
	ThreeRadioPanel shadowPanel;
	ThreeRadioPanel antiAPanel;
	ParameterPanel reflectPanel;
	ParameterPanel refractPanel;
	CheckBoxPanel transPanel;
	CheckBoxPanel accelPanel;
	ParameterPanel depthPanel;
	CheckBoxPanel progressPanel;
	CheckBoxPanel verbosePanel;
	JPanel writePanel;

	public JFrame parentFrame = null;
	JPanel optimizationPanel;
	JPanel qualityPanel;
	JPanel miscPanel;
	JLabel writeLabel;

	JButton outButton;
	JButton saveChanges;

	public RenderSettingsPanel(Scene targetScene) {
		super();
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		add(mainPanel);
		myScene = targetScene;
		setupPanels();
		addPanels();
		this.setMinimumSize(new Dimension(500, 500));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setVisible(true);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new FlowLayout(FlowLayout.LEFT));
		Sphere sphere = new Sphere();
		SpherePanel m = new SpherePanel(sphere);
		RenderSettingsPanel rsp = new RenderSettingsPanel(new SpheresInRoom());

		f.add(rsp);
		f.add(m);
		f.pack();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void addPanels() {
		mainPanel.add(width);
		mainPanel.add(height);
		mainPanel.add(shadowPanel);
		mainPanel.add(antiAPanel);
		mainPanel.add(threadingPanel);
		mainPanel.add(phongPanel);

		mainPanel.add(reflectPanel);
		mainPanel.add(refractPanel);
		mainPanel.add(transPanel);
		mainPanel.add(accelPanel);
		mainPanel.add(depthPanel);
		mainPanel.add(progressPanel);
		mainPanel.add(verbosePanel);
		mainPanel.add(writePanel);
		mainPanel.add(outButton);
		mainPanel.add(saveChanges);
	}

	public void setupPanels() {
		RenderSettings mySettings = myScene.settings;
		//resPanel = new ResolutionPanel(mySettings.getWIDTH(),
		//		mySettings.getHEIGHT());
		width = new ParameterPanel("Width: ", ""+mySettings.getWIDTH(), 4);
		height = new ParameterPanel("Height: ", ""+mySettings.getHEIGHT(), 4);
		threadingPanel = new CheckBoxPanel("Multithreading: ",
				mySettings.isMULTITHREADING());
		phongPanel = new CheckBoxPanel("Phong Shading: ", mySettings.isPHONG());
		shadowPanel = new ThreeRadioPanel("Shadow Type: ",
				mySettings.getSHADOW_TYPE());
		shadowPanel.setNames("Simple: ", "Soft: ", "Pretty (recursive): ");
		antiAPanel = new ThreeRadioPanel("Antialiasing amount: ",
				mySettings.getANTIALIASING());
		antiAPanel.setNames("1x (none): ", "4x: ", " 9x: ");
		reflectPanel = new ParameterPanel("Reflections: ", ""
				+ mySettings.getREFLECTION(), 2);
		refractPanel = new ParameterPanel("Refractions: ", ""
				+ mySettings.getREFRACTION(), 2);
		transPanel = new CheckBoxPanel("Transparency: ",
				mySettings.isTRANSPARENCY());
		accelPanel = new CheckBoxPanel("Octree Acceleration: ",
				mySettings.isACCELERATE());
		progressPanel = new CheckBoxPanel("Show progress: ",
				mySettings.isPROGRESS());
		verbosePanel = new CheckBoxPanel("Verbose mode: ",
				mySettings.isVERBOSE());
		mySettings.setOUTPUT_PATH(null);
		writePanel = new JPanel();
		writeLabel = new JLabel("Output: " + mySettings.getOUTPUT_PATH());
		writePanel.add(writeLabel);
		depthPanel = new ParameterPanel("Max Octree Depth: ", ""
				+ mySettings.getOCTREE_DEPTH(), 2);

		outButton = new JButton("Specify Output File");
		saveChanges = new JButton("Save Changes");

		outButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setOutput();
				updateSettings();
				updatePanels();
			}
		});
		
		saveChanges.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateSettings();
				updatePanels();
				if (parentFrame != null){
					parentFrame.setVisible(false);
				}
			}
		});

		ActionListener act = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				{
					updateSettings();
					updatePanels();
				}
			}
		};

		addFieldListeners(act);
		//addFocusListeners(focus);

		ActionListener rend = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					updateSettings();
					new RenderViewer(Renderer.renderScene(myScene));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		};

	}

	public File getOutputFile() {

		JFileChooser choose = null;
		File f = myScene.settings.getOUTPUT_PATH();
		if (myScene.settings.getOUTPUT_PATH() != null) {
			choose = new JFileChooser(f);
		} else {
			choose = new JFileChooser("./out");
		}

		choose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"PNG images (.png)", "png");
		choose.setFileFilter(filter);

		int result = choose.showSaveDialog(this);

		if (result == choose.APPROVE_OPTION) {
			if (!choose.getSelectedFile().getAbsolutePath().endsWith(".png")) {
				return new File(choose.getSelectedFile() + ".png");
			} else {
				return choose.getSelectedFile();
			}
		} else {
			return null;
		}
	}

	public void setOutput() {
		File f = getOutputFile();
		myScene.settings.setOUTPUT_PATH(f);
		writeLabel.setText("Output: " + f);
	}

	public void updatePanels() {
		RenderSettings mySettings = myScene.settings;

		//resPanel.setValue(mySettings.getHEIGHT(), mySettings.getWIDTH());
		width.setText(""+mySettings.getWIDTH());
		height.setText(""+mySettings.getHEIGHT());
		threadingPanel.setValue(mySettings.isMULTITHREADING());
		phongPanel.setValue(mySettings.isPHONG());
		antiAPanel.setValue(mySettings.getANTIALIASING());
		shadowPanel.setValue(mySettings.getSHADOW_TYPE());
		reflectPanel.setText("" + mySettings.getREFLECTION());
		refractPanel.setText("" + mySettings.getREFRACTION());
		transPanel.setValue(mySettings.isTRANSPARENCY());
		accelPanel.setValue(mySettings.isACCELERATE());
		progressPanel.setValue(mySettings.isPROGRESS());
		verbosePanel.setValue(mySettings.isVERBOSE());
		depthPanel.setText("" + mySettings.getOCTREE_DEPTH());
		writeLabel.setText("Output: " + mySettings.getOUTPUT_PATH());

		if (parentFrame != null) {
			parentFrame.pack();
		}
	}

	public void addFieldListeners(ActionListener act) {
		//resPanel.addFieldListeners(act);
		width.addFieldListener(act);
		height.addFieldListener(act);
		reflectPanel.addFieldListener(act);
		refractPanel.addFieldListener(act);
		depthPanel.addFieldListener(act);
	}

	public void addFocusListeners(FocusListener act) {
		phongPanel.addFocusListener(act);
		threadingPanel.addFocusListener(act);
		shadowPanel.addFocusListener(act);
		antiAPanel.addFocusListener(act);
		transPanel.addFocusListener(act);
		accelPanel.addFocusListener(act);
		verbosePanel.addFocusListener(act);
		writePanel.addFocusListener(act);
	}

	public void updateSettings() {
		RenderSettings mySettings = myScene.settings;

		try {
			mySettings.setWIDTH(Integer.parseInt(width.getValue()));
			mySettings.setHEIGHT(Integer.parseInt(height.getValue()));
			mySettings.setMULTITHREADING(threadingPanel.getValue());
			mySettings.setPHONG(phongPanel.getValue());
			mySettings.setSHADOW_TYPE(shadowPanel.getValue());
			mySettings.setANTIALIASING(antiAPanel.getValue());
			mySettings.setREFLECTION(Integer.parseInt(reflectPanel.getValue()));
			mySettings.setREFRACTION(Integer.parseInt(refractPanel.getValue()));
			mySettings.setTRANSPARENCY(transPanel.getValue());
			mySettings.setACCELERATE(accelPanel.getValue());
			mySettings.setPROGRESS(progressPanel.getValue());
			mySettings.setVERBOSE(verbosePanel.getValue());

			mySettings.setOCTREE_DEPTH(Integer.parseInt(depthPanel.getValue()));
		} catch (Exception e) {
			JFrame errorFrame = new JFrame("Object update error!");
			JPanel errorPanel = new JPanel(new GridLayout(2, 0));
			JLabel errorLabel = new JLabel(
					"Unable to update object parameters -- check for weird data!");
			JLabel errorLabel2 = new JLabel(e.toString());
			errorPanel.add(errorLabel);
			errorPanel.add(errorLabel2);
			errorFrame.add(errorPanel);
			errorFrame.pack();
			errorFrame.setVisible(true);
		}
	}

}
