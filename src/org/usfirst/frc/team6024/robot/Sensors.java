package org.usfirst.frc.team6024.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {
	public static AHRS navx = new AHRS(I2C.Port.kOnboard);
	public static Encoder leftE = new Encoder(4, 5, false);
	public static Encoder rightE = new Encoder(0, 1, true);
	public static Encoder leftLE = new Encoder(8, 9, false);
	public static Encoder rightLE = new Encoder(2, 3, true);
	
	public static void init() {
		navx.reset();
		leftE.reset(); rightE.reset();
		leftLE.reset(); rightLE.reset();
	}
	
	public static void dashboardUpdate() {
		SmartDashboard.putNumber("encoder left", leftE.get());
		SmartDashboard.putNumber("encoder right", rightE.get());
		SmartDashboard.putNumber("navx angle", navx.getFusedHeading());
		SmartDashboard.putNumber("left speed", leftE.getRate());
		SmartDashboard.putNumber("right speed", rightE.getRate());
		SmartDashboard.putNumber("left lift speed", leftLE.getRate());
		SmartDashboard.putNumber("right lift speed", rightLE.getRate());
		SmartDashboard.putNumber("left lift", leftLE.get());
		SmartDashboard.putNumber("right lift", rightLE.get());
		SmartDashboard.putBoolean("navx connected", navx.isConnected());
		
	}
	
	public static double findHeading(double old, double cur) {
		double heading = (old-cur+360)%360;
		heading = heading > 180 ? heading - 360 : heading;
		return heading;
	}
}
