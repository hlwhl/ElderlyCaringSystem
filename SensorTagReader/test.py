import os


def main():
	os.system('curl -X POST \
				-H \"X-Bmob-Application-Id: 718cb7645ebfcd11e7af7fc89230d1ce\"          \
				-H \"X-Bmob-REST-API-Key: 7c42e568f537207d6beb3e38a0c4c5dc\"        \
				-H \"Content-Type: application/json\" \
				-d \'{\"data\": {\"alert\": "Warning!! Fall detected!!!.\"}}\' \
				https://api.bmob.cn/1/push')

if __name__ == '__main__':
	main()