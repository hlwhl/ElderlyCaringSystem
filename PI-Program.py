#Import the bluepy library
import bluepy
from bluepy import sensortag
import math
import time
import math
import os
import struct

#Config the address of SensorTag
sensortagAddress="A0:E6:F8:AF:76:01"

class MyKeypressDelegate(sensortag.KeypressDelegate):

    def handleNotification(self, hnd, data):
        # NB: only one source of notifications at present
        # so we can ignore 'hnd'.
        val = struct.unpack("B", data)[0]
        up = (~val & self.lastVal) & self.ALL_BUTTONS
        if up != 0:
            self.onButtonUp(up)
        self.lastVal = val
        
    def onButtonUp(self, but):
        print ( "** " + self._button_desc[but] + " UP")
        writePanicButton()



def writeTemp(tempData):
    os.system('curl -X POST \
                    -H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"    \
                    -H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"    \
                    -H \"Content-Type: application/json\" \
                    -d \'{\"type\": \"temp\",\"content\":\"'+str(int(tempData[0]))+'\",\"sensor\":{\"__type\":\"Pointer\",\"className\":\"Sensor\",\"objectId\":\"Yxw1888C\" } }\' \
                    https://api.bmob.cn/1/classes/SensorDataHistory')

def writePanicButton():
    os.system('curl -X POST \
                -H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"          \
                -H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"        \
                -H \"Content-Type: application/json\" \
                -d \'{\"data\": {\"Alert\": "Warning!! Panic Button Pressed!!!.\"}}\' \
                https://api.bmob.cn/1/push')    

    os.system('curl -X POST \
                    -H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"    \
                    -H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"    \
                    -H \"Content-Type: application/json\" \
                    -d \'{\"type\": \"panic\",\"content\":\"panic\",\"sensor\":{\"__type\":\"Pointer\",\"className\":\"Sensor\",\"objectId\":\"ZLjPBBBN\" } }\' \
                    https://api.bmob.cn/1/classes/SensorDataHistory')


def main():
    print("Connecting to the SensorTag")
    #Connect to Sensortag
    tag=sensortag.SensorTag(sensortagAddress);
    print("Connected!")

    #Enableing Sensors
    tag.accelerometer.enable()
    tag.IRtemperature.enable()
    tag.keypress.enable()
    tag.setDelegate(MyKeypressDelegate())

    time.sleep(1.0)

    counter=120

    while True:
        accData=tag.accelerometer.read()
        print("Accelerometer: ",accData)
        tempData=tag.IRtemperature.read()
        print("Temp: ",tempData)
        
        x=accData[0];
        y=accData[1];
        z=accData[2];

        counter+=1

        #120s do write temp to database
        if counter>120:
            #write temp data
            writeTemp(tempData)
            counter=0

        #Fall Detect
        if math.sqrt(x*x+y*y+z*z)>2 :
            print("FALL!!!")
            #push notification
            os.system('curl -X POST \
                -H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"          \
                -H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"        \
                -H \"Content-Type: application/json\" \
                -d \'{\"data\": {\"Alert\": "Warning!! Fall detected!!!.\"}}\' \
                https://api.bmob.cn/1/push')
            #write sensor history
            os.system('curl -X POST \
                -H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"    \
                -H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"    \
                -H \"Content-Type: application/json\" \
                -d \'{\"type\": \"motion sensor\",\"content\":\"fallen over\",\"sensor\":{\"__type\":\"Pointer\",\"className\":\"Sensor\",\"objectId\":\"a6a263407f\" } }\' \
                https://api.bmob.cn/1/classes/SensorDataHistory')

        time.sleep(0.5)

    db.close()
    tag.disconnect()
    
if __name__ == "__main__":
    main()