import csv
import sys
from datetime import datetime
from datetime import timedelta

# python label_task.py 2014-05-02 TrailerDrug INTERN_529844535 engage

data_path = sys.argv[1]
label = sys.argv[2]

with open(data_path + '/survey.txt', 'rb') as f:
	reader = csv.reader(f, delimiter='\t')
	reader.next()

	values = {}
	for row in reader:
		subject, start_time, answer = row[0], row[1], row[4]
		if answer != '[]':
			values[subject + ' ' + start_time] = int(answer[-4])

header = ['machine', 'subject', 'stim', 'block', 'start_time', 'end_time', 'cond', 'score']

with open(data_path + '/log.txt', 'rb') as f, open(data_path + '/task.xls', 'wb') as f2:
	reader = csv.reader(f, delimiter='\t')
	writer = csv.writer(f2, delimiter='\t')

	reader.next()
	writer.writerow(header)

	for row in reader:
		content, subject, start_time, end_time, engage = row[0], row[1], row[2], row[3], int(row[4])

		key = subject + ' ' + start_time
		if key in values:
			score = values[key]
		else:
			score = 0

		if label == 'score':

			if content == 'Rest':
				cond = 3
			elif 6 <= score:
				cond = 2
			elif 1 <= score and score <= 2:
				cond = 1
			else:
				cond = 0

		elif label == 'engage':

			if content == 'Rest':
				cond = 3
			elif engage == 1:
				cond = 2
			elif engage == 0:
				cond = 1
			else:
				cond = 0

		entry = list(header)
		entry[ header.index('subject') ] = subject
		entry[ header.index('stim') ] = content
		entry[ header.index('block') ] = data_path
		entry[ header.index('start_time') ] = start_time
		entry[ header.index('end_time') ] = end_time
		entry[ header.index('cond') ] = cond
		entry[ header.index('score') ] = score

		writer.writerow(entry)
