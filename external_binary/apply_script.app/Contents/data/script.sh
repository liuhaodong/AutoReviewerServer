#!/bin/bash

rm -f survey.xls task.xls task0.xls task1.xls task2.xls task3.xls

# preprocess task data

for LOG_FILE in **/log.txt; do
	DATA_PATH=${LOG_FILE/\/log.txt}
	IFS=" "
	set $DATA_PATH

	MACHINE='machine'
	SUBJECT=$3
	LABEL='engage'

	echo -e "\n$DATA_PATH"
	python label_task.py "$DATA_PATH" $LABEL
	python convert_muse.py "$DATA_PATH" $MACHINE $SUBJECT
done

# aggregate directories

LIST="task TP9 FP1 FP2 TP10"

for l in $LIST
do
	head -n 1 "`ls | head -n 1`"/$l.xls > $l.xls
	for f in **/$l.xls; do
		tail -n +2 "$f" >> $l.xls
	done

	# replace subject

	sed -i 's/INTERN_[^\t]*\t/subject\t/g' $l.xls
done

cp task.xls task0.xls

# recombine rows

DATA_PATH='.'
GROUP_ONE='subject'
GROUP_TWO='stim'
MIN_LENGTH=0
MAX_LENGTH=5

python recombine_task.py "$DATA_PATH" $GROUP_ONE $GROUP_TWO $MIN_LENGTH $MAX_LENGTH
cp task2.xls task.xls

# filter by cond ($7) (2nd awk command also prints 1 line before the match)

awk -F'\t' '$7 != 0' task.xls > task3.xls
#head -n1 task.xls > task3.xls && awk -F'\t' '$7 == 2 && l { print l; print $0 }; { l=$0 }' task.xls >> task3.xls
cp task3.xls task.xls
