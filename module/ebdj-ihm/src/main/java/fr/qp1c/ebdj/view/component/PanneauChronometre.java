package fr.qp1c.ebdj.view.component;

import fr.qp1c.ebdj.moteur.utils.StringUtilities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class PanneauChronometre extends Label {

	private int duree;

	private Timeline chronometre;

	public PanneauChronometre() {
		initialiser();
		start();
	}

	public void start() {
		duree = 0;
		chronometre.play();
	}

	public void stop() {
		chronometre.stop();
	}

	public void restart() {
		stop();
		start();
	}

	private void initialiser() {
		chronometre = new Timeline(new KeyFrame(Duration.seconds(0), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				setText(formaterDuree());
				duree += 1;
			}
		}), new KeyFrame(Duration.seconds(1)));
		chronometre.setCycleCount(Animation.INDEFINITE);
	}

	private String formaterDuree() {

		int hour = duree / 3600;
		int minute = (duree / 60) - hour;
		int second = duree % 60;

		String hourString = StringUtilities.pad(2, ' ', hour);
		String minuteString = StringUtilities.pad(2, '0', minute);
		String secondString = StringUtilities.pad(2, '0', second);

		StringBuilder sb = new StringBuilder();
		sb.append(hourString);
		sb.append(':');
		sb.append(minuteString);
		sb.append(':');
		sb.append(secondString);

		return sb.toString();
	}
}
