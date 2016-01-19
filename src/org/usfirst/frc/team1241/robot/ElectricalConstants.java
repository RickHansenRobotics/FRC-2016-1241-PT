package org.usfirst.frc.team1241.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class ElectricalConstants {
	//**************************************************************************
	//**************************** DRIVE MOTORS*********************************
	//**************************************************************************        

//	public static final int RIGHT_DRIVE_FRONT                               = 8; 
//	public static final int RIGHT_DRIVE_BACK                                = 9;
//
//	public static final int LEFT_DRIVE_FRONT                                = 0;
//	public static final int LEFT_DRIVE_BACK                                 = 1;

	//**************************************************************************
	//*************************** SHOOTER MOTORS *******************************
	//**************************************************************************        

	public static final int INTAKE											= 1;
	public static final int ARM												= 2;
	public static final int CONVEYOR										= 3;
	public static final int SHOOTER											= 4;
	
	public static final int SHOOTERPISTON_A									= 0;
	public static final int SHOOTERPISTON_B									= 7;
	
	public static final int OUTTAKEPISTON_A									= 1;
	public static final int OUTTAKEPISTON_B									= 6;
	
	public static final int HOOD_A											= 2;
	public static final int HOOD_B											= 5;
	
	//**************************************************************************
	//**************************** INTAKE MOTORS *******************************
	//**************************************************************************        

//	public static final int RIGHT_INTAKE_MOTOR 								= 7;
//	public static final int LEFT_INTAKE_MOTOR 						        = 2;

	//**************************************************************************
	//************************** DRIVE ENCODERS ********************************
	//**************************************************************************

	//**************************************************************************
	//************************* TURRET ENCODERS ******************************
	//**************************************************************************


	//**************************************************************************
	//*************************** Digital Sensors ******************************
	//**************************************************************************


	//***************************************************************************
	//*************************** Pneumatics ************************************
	//***************************************************************************

	//**************************************************************************
	//********************* DRIVE ENCODER CONSTANTS ****************************
	//**************************************************************************
	public static final int driveWheelRadius = 4;//wheel radius in inches
	public static final int drivePulsePerRotation = 128; //encoder pulse per rotation
	public static final double driveGearRatio = 1/1; //ratio between wheel and encoder
	public static final double driveEncoderPulsePerRot = drivePulsePerRotation*driveGearRatio; //pulse per rotation * gear ratio
	public static final double driveEncoderDistPerTick =(Math.PI*2*driveWheelRadius)/driveEncoderPulsePerRot;
	public static final boolean rightDriveTrainEncoderReverse = false; 
	public static final boolean leftDriveTrainEncoderReverse = false; 

	//**************************************************************************
	//******************** Shooter ENCODER CONSTANTS **************************
	//**************************************************************************
	public static final int elevPulleyRadius = 2;
	public static final int elevPulsePerRotation = 128; //encoder pulse per rotation
	public static final double elevGearRatio = 1/1; //ratio between pulley and encoder
	public static final double elevEncoderPulsePerRot = elevPulsePerRotation*elevGearRatio; //pulse per rotation * gear ratio
	public static final double elevEncoderDistPerTick =(Math.PI*2*elevPulleyRadius)/elevEncoderPulsePerRot;
	public static final boolean rightElevEncoderReverse = false; 
	public static final boolean leftElevEncoderReverse = false;
}
