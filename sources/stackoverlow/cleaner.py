# processing raw SO data files into custom format
# Operations : removing source code from body,
#              removing rest of markdown tags,
#              add to new file with processed, body, unix epoch timestamp, userName



import json
from cleaningUtils import processSORecord
from cleanedObject import *



#given parameter with fileName with SO dump
f = open('myfile2.json','r')
input = f.read()
f.close()

#json files
json_files = json.loads(input)

output_list = []

#main loop
for question in json_files :
    #question
    cleanedObject = processSORecord(question)
    output_list.append(cleanedObject.toJSON())
    #answers
    print question['is_answered']
    if question['is_answered'] :
        for answer in question['answers'] :
            cleanedObject = processSORecord(answer)
            output_list.append(cleanedObject.toJSON())

output = open('myfile4.json','w')
output.write(json.dumps(output_list))
output.close()
