import stackexchange
import json

def download(tag, amount, file_path):
    so = stackexchange.Site(stackexchange.StackOverflow)
    i = 1;
    # step one - import question with given tag and exclude source code
    f = open(file_path, 'w')
    f.write('[')
    for question in so.questions(tagged=[tag], pagesize=10, body = 'true'):
        print i, ' question out of ', amount, ' downloaded'
        raw = question.json
        f.write(json.dumps(raw))
        if i == amount :
            break;
        f.write(',')
        i += 1
    f.write(']')
    f.close()
