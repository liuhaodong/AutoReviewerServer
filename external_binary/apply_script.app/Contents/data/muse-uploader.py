#!/usr/bin/env python
import urllib, urllib2
import datetime
import optparse
from OSC import *
from OSC import _readString, _readFloat, _readInt

API_ENDPOINT = 'http://li282-60.members.linode.com/EEG/input'
TIMEFORMAT = '%Y-%m-%d %H:%M:%S'

def testStreamingServerAndClient(listen_address):
	# define a message-handler function for the server to call.
		
	def default_handler(addr, tags, stuff, source):
		#print "SERVER: No handler registered for ", addr
		return None

	def send_eeg(wave, sensor, start_time, end_time):
		values = dict(student='viewer', raw=wave, sensor=sensor, start_time=start_time, end_time=end_time)
		data = urllib.urlencode(values)
		req = urllib2.Request(API_ENDPOINT, data)
		rsp = urllib2.urlopen(req)
		return rsp

	class DemoOSCStreamRequestHandler(OSCStreamRequestHandler):
		data = []
		buffer_start = None
		buffer_end = None
		def setupAddressSpace(self):
			self.addMsgHandler("/exit", self.exit_handler)
			self.addMsgHandler("/muse/eeg", self.printing_handler)
			self.addMsgHandler("default", default_handler)
			print "SERVER: Address space:"
			for addr in self.getOSCAddressSpace():
				print addr

		def printing_handler(self, addr, tags, stuff, source):
			now = datetime.datetime.now()
			if (self.buffer_end != None and now > self.buffer_end):
				start_time = self.buffer_start.strftime(TIMEFORMAT)
				end_time = self.buffer_end.strftime(TIMEFORMAT)
				chans = ['fp1', 'fp2', 'a1', 'a2']
				rsps = []
				for i, c in enumerate(chans):
					wave_toks = [str(int(d[i])) for d in self.data]
					wave = ' '.join(wave_toks)
					rsp = send_eeg(wave, c, start_time, end_time)
					rsps.append(rsp)
				for rsp in rsps:
					if rsp.read() != "ok":
						print "ERROR SENDING EEG"
				self.data = []
			if (len(self.data) == 0):
				self.buffer_start = now
				self.buffer_end = now + datetime.timedelta(seconds=1)
			self.data.append(stuff)
			return None
			
		def exit_handler(self, addr, tags, stuff, source):
			print "SERVER: EXIT ", addr
			self.server.run = False
			self.stop()
			sys.exit()
			return None

	class DemoServer(OSCStreamingServerThreading):
		RequestHandlerClass = DemoOSCStreamRequestHandler
		def __init__(self, listen_address):
			OSCStreamingServerThreading.__init__(self, listen_address)
			self.run = True
			
	s = DemoServer(listen_address)
	s.start()
	
	try:
		while s.run :
			time.sleep(.01)
	except KeyboardInterrupt:
		print "Interrupted."
		time.sleep(1)
		s.stop()
		sys.exit()

	time.sleep(1)
	s.stop()
	sys.exit()

listen_address = ('127.0.0.1', 5000)
testStreamingServerAndClient(listen_address)