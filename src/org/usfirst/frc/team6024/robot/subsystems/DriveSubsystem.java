/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6024.robot.subsystems;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.RobotMap;
import org.usfirst.frc.team6024.robot.Sensors;
import org.usfirst.frc.team6024.robot.commands.DefaultDriveCommand;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	
	VictorSP fl, fr, bl, br;
	
	public DriveSubsystem() {
		fl = new VictorSP(RobotMap.LFmotor);
		fr = new VictorSP(RobotMap.RFmotor);
		bl = new VictorSP(RobotMap.LBmotor);
		br = new VictorSP(RobotMap.RBmotor);
		fl.setInverted(true);
		bl.setInverted(true);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new DefaultDriveCommand());
	}
	
	public void move(double left, double right) {
		
		fl.set(left);
		fr.set(right);
		bl.set(left);
		br.set(right);
	}
	
	public void driveTeleop() {
		double yaxis = OI.driveStick.getY();
		double zaxis = OI.driveStick.getZ();
		boolean boost = OI.driveStick.getRawButton(2);
		boolean trigger = OI.driveStick.getRawButton(1);
		
		if(trigger) {
			move(0, 0);
		}else if(Math.abs(zaxis) > 0.3 || Math.abs(yaxis) > 0.25) {
			if(Math.abs(zaxis) > Math.abs(yaxis)) 
				move(-(zaxis - Math.signum(zaxis)*0.175)*0.55, (zaxis-Math.signum(zaxis)*0.175)*0.55);
			else {
				if(boost) move(yaxis*0.8, yaxis*0.8);
				else move(yaxis*0.6, yaxis*0.6);
			}
		}else {
			move(0, 0);
		}
	}
	
	
	
	double angleOffset = 0;
	double errorSum = 0;
	int timeCount = 0;
	
	
	
	public void drivePIDEncoders() {
		//put failsafe here
		double yaxis = OI.driveStick.getY();
		double zaxis = OI.driveStick.getZ();
		double xaxis = OI.driveStick.getX();
		boolean boost = OI.driveStick.getRawButton(2);
		boolean trigger = OI.driveStick.getRawButton(1);
		boolean leftPivot = OI.driveStick.getRawButton(3);
		boolean rightPivot = OI.driveStick.getRawButton(4);
		double boostMult = trigger?0.25:(boost?0.8:0.55);
		if(leftPivot){
			if(zaxis > 0.2) move(-zaxis*boostMult*0.8, zaxis*boostMult*0.2);
			else if(zaxis < -0.2) move(-zaxis*boostMult*0.2, zaxis*boostMult*0.8);
			else move(0, 0);
			timeCount = 0;
		}else if(rightPivot){
			if(zaxis > 0.2) move(-zaxis*boostMult*0.2, zaxis*boostMult*0.8);
			else if(zaxis < -0.2) move(-zaxis*boostMult*0.8, zaxis*boostMult*0.2);
			else move(0, 0);
			timeCount = 0;
		}else if(Math.abs(zaxis) > 0.3 || Math.abs(yaxis) > 0.25) {
			if(Math.abs(zaxis) > Math.abs(yaxis)) {
				move(-(zaxis - Math.signum(zaxis)*0.175)*boostMult*0.8, (zaxis-Math.signum(zaxis)*0.175)*boostMult*0.8);
				timeCount = 0;
			}else if(xaxis > 0.5 || xaxis < -0.5) {
				double steeringMult = Math.pow(1 + Math.abs(xaxis)*1.0 - 0.5, Math.signum(xaxis));
				double leftSpeed = yaxis*boostMult*steeringMult;
				double rightSpeed = yaxis*boostMult/steeringMult;
				move(leftSpeed, rightSpeed);
				timeCount = 0;
			}else {
				double leftSpeed = yaxis*boostMult;
				double rightSpeed = yaxis*boostMult;
				if(timeCount > 4) {
					double ratio = (Sensors.rightE.getRate() + 0.00000000000001)/Sensors.leftE.getRate(); 
					leftSpeed *= ratio;
					rightSpeed /= ratio;
				}
				move(leftSpeed, rightSpeed);
				timeCount++;
			}
		}else {
			move(0, 0);
			timeCount = 0;
		}
	}
	
	
	public void drivePID() {
		if(Sensors.navx.isConnected() == false) {
			//driveTeleop();
			//return;
			errorSum = 0;
			
		}
		double yaxis = OI.driveStick.getY();
		double zaxis = OI.driveStick.getZ();
		double xaxis = OI.driveStick.getX();
		boolean boost = OI.driveStick.getRawButton(1);
		boolean trigger = OI.driveStick.getRawButton(2);
		boolean leftPivot = OI.driveStick.getRawButton(3);
		boolean rightPivot = OI.driveStick.getRawButton(4);
		double boostMult = trigger?0.3:(boost?0.85:0.7);
		double curAngle = Sensors.navx.getFusedHeading();
		if(leftPivot){
			if(zaxis > 0.2) move(-zaxis*boostMult*0.8, zaxis*boostMult*0.2);
			else if(zaxis < -0.2) move(-zaxis*boostMult*0.2, zaxis*boostMult*0.8);
			else move(0, 0);
			angleOffset = Sensors.navx.getFusedHeading();
			errorSum = 0;
			timeCount = 0;
		}else if(rightPivot){
			if(zaxis > 0.2) move(-zaxis*boostMult*0.2, zaxis*boostMult*0.8);
			else if(zaxis < -0.2) move(-zaxis*boostMult*0.8, zaxis*boostMult*0.2);
			else move(0, 0);
			angleOffset = Sensors.navx.getFusedHeading();
			errorSum = 0;
			timeCount = 0;
		}else if(Math.abs(zaxis) > 0.3 || Math.abs(yaxis) > 0.25) {
			if(Math.abs(zaxis) > Math.abs(yaxis)) {
				move(-(zaxis - Math.signum(zaxis)*0.175)*boostMult*0.8, (zaxis-Math.signum(zaxis)*0.175)*boostMult*0.8);
				angleOffset = curAngle;
				errorSum = 0;
				timeCount = 0;
			}else if(xaxis > 0.5 || xaxis < -0.5) {
				double steeringMult = Math.pow(1 + Math.abs(xaxis)*1.0 - 0.5, Math.signum(xaxis));
				double leftSpeed = yaxis*boostMult*steeringMult;//*Math.min(1, timeCount/30.000);
				double rightSpeed = yaxis*boostMult/steeringMult;//*Math.min(1, timeCount/30.000);
				move(leftSpeed, rightSpeed);
				angleOffset = curAngle;
				timeCount = 0;
				errorSum =0;
			}else {
				if(timeCount <= 10) angleOffset = curAngle;
				double angDiff = Sensors.findHeading(angleOffset, curAngle);
				errorSum += angDiff*0.05;
				double pidMult = Math.pow(angDiff/35.00 + errorSum/35.00 + 1, Math.signum(-yaxis));
				double leftSpeed = yaxis*boostMult*pidMult;//*Math.min(1, timeCount/30.000);
				double rightSpeed = yaxis*boostMult/pidMult;//*Math.min(1, timeCount/30.00);
				move(leftSpeed, rightSpeed);
				timeCount++;
			}
		}else {
			move(0, 0);
			angleOffset = curAngle;
			errorSum = 0;
			timeCount = 0;
		}
		SmartDashboard.putNumber("angleOffset", angleOffset);
		SmartDashboard.putNumber("angdiff", Sensors.findHeading(angleOffset, curAngle));
	}


	public double movePID(double speed, double initialAngle, double curError) {
		speed *= -1;
		double curAngle = Sensors.navx.getFusedHeading();
		double angDiff = Sensors.findHeading(initialAngle, curAngle);
		double pidMult = Math.pow((angDiff/35.00) + (curError/40.00) + 1, Math.signum(-speed));
		double leftSpeed = speed*pidMult;
		double rightSpeed = speed/pidMult;
		move(leftSpeed, rightSpeed);
		return curError + angDiff*0.03;
	}
}