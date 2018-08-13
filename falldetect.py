import bluepy
from bluepy import sensortag
import math
import time
import math
import os

#Config the address of SensorTag
sensortagAddress="A0:E6:F8:AF:76:01"


def main():
	print("Connecting to the SensorTag")
	#Connect to Sensortag
	tag=sensortag.SensorTag(sensortagAddress);
	print("Connected!")

	#Enableing Sensors
	tag.accelerometer.enable()
	tag.IRtemperature.enable()

	time.sleep(1.0)

	while True:
		accData=tag.accelerometer.read()
		print("Accelerometer: ",accData)
		tempData=tag.IRtemperature.read()
		print("Temp: ",tempData)
		
		x=accData[0];
		y=accData[1];
		z=accData[2];

		#write temp data
		os.system('curl -X POST \
				-H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"    \
				-H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"    \
				-H \"Content-Type: application/json\" \
				-d \'{\"type\": \"temp\",\"content\":'+temp+',\"sensor\":{\"__type\":\"Pointer\",\"className\":\"Sensor\",\"objectId\":\"Yxw1888C\" } }\' \
				https://api.bmob.cn/1/classes/SensorDataHistory')

		if math.sqrt(x*x+y*y+z*z)>2 :
			print("FALL!!!")
			#push notification
			os.system('curl -X POST \
				-H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"          \
				-H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"        \
				-H \"Content-Type: application/json\" \
				-d \'{\"data\": {\"alert\": "Warning!! Fall detected!!!.\"}}\' \
				https://api.bmob.cn/1/push')
			#write sensor history
			os.system('curl -X POST \
				-H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"    \
				-H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"    \
				-H \"Content-Type: application/json\" \
				-d \'{\"type\": \"motion sensor\",\"content\":\"fallen over\",\"sensor\":{\"__type\":\"Pointer\",\"className\":\"Sensor\",\"objectId\":\"a6a263407f\" } }\' \
				https://api.bmob.cn/1/classes/SensorDataHistory')
		time.sleep(1.0)

	db.close()
	tag.disconnect()
	
if __name__ == "__main__":
	main()