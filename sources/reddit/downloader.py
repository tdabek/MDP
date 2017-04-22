import praw
#TODO
def download(subredit,amount,file_path, id, secret,agent):
    reddit = praw.Reddit(client_id=id,
                         client_secret=secret,
                         user_agent=agent)
    for submission in reddit.subreddit('learnpython').hot(limit=10):
        print(submission.title)
