import json
import time
from cleaningUtils import *
from cleanedObject import *
# processing raw SO data files into custom format
# Operations : removing source code from body,
#              removing rest of markdown tags,
#              add to new file with processed, body, unix epoch timestamp, userName

#given parameter with fileName with SO dump
f = open('myfile2.json','r')
input = f.read()
f.close()

#json files
json_files = json.loads(input)

output_list = []

#main loop
for question in json_files :
    body = question['body']
    cleanedBody = removeSourceCodeWithCodeTag(body)
    cleanedBody = removeTags(cleanedBody)
    author = question['owner']['display_name']
    creation_time = question['creation_date']
    cleanedObject = CleanedObject()
    cleanedObject.body = cleanedBody
    cleanedObject.time = time.strftime('%H : %M : %S - %d/%m/%Y',time.gmtime(creation_time))
    cleanedObject.time_raw = creationTime
    cleanedObject.author = author
    output_list.append(cleanedObject.toJSON())
output = open('myfile4.json','w')
output.write(json.dumps(output_list))
output.close()
