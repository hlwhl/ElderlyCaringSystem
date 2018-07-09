from BaseHTTPServer import HTTPServer, BaseHTTPRequestHandler
import MySQLdb

class MyHTTPHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        db=MySQLdb.Connect(host='systemdb.c6fod1umq6rt.eu-west-2.rds.amazonaws.com',port=3306,user='hlwhl',passwd='sxlfszhlw216',db='systemDB',charset='utf8')
        cursor=db.cursor()
        cursor.execute("SELECT * FROM AccData WHERE id=1")
        data=cursor.fetchone()
        json="{\"x\":"+str(data[1])+",\"y\":"+str(data[2])+",\"z\":"+str(data[3])+"}"
        self.protocal_version = 'HTTP/1.1'
        self.send_response(200)
        self.send_header("Welcome", "Contect")
        self.end_headers()
        self.wfile.write(json)

def start_server(port):
    http_server = HTTPServer(('', int(port)), MyHTTPHandler)  
    http_server.serve_forever()

start_server(8000)
