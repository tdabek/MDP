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

    output = open(output_path,'w')
    iterator = 1
    length = len(json_files)
    #main loop
    for question in json_files :
        #question
        cleanedObject = processSORecord(question)
        if not cleanedObject is None:
            output.write(cleanedObject.toJSON())
        ans_length = question['answer_count']
        if(iterator != length or ans_length > 0):
            output.write('\n')
        #answers
        if ans_length > 0 :
            ans_iterator = 1
            for answer in question['answers'] :
                cleanedObject = processSORecord(answer)
                if not cleanedObject is None:
                    output.write(cleanedObject.toJSON())
                if not (length == iterator and ans_iterator ==  ans_length):
                    output.write('\n')
                ans_iterator += 1
        iterator += 1
    output.close()
