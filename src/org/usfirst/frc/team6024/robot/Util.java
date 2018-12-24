package org.usfirst.frc.team6024.robot;

public class Util {
	public double sigmoid(double z) {
		return (1.00/(1.00+Math.pow(Math.E, -z)));
	}
}