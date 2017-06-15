import praw,re
from cleanedObject import *

#removes all source code from posts including code tag
def removeSourceCodeWithCodeTag(text) :
    return re.sub("((<code>).*(<\/code>))", '', text, flags = re.MULTILINE|re.DOTALL)

#removes all tags from text
def removeTags(text) :
    return re.sub("(<.+?>)",'',text)

def makeCleanedObjectFromSubmission(submission):
    cleanedObject = CleanedObject()
    body = submission.selftext_html
    body = removeSourceCodeWithCodeTag(body)
    body = removeTags(body)
    cleanedObject.body = body
    cleanedObject.author = submission.author.name
    cleanedObject.date = int(submission.created_utc)
    cleanedObject.source = 2
    return cleanedObject

def makeCleanedObjectFromComment(comment):
    cleanedObject = CleanedObject()
    body = comment.body_html
    body = removeSourceCodeWithCodeTag(body)
    body = removeTags(body)
    cleanedObject.body = body
    cleanedObject.author = comment.author.name
    cleanedObject.date = int(comment.created_utc)
    cleanedObject.source = 2
    return cleanedObject

def download(subredit,amount,file_path, id, secret,agent):
    reddit = praw.Reddit(client_id=id,
                         client_secret=secret,
                         user_agent=agent)
    iterator = 1
    
    f = open(file_path,'w')
    for submission in reddit.subreddit(subredit).hot(limit=amount):
        cleaned = makeCleanedObjectFromSubmission(submission)
        f.write(cleaned.toJSON())
        comment_iterator = 1
        if amount != iterator or submission.num_comments > 0:
            f.write('\n')
        submission.comments.replace_more(limit=0)
        comments = submission.comments.list()
        total_num_of_comments = len(comments)
        for comment in comments:
            cleaned = makeCleanedObjectFromComment(comment)
            f.write(cleaned.toJSON())
            if not (amount == iterator and comment_iterator ==  total_num_of_comments):
                f.write('\n')
            comment_iterator += 1
        iterator += 1
    f.close()


