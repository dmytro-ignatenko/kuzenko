import httplib2
import json
import urllib
from optparse import OptionParser
#from AptUrl.Parser import parse

h = httplib2.Http()
host = 'http://localhost:8080/kuzenko-ws/api/'

def setHost(hostName) :
    global host
    host = hostName


def setDatabaseName(name) :
    resp, content = h.request(host + 'database/' + name, "POST", '')
    #print resp
    print content
    
def listTables()  :  
    resp, content = h.request(host + "table", "GET")
    #print resp
    print content
    
def makeTable(data):
    name,rest = data[0], ','.join(data[1:])
    resp, content = h.request(host + "table/" + name + '?' + urllib.urlencode({"columnTypes" : rest}), "POST", '')
    #print resp
    print content
    
def removeTable(name) :
    resp, content = h.request(host + "table/" + name , "DELETE", '')
    #print resp
    print content
    
def addRow(data) :
    name,rest = data[0], ','.join(data[1:])
    resp, content = h.request(host  + "table/" + name + '/data' + '?' +  urllib.urlencode({"columnData" : rest}) , "POST", '')
    #print resp
    print content
    
def removeRow(data) :
    name, data = data[0], ','.join(data[1:])
    resp, content = h.request(host  + "table/" + name + '/data' + '?' +  urllib.urlencode({"columnData" : {'1':'3'}}), "DELETE", '')
    #print resp
    print content
    
def dropDatabase() :
    resp, content = h.request(host +  "/database", "DELETE", '')
    #print resp
    print content
    
def showTable(name) :
    resp, content = h.request(host + "table/" + name + '/data', "GET")
    #print resp
    print content
        
def descartTables(data) :
    name1,name2 = data[0],data[1]
    resp, content = h.request(host + "table/" + name1 + '/descart/' + name2, "GET")
    #print resp
    print content
    
methods = {
           "lstbl" : listTables,
           "mktbl" : makeTable,
           "rmtbl" : removeTable,
           "addrw" : addRow,
           "rmvrw" : removeRow,
           "drpdb" : dropDatabase,
           "swtbl" : showTable,
           "dctbl" : descartTables,
    }

parser = OptionParser()
parser.add_option('-d',"--directory", action="store", type="string", dest="directory")
parser.add_option('-c','--command',action='store',type='string',dest='command')
parser.add_option('-p','--parameters',action='store',type='string',dest='parameters')

print "Python client started..."
line = raw_input()
while line != 'exit'  : 
    (option,_) = parser.parse_args(line.split(' '))
    if option.directory is None or option.command is None :
        print "Wrong command format"
        line = raw_input()
        continue
    setDatabaseName(option.directory)
    method = methods[option.command]
    if option.parameters is None : method()
    else :
        l = option.parameters.split(';')
        if len(l) == 1 :
            method(l[0].split('=')[1])
        else :
            method([x.split('=')[1] for x in l])
    line = raw_input()
