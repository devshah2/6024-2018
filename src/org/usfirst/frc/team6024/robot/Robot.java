/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6024.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team6024.robot.commands.GoForwardCommand;
import org.usfirst.frc.team6024.robot.commands.GoToHeadingCommand;
import org.usfirst.frc.team6024.robot.commands.MoveDriveCommand;
import org.usfirst.frc.team6024.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team6024.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team6024.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team6024.robot.subsystems.LiftSubsystem;
import org.usfirst.frc.team6024.robot.subsystems.WinchSubsystem;

import Autonomous.leftSwitch;
import Autonomous.rightSwitch;
import Autonomous.LeftScale;
import Autonomous.LeftToRightScale;
import Autonomous.MidSwitchLeft;
import Autonomous.MidSwitchRight;
import Autonomous.RaheshJugaad;
import Autonomous.RightScale;
import Autonomous.RightToLeftScale;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static OI m_oi;

	public static Command autoCommand;
	public static DriveSubsystem driveSubsystem;
	public static LiftSubsystem liftSubsystem;
	public static WinchSubsystem winchSubsystem;
	public static ArmSubsystem armSubsystem;
	public static PowerDistributionPanel pdp = new PowerDistributionPanel();
	SendableChooser<Integer> startingPositionChooser = new SendableChooser<>();
	public static SendableChooser<Integer> teleopDriveType = new SendableChooser<>();
	public void robotInit() {
		driveSubsystem = new DriveSubsystem();
		liftSubsystem = new LiftSubsystem();
		winchSubsystem = new WinchSubsystem();
		armSubsystem = new ArmSubsystem();
		Sensors.init();
		m_oi = new OI();
		CameraServer.getInstance().startAutomaticCapture();
		startingPositionChooser.addDefault("Do nothing", 1);
		startingPositionChooser.addObject("Middle Switch", 2);
		startingPositionChooser.addObject("Left Switch", 3);
		startingPositionChooser.addObject("Left Scale", 4);
		startingPositionChooser.addObject("Right Switch", 5);
		startingPositionChooser.addObject("Right Scale", 6);
		startingPositionChooser.addObject("Go Forward", 7);
		startingPositionChooser.addObject("Left Scale or Switch", 8);
		startingPositionChooser.addObject("Right Scale or Switch", 9);
		startingPositionChooser.addObject("Left Switch or Scale", 10);
		startingPositionChooser.addObject("Right Switch or Scale", 11);
		startingPositionChooser.addObject("Go Forward with time", 12);
		startingPositionChooser.addObject("Rahesh Jugaad", 13);
		startingPositionChooser.addObject("Right Both Scale", 14);
		startingPositionChooser.addObject("Left Both Scale", 15);
		startingPositionChooser.addObject("Right Switch or Both", 16);
		startingPositionChooser.addObject("Left Switch or Both", 17);
		SmartDashboard.putData("Auto Chooser", startingPositionChooser);
		
		teleopDriveType.addDefault("Normal NavX", 0);
		teleopDriveType.addObject("Encoder PID", 1);
		teleopDriveType.addObject("None", 2);
		SmartDashboard.putData("Drive Type", teleopDriveType);
	}

	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("navx angle", Sensors.navx.getFusedHeading());
		SmartDashboard.putBoolean("navx connected", Sensors.navx.isConnected());;
		Sensors.dashboardUpdate();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */

	String perm;
	public void autonomousInit() {
		// schedule the autonomous command (example)
		Sensors.init();
		liftSubsystem.leftCount = Sensors.leftLE.get();
		liftSubsystem.rightCount = Sensors.rightLE.get();
		perm = DriverStation.getInstance().getGameSpecificMessage();
		int startingPos = startingPositionChooser.getSelected();
		Command goForward = new GoForwardCommand(180, 0.3, 0.5);
		autoCommand = null;
		if(perm.length() == 0) { // do nothing
			autoCommand = null;
		}else if(startingPos == 1) { // do nothing
			autoCommand = null;
		}else if(startingPos == 2) { // do middle switch
			if(perm.charAt(0) == 'L') autoCommand = new MidSwitchLeft();
			else if(perm.charAt(0) == 'R') autoCommand = new MidSwitchRight();
		}else if(startingPos == 3) { // do left switch
			if(perm.charAt(0) == 'L') autoCommand = new leftSwitch();
			else autoCommand = goForward;
		}else if(startingPos == 4) { // do left scale
			if(perm.charAt(1) == 'L') autoCommand = new LeftScale();
			else autoCommand = goForward;
		}else if(startingPos == 5) { // right switch
			if(perm.charAt(0) == 'R') autoCommand = new rightSwitch();
			else autoCommand = goForward;
		}else if(startingPos == 6) { // right scale
			if(perm.charAt(0) == 'R') autoCommand = new RightScale();
			else autoCommand = goForward;
		}else if(startingPos == 7) { // go forward
			autoCommand = goForward;
		}else if(startingPos == 8) { // left scale or switch
			if(perm.charAt(1) == 'L') autoCommand = new LeftScale();
			else if(perm.charAt(0) == 'L') autoCommand = new leftSwitch();
			else autoCommand = goForward;
		}else if(startingPos == 9) { // right scale or switch
			if(perm.charAt(1) == 'R') autoCommand = new RightScale();
			else if(perm.charAt(0) == 'R') autoCommand = new rightSwitch();
			else autoCommand = goForward;
		}else if(startingPos == 10) { // left switch or scale
			if(perm.charAt(0) == 'L') autoCommand = new leftSwitch();
			else if(perm.charAt(1) == 'L') autoCommand = new LeftScale();
			else autoCommand = goForward;
		}else if(startingPos == 11) { // right switch or scale
			if(perm.charAt(0) == 'R') autoCommand = new rightSwitch();
			else if(perm.charAt(1) == 'R') autoCommand = new RightScale();
			else autoCommand = goForward;
		}else if(startingPos == 12) {
			autoCommand = new MoveDriveCommand(4100, 0.6);
		}else if(startingPos == 13) {
			autoCommand = new RaheshJugaad();
		}else if(startingPos == 14) {
			if(perm.charAt(1) == 'R') autoCommand = new RightScale();
			else autoCommand = new RightToLeftScale();
		}else if(startingPos == 15){
			if(perm.charAt(1) == 'L') autoCommand = new LeftScale();
			else autoCommand = new LeftToRightScale();
		}else if(startingPos == 16) {
			if(perm.charAt(0) == 'R') autoCommand = new rightSwitch();
			else if(perm.charAt(1) == 'R') autoCommand = new RightScale();
			else autoCommand = new RightToLeftScale();
		}else if(startingPos == 17) {
			if(perm.charAt(0) == 'L') autoCommand = new leftSwitch();
			else if(perm.charAt(1) == 'L') autoCommand = new LeftScale();
			else autoCommand = new LeftToRightScale();
		}
		else autoCommand = null;
		if (autoCommand != null)
			autoCommand.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		//Sensors.dashboardUpdate();
		SmartDashboard.putNumber("navx angle", Sensors.navx.getFusedHeading());
		SmartDashboard.putBoolean("navx connected", Sensors.navx.isConnected());;
	}

	@Override   
	public void teleopInit() {		
		if (autoCommand != null)
			autoCommand.cancel();
		liftSubsystem.leftCount = Sensors.leftLE.get();
		liftSubsystem.rightCount = Sensors.rightLE.get();
	}

	public void teleopPeriodic() {
		//Sensors.dashboardUpdate();
		if(m_oi.driveStick.getPOV() != -1) 
			Scheduler.getInstance().add(new GoToHeadingCommand(m_oi.driveStick.getPOV()));
		if(OI.driveStick.getRawButton(12) || OI.liftStick.getRawButton(12)) {
			liftSubsystem.leftCount = Sensors.leftLE.get();
			liftSubsystem.rightCount = Sensors.rightLE.get();
			Scheduler.getInstance().removeAll();
		}
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("navx angle", Sensors.navx.getFusedHeading());
		SmartDashboard.putBoolean("navx connected", Sensors.navx.isConnected());;
	}

	public void testPeriodic() {
	}
}
