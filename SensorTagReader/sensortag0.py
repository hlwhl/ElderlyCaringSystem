import bluepy
from bluepy import sensortag
import math
import time
import MySQLdb

#Config the address of SensorTag
sensortagAddress="A0:E6:F8:AF:76:01"


def main():
	print("Connecting to the SensorTag")
	#Connect to Sensortag
	tag=sensortag.SensorTag(sensortagAddress);
	print("Connected!")
	#Connect to db
	db=MySQLdb.Connect(host='systemdb.c6fod1umq6rt.eu-west-2.rds.amazonaws.com',port=3306,user='hlwhl',passwd='sxlfszhlw216',db='systemDB',charset='utf8')
	db.autocommit(1)
	cursor=db.cursor()
	#Enableing Sensors
	tag.accelerometer.enable()

	time.sleep(1.0)

	while True:
		accData=tag.accelerometer.read()
		print("Accelerometer: ",accData)
		x=accData[0];
		y=accData[1];
		z=accData[2];
		cursor.execute("UPDATE AccData SET x="+str(x)+" WHERE id=1")
		cursor.execute("UPDATE AccData SET y="+str(y)+" WHERE id=1")
		cursor.execute("UPDATE AccData SET z="+str(z)+" WHERE id=1")
		time.sleep(1.0)

	db.close()
	tag.disconnect()
	
if __name__ == "__main__":
	main()