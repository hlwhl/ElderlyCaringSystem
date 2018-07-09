import bluepy
from bluepy import sensortag
import math

def main():
    import time
    import sys
    import argparse

    parser = argparse.ArgumentParser()
    parser.add_argument('host', action='store',help='MAC of BT device')
    parser.add_argument('-n', action='store', dest='count', default=0,
            type=int, help="Number of times to loop data")
    parser.add_argument('-t',action='store',type=float, default=5.0, help='time between polling')
    parser.add_argument('-T','--temperature', action="store_true",default=False)
    parser.add_argument('-A','--accelerometer', action='store_true',
            default=False)
    parser.add_argument('-H','--humidity', action='store_true', default=False)
    parser.add_argument('-M','--magnetometer', action='store_true',
            default=False)
    parser.add_argument('-B','--barometer', action='store_true', default=False)
    parser.add_argument('-G','--gyroscope', action='store_true', default=False)
    parser.add_argument('-K','--keypress', action='store_true', default=False)
    parser.add_argument('-L','--light', action='store_true', default=False)
    parser.add_argument('--all', action='store_true', default=False)

    arg = parser.parse_args(sys.argv[1:])

    print('Connecting to ' + arg.host)
    tag = sensortag.SensorTag("A0:E6:F8:AF:76:01")

    # Enabling selected sensors
    if arg.temperature or arg.all:
        tag.IRtemperature.enable()
    if arg.humidity or arg.all:
        tag.humidity.enable()
    if arg.barometer or arg.all:
        tag.barometer.enable()
    if arg.accelerometer or arg.all:
        tag.accelerometer.enable()
    if arg.magnetometer or arg.all:
        tag.magnetometer.enable()
    if arg.gyroscope or arg.all:
        tag.gyroscope.enable()
    if arg.keypress or arg.all:
        tag.keypress.enable()
        tag.setDelegate(sensortag.KeypressDelegate())
    if arg.light and tag.lightmeter is None:
        print("Warning: no lightmeter on this device")
    if (arg.light or arg.all) and tag.lightmeter is not None:
        tag.lightmeter.enable()

    # Some sensors (e.g., temperature, accelerometer) need some time for initialization.
    # Not waiting here after enabling a sensor, the first read value might be empty or incorrect.
    time.sleep(1.0)

    counter=1
    while True:
       if arg.temperature or arg.all:
           print('Temp: ', tag.IRtemperature.read())
       if arg.humidity or arg.all:
           print("Humidity: ", tag.humidity.read())
       if arg.barometer or arg.all:
           print("Barometer: ", tag.barometer.read())
       if arg.accelerometer or arg.all:
           print("Accelerometer: ", tag.accelerometer.read())
       if arg.magnetometer or arg.all:
           print("Magnetometer: ", tag.magnetometer.read())
       if arg.gyroscope or arg.all:
           print("Gyroscope: ", tag.gyroscope.read())
       if (arg.light or arg.all) and tag.lightmeter is not None:
           print("Light: ", tag.lightmeter.read())
       if counter >= arg.count and arg.count != 0:
           break
       counter += 1
       tag.waitForNotifications(arg.t)

    tag.disconnect()
    del tag

if __name__ == "__main__":
    main()