import json
import re

#remove all source code from posts including code tag
def removeSourceCodeWithCodeTag(text) :
    return re.sub("((<code>).*(<\/code>))", '', text, flags = re.MULTILINE|re.DOTALL)

def removeTags(text) :
    return re.sub("(<.+?>)",'',text)
