import csv
import sys
import datetime

# python convert_muse.py 2014-09-09\ TrailerDrug\ INTERN_529844535 machine INTERN_529844535

data_path = sys.argv[1]

machine = sys.argv[2]
subject = sys.argv[3]

sensors = ['TP9', 'FP1', 'FP2', 'TP10']

header = ['machine', 'subject', 'start_time', 'end_time', 'sigqual', 'rawwave']

times = {}
values = {}

with open(data_path + '/' + 'museplayer.csv', 'rb') as f:
	reader = csv.reader(f)
	reader.next() # skip 1st line
	reader.next() # skip 2nd line

	for row in reader:
		unixtime, path = float(row[0]), row[1].strip()
		utctime = datetime.datetime.fromtimestamp(unixtime) + datetime.timedelta(hours=4) # Hack: convert EDT to UTC
		start_time = utctime.strftime('%Y-%m-%d %H:%M:%S')

		if path == '/muse/eeg':

			times[start_time] = True

			for r in sensors:
				key = start_time + ' ' + r
				value = row[ sensors.index(r) + 2].strip()

				if key in values:
					values[key] = values[key] + ' ' + value 
				else:
					values[key] = value

for r in sensors:
	with open(data_path + '/' + r + '.xls', 'wb') as f:
		writer = csv.writer(f, delimiter='\t')
		writer.writerow(header)

		for start_time in sorted(times.keys()):
			entry = list(header)
			entry[ header.index('machine') ] = machine
			entry[ header.index('subject') ] = subject
			entry[ header.index('start_time') ] = start_time + '.000'
			entry[ header.index('end_time') ] = start_time + '.999'
			entry[ header.index('sigqual') ] = 'sigqual'

			key = start_time + ' ' + r
			entry[ header.index('rawwave') ] = values[key]

			writer.writerow(entry)
