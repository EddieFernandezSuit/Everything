from requests.auth import HTTPDigestAuth
import requests
import datetime

TOMORROW = datetime.date.today() + datetime.timedelta(days=2)

SB_PROJECT_ID = "5f5709cb91d3e224f860a391"
DELETE_AFTER_DATE = str(TOMORROW) + "T23:59:0Z"
PUBLIC_IP = "73.239.162.188"

API = "https://cloud.mongodb.com/api/atlas/v1.0/groups/{0}/accessList?pretty=true".format(SB_PROJECT_ID)

PAYLOAD = [{"ipAddress": PUBLIC_IP, "comment": "eddie ip address", "deleteAfterDate": DELETE_AFTER_DATE}]

r = requests.post(API, auth=HTTPDigestAuth("VZKTZPDQ", "ab5d29d4-62b4-4f0a-90ca-2d46238b6796"), json=PAYLOAD,
                headers={"Content-Type": "application/json"})

print(r.text)

