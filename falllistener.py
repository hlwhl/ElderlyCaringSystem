import MySQLdb

if __name__ == '__main__':
	db=MySQLdb.Connect(host='systemdb.c6fod1umq6rt.eu-west-2.rds.amazonaws.com',port=3306,user='hlwhl',passwd='sxlfszhlw216',db='systemDB',charset='utf8')
    cursor=db.cursor()