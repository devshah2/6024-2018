package org.usfirst.frc.team6024.robot.subsystems;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.RobotMap;
import org.usfirst.frc.team6024.robot.commands.DeafaultLiftCommand;
import org.usfirst.frc.team6024.robot.commands.DefaultDriveCommand;
import org.usfirst.frc.team6024.robot.commands.DefaultWinchCommand;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class WinchSubsystem extends Subsystem{
	VictorSP motor;
	
	public WinchSubsystem() {
		motor = new VictorSP(RobotMap.winchMotor);
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultWinchCommand());
	}
	
	public void move(double speed) {
		motor.set(speed);
	}
	public void teleop() {
		boolean forwardDir = OI.liftStick.getRawButton(9);
		boolean backwardDir = OI.liftStick.getRawButton(11);
		double slide = -(OI.liftStick.getRawAxis(3) + 1)/2;
		if(forwardDir) {
			move(slide*0.85);
		}else if(backwardDir) {
			move(-slide*0.85);
		}else {
			move(0);
		}
	}
}
