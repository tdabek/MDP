import praw
from cleanedObject import *


def makeCleanedObjectFromSubmission(submission):
    cleanedObject = CleanedObject()
    cleanedObject.body = submission.selftext
    cleanedObject.author = submission.author.name
    cleanedObject.date = int(submission.created_utc)
    return cleanedObject

def makeCleanedObjectFromComment(comment):
    cleanedObject = CleanedObject()
    cleanedObject.body = comment.body
    cleanedObject.author = comment.author.name
    cleanedObject.date = int(comment.created_utc)
    return cleanedObject

def download(subredit,amount,file_path, id, secret,agent):
    reddit = praw.Reddit(client_id=id,
                         client_secret=secret,
                         user_agent=agent)
    iterator = 1
    
    f = open(file_path,'w')
    f.write('[')
    for submission in reddit.subreddit(subredit).hot(limit=amount):
        cleaned = makeCleanedObjectFromSubmission(submission)
        f.write(cleaned.toJSON())
        comment_iterator = 1
        if amount != iterator or submission.num_comments > 0:
            f.write(',')
        submission.comments.replace_more(limit=0)
        comments = submission.comments.list()
        total_num_of_comments = len(comments)
        for comment in comments:
            cleaned = makeCleanedObjectFromComment(comment)
            f.write(cleaned.toJSON())
            if not (amount == iterator and comment_iterator ==  total_num_of_comments):
                f.write(',')
            comment_iterator += 1
        iterator += 1
    f.write(']')
    f.close()


