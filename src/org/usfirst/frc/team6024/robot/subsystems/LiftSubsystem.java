package org.usfirst.frc.team6024.robot.subsystems;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.RobotMap;
import org.usfirst.frc.team6024.robot.Sensors;
import org.usfirst.frc.team6024.robot.commands.DeafaultLiftCommand;
import org.usfirst.frc.team6024.robot.commands.DefaultDriveCommand;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LiftSubsystem extends Subsystem{
	VictorSP left, right;
	public static final double pulsesPerInchFast = 11.00;
	public static final double pulsesPerInchSlow = 29.9;
	public LiftSubsystem() {
		left = new VictorSP(RobotMap.liftLeftMotor);
		right = new VictorSP(RobotMap.liftRightMotor);
		left.setInverted(true);
		leftCount = Sensors.leftLE.get();
		rightCount = Sensors.rightLE.get();
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DeafaultLiftCommand());
	}
	
	public void move(double speed) {
		left.set(speed);
		right.set(speed);
	}
	
	public void  PIDmove(double speed) {
		double ratio = Sensors.leftLE.getRate()/Sensors.rightLE.getRate();
		if(ratio == Double.NaN) ratio = 1;
		ratio = Math.max(Math.min(1.5, ratio), 0.1);
		left.set(speed*ratio);
		right.set(speed/ratio);
	}
	
	public int leftCount, rightCount;
	public void brake() {
		if(Sensors.rightLE.get() > 50) {
			left.set((Sensors.rightLE.get() - rightCount)/60.00);
			right.set((Sensors.rightLE.get() - rightCount)/60.00);
		}else {
			left.set(0); right.set(0);
		}
	}
	
	
	public void teleopMove() {
		double speed = OI.liftStick.getY();
		boolean trigger = OI.liftStick.getTrigger();
		boolean slowLift = OI.liftStick.getRawButton(2);
		if(trigger) brake();
		else if(speed > 0.3 || speed < -0.3) {
			leftCount = Sensors.leftLE.get();
			rightCount = Sensors.rightLE.get();
			if(speed < 0) { // go up
				move((slowLift?0.6:1)*speed);
			}else { // go down
				move((slowLift?0.3:0.8)*speed);
			}
			//move((speed>0?0.6*speed:speed)*(slowLift?0.4:1));
		}
		else brake();
	}
}
