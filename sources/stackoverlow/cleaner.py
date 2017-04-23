# Processing raw SO data files into custom format
# Operations : removing source code from body,
#              removing rest of markdown tags,
#              add to new file with processed, body, unix epoch timestamp, userName
# PARAMS : path to input file, path to output file
# WARNING : output file will be replaced if exists



import json
from cleaningUtils import processSORecord
from cleanedObject import *


def clean(input_path,output_path):
    #given parameter with fileName with SO dump
    f = open(input_path,'r')
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
        if question['is_answered'] :
            for answer in question['answers'] :
                cleanedObject = processSORecord(answer)
                output_list.append(cleanedObject.toJSON())

    output = open(output_path,'w')
    output.write(json.dumps(output_list))
    output.close()
