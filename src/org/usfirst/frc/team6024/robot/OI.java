/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6024.robot;


import org.usfirst.frc.team6024.robot.commands.AbortCommand;
import org.usfirst.frc.team6024.robot.commands.*;
import org.usfirst.frc.team6024.robot.commands.GoForwardCommand;
import org.usfirst.frc.team6024.robot.commands.GoToHeadingCommand;
import org.usfirst.frc.team6024.robot.commands.MoveLiftCommand;
import org.usfirst.frc.team6024.robot.commands.MoveWheelCommand;

import Autonomous.InitialBlockDropCommand;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick driveStick = new Joystick(1);
	public static Joystick liftStick = new Joystick(2);
	//public static Joystick drive2Stick = new Joystick(3);
	public static Button MoveLift = new JoystickButton(driveStick, 9);
	public static Button WinchCommand = new JoystickButton(liftStick, 10);
//	public static Button AllignTo0 = new JoystickButton(driveStick, 7);
//	public static Button AllignTo180 = new JoystickButton(driveStick, 8);
	public static Button GetLiftDown = new JoystickButton(liftStick, 4);
	public static Button GetLiftSwitch = new JoystickButton(liftStick, 5);
	public static Button GetLiftScale = new JoystickButton(liftStick, 6);
	public OI() {
		//MoveLift.whenReleased(new MoveLiftCommand(12));
		//WinchCommand.whenReleased(new WinchCommand());
		//AllignTo0.whenReleased(new MoveLiftCommand(30));
		//AllignTo180.whenReleased(new MoveLiftCommand(85));
		GetLiftDown.whenReleased(new MoveLiftCommand(2));
		GetLiftSwitch.whenReleased(new MoveLiftCommand(50));
		GetLiftScale.whenReleased(new MoveLiftCommand(85));
	} 
}
