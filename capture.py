"""
Written by Prashant Kumar Prajapati
Start Date : 20th Jan 2017

"""

#Import all the necessary modules

from flask import Flask,Response,jsonify,request
import time
import picamera
import numpy as np
import cv2

#Create a Flask Server to handle get requests

app = Flask(__name__)
@app.route("/get_object")

"""
Function getobject() is called when API request is received.

Function Input Details : No Input Parameters are required to be passed.

Function return Details : JSON Object
                   Object Details: JSON Object (Sucess=True/False,x,y,message="Sucess")
		   
		   Sucess -> returns true if object created successfully otherwise false
		   x -> Manhattan Distance along x-axis of our User from the requested object.
		   y -> Manhattan Distance along y-axis of our User from the requested object.
		   message -> string which is passed to android app to inform the user about successfull detection of object. 
"""

def getobject():
    user_input = request.args.get('user_input')
    with picamera.PiCamera() as camera:
        camera.resolution = (640, 360)
        camera.capture('previous.jpg')
        time.sleep(1)
        camera.capture('current.jpg')
        prev ,curr = cv2.imread('/home/pi/previous.jpg',-1), cv2.imread('/home/pi/current.jpg',-1)
        prev = cv2.cvtColor(prev, cv2.COLOR_BGR2GRAY)
        prev = cv2.GaussianBlur(prev,(21,21),0)
        curr = cv2.cvtColor(curr, cv2.COLOR_BGR2GRAY)
        curr = cv2.GaussianBlur(curr,(21,21),0)
        diff=cv2.absdiff(prev,curr)
        thresh=cv2.threshold(diff,25,255,cv2.THRESH_BINARY)[1]
        thresh=cv2.dilate(thresh,None,iterations=2)
        (cnts,_)=cv2.findContours(thresh.copy(), cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
        for c in cnts:
	    (x, y, w, h) = cv2.boundingRect(c)
	    x0=x + w//2
	    y0=y + h//2	    
    template = cv2.imread('/home/pi/Desktop/'+user_input+'.jpg',0)
    h,w = template.shape
    res=cv2.matchTemplate(curr,template,1)
    min_val, max_val, min_loc, max_loc = cv2.minMaxLoc(res)
    top_left=min_loc
    x1,y1=top_left[0]+w//2 ,top_left[1]+h//2
    X= x1-x0
    Y= y1-y0
    Z= (X**2+Y**2)**0.5

    return jsonify(
        success=True,
        x=X,
        y=Y,
        message="Success")

#Run your local server on port 8888 of Raspberry Pi

if __name__ == "__main__":
    app.run(host='0.0.0.0', port = 8888, debug = True)


        



    
