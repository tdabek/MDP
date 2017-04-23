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

To clean downloaded data

<code>
python stackoverflow.py c pythonrawdump.json pythonformateddump.json
</code>


## Reddit
To use Reddit module, downloading and installig PRAW is necessary

**LINK:** https://github.com/praw-dev/praw

What is more, to run reddit you need to generate unique OAuth key to API. The key is not published in public repo because of security reason.
