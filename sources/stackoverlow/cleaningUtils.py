import json, re, time
from cleanedObject import *

#removes all source code from posts including code tag
def removeSourceCodeWithCodeTag(text) :
    return re.sub("((<code>).*(<\/code>))", '', text, flags = re.MULTILINE|re.DOTALL)

#removes all tags from text
def removeTags(text) :
    return re.sub("(<.+?>)",'',text)

#converts JSON question to cleanedObject which can be serialized
def processSORecord (question):
    body = question['body']
    cleanedBody = removeSourceCodeWithCodeTag(body)
    cleanedBody = removeTags(cleanedBody)
    author = question['owner']['display_name']
    creation_time = question['creation_date']
    cleanedObject = CleanedObject()
    cleanedObject.body = cleanedBody
    cleanedObject.time = time.strftime('%H : %M : %S - %d/%m/%Y',time.gmtime(creation_time))
    cleanedObject.time_raw = creation_time
    cleanedObject.author = author
    return cleanedObject
