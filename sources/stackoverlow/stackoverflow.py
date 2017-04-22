#Main STACK OVERFLOW data application
#Beware of API Throttling !!
#Working modes:
####################
# sdd - simple data downloader :
# param 1 : SO post tag
# param 2 : number of pages of questions to download (20 question per page)
# param 3 : output file path
####################
# add advanced data downloader - TODO
####################
# c - data cleaner
# param 1 : input file path
# param 2 : output file path
####################
# Examples of use :
#
# To download 1000 questions with potential answers with java tag :
# python stackoverflow.py sdd java 3 javarawdump.json
#
# To clean data :
# python stackoverflow.py c javarawdump.json javacleaneddump.json
import sys
from cleaner import clean
from downloader import download

try:
    mode = sys.argv[1]
    if mode == 'sdd': #simple data downloader
        tag = sys.argv[2]
        amount = int(sys.argv[3])
        output = sys.argv[4]
        download(tag,amount,output)
    elif mode == 'c': #clean data
        input = sys.argv[2]
        output = sys.argv[3]
        clean(input,output)
    elif mode=='add':
        print 'not supported yet'
    else:
        print 'Not supported work mode'
except:
    print 'Unspecified error occured ' , sys.exc_info()[0]
