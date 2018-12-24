package org.usfirst.frc.team6024.robot.subsystems;

import org.usfirst.frc.team6024.robot.OI;
import org.usfirst.frc.team6024.robot.RobotMap;
import org.usfirst.frc.team6024.robot.Sensors;
import org.usfirst.frc.team6024.robot.commands.DeafaultLiftCommand;
import org.usfirst.frc.team6024.robot.commands.DefaultArmCommand;
import org.usfirst.frc.team6024.robot.commands.DefaultDriveCommand;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ArmSubsystem extends Subsystem{
	VictorSP moveArm, wheelLeft, wheelRight;
	
	public ArmSubsystem() {
		moveArm = new VictorSP(RobotMap.armMoveMotor);
		wheelLeft = new VictorSP(RobotMap.armWheelLeftMotor);
		wheelRight = new VictorSP(RobotMap.armWheelRightMotor);
		wheelLeft.setInverted(false);
		wheelRight.setInverted(true);;
	}
	
	protected void initDefaultCommand() {
		setDefaultCommand(new DefaultArmCommand());
	}
	
	public void setWheelSpeed(double speed) {
		wheelLeft.set(speed);
		wheelRight.set(speed);
	}
	
	public void setArmSpeed(double speed) {
		moveArm.set(speed);
	}
	
	public void setWheelSpeed(double left, double right) {
		wheelLeft.set(left);
		wheelRight.set(right);
	}
	
	public void teleop() {
		boolean wheelButtonThrow =  false;//OI.liftStick.getRawButton(7);
		boolean wheelButtonIntake = false;//OI.liftStick.getRawButton(8);
		double angle = OI.liftStick.getPOV();
		
		if(angle != -1) {
			if(angle <= 45 || angle >= 315) setWheelSpeed(-0.8, -0.8);
			else if(angle == 180) setWheelSpeed(0.8, 0.8);
			else if(angle == 90 || angle == 135) setWheelSpeed(-0.6, 0.6);
			else if(angle == 225 || angle == 270) setWheelSpeed(0.6, -0.6);
		}else if(wheelButtonThrow) {
			setWheelSpeed(-0.8);
		}else if(wheelButtonIntake) {
			setWheelSpeed(0.8);
		}else setWheelSpeed(0);
		
		
		double speedArm =  OI.liftStick.getRawAxis(2);
		boolean slowArm = OI.liftStick.getRawButton(1);
		boolean armIn = OI.liftStick.getRawButton(8);
		boolean armOut = OI.liftStick.getRawButton(7);
		if(armIn) {
			setArmSpeed(0.7);
		}else if(armOut) {
			setArmSpeed(-0.5);
		}else if(speedArm > 0.3 || speedArm < -0.3) {
			setArmSpeed(-speedArm*(slowArm?0.3:0.7));
		}else {
			setArmSpeed(0);
		}
	}
}
