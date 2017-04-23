# Massive Data Processing - Technical forum comparison - Stack Overflow and Reddit
Project for Massive Data Processing at Universitat de Lleida 


Authors : Tomasz Dąbek and Stefania Perlak
## StackOverflow
To use StackOverflow module, downloading and installing Py-StackExchange is necessary 

**LINK:** https://github.com/lucjon/Py-StackExchange

This module works in two ways. The first way is downloading raw json file using API wrapper. Wrapper provides methods which can be used to download explicit data. However, here whole json dump is downloaded in purpose to have offline access to raw data. Questions are downloaded in date order, starting with the newest. If it contains answers, they are also downloaded. In this step, user must be aware of SO API throtling. On cleaning step, markdown tags and source code are eliminated using regular expresions. Data is stored in custom json structured file : text, author, date. 

### Sample usage
To download 350 questions from SO, which are tagged as python :


<code>
python stackoverflow.py sdd python 350 pythonrawdump.json 
</code>



To clean downloaded data:


<code>
python stackoverflow.py c pythonrawdump.json pythonformateddump.json
</code>


## Reddit
To use Reddit module, downloading and installig PRAW is necessary

**LINK:** https://github.com/praw-dev/praw

What is more, to run reddit you need to generate unique OAuth key to API. The key used by us in development process is not published in public repo because of security reason.

We do not figure how to download raw data using PRAW, so we decided to download data into common format in single step. In this case, we do not think about separate cleaning working module. 

### Sample usage 

To download 10 topics with comments from subreddit *learnpython*

<code>
python reddit.py sdd learnpython 10 pythonrawdump.json id secret agent
</code>

Where <code>id</code> is client id generated in reddit account, <code>secret</code> is OAuth secret geenrated by reddit and <code>agent</code> is user agent, which fulfill Reddit API usage policy 


