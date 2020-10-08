import pandas as pandas
import numpy as numpy
import sys
import matplotlib as mpl
import matplotlib.pyplot as mpt
import seaborn as sbn

if __name__ == "__main__":
	infile = sys.argv[1]
	outfile = sys.argv[2]
	df = pandas.read_csv(infile)
	#print(df)
	labelrow = sys.argv[3]
	dararow = sys.argv[4]
	grouped = df.groupby([labelrow])[labelrow].count()
	print(grouped)
