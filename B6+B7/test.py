import uuid
import base64

print(base64.urlsafe_b64encode(uuid.uuid1().bytes).rstrip(b'=').decode('ascii'))