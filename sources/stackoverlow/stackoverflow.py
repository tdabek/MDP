import stackexchange
import json
so = stackexchange.Site(stackexchange.StackOverflow)

i = 0;
output = []
# step one - import question with given tag and exclude source code
f = open('myfile3.json', 'w')
for question in so.questions(tagged=['python'], pagesize=100, body = 'true'):
    raw= question.json
    output.append(raw)
    if i == 15000 :
        f.write(json.dumps(output))
        break;
    i += 1

f.close()
