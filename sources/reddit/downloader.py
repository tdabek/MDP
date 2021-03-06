import praw,re
from cleanedObject import *

#removes all source code from posts including code tag
def removeSourceCodeWithCodeTag(text) :
    return re.sub("((<code>).*(<\/code>))", '', text, flags = re.MULTILINE|re.DOTALL)

#removes all tags from text
def removeTags(text) :
    return re.sub("(<.+?>)",'',text)

def makeCleanedObjectFromSubmission(submission):
    if submission.author is None or submission.selftext_html is None or submission.created_utc is None or len(submission.selftext_html) == 0:
        return None
    cleanedObject = CleanedObject()
    body = submission.selftext_html
    body = removeSourceCodeWithCodeTag(body)
    if len(body) == 0:
        return None
    body = removeTags(body)
    cleanedObject.body = body
    cleanedObject.author = submission.author.name
    cleanedObject.date = int(submission.created_utc)
    cleanedObject.source = 2
    return cleanedObject

def makeCleanedObjectFromComment(comment):
    if comment.author is None or comment.body_html is None or comment.created_utc is None or len(comment.body_html) == 0:
        return None
    cleanedObject = CleanedObject()
    body = comment.body_html
    body = removeSourceCodeWithCodeTag(body)
    if len(body) == 0:
        return None
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
        if not cleaned is None:
            f.write(cleaned.toJSON())
        comment_iterator = 1
        if amount != iterator or submission.num_comments > 0:
            f.write('\n')
        submission.comments.replace_more(limit=0)
        comments = submission.comments.list()
        total_num_of_comments = len(comments)
        for comment in comments:
            cleaned = makeCleanedObjectFromComment(comment)
            if not cleaned is None:
                f.write(cleaned.toJSON())
            if not (amount == iterator and comment_iterator ==  total_num_of_comments):
                f.write('\n')
            comment_iterator += 1
        iterator += 1
    f.close()


